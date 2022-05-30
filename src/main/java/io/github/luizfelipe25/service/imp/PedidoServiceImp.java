package io.github.luizfelipe25.service.imp;

import io.github.luizfelipe25.domain.Cliente;
import io.github.luizfelipe25.domain.ItemPedido;
import io.github.luizfelipe25.domain.Pedido;
import io.github.luizfelipe25.domain.Produto;
import io.github.luizfelipe25.domain.enums.StatusPedido;
import io.github.luizfelipe25.exception.PedidoNaoEncontradoException;
import io.github.luizfelipe25.exception.RegraNegocioException;
import io.github.luizfelipe25.repository.Clientes;
import io.github.luizfelipe25.repository.ItensPedido;
import io.github.luizfelipe25.repository.Pedidos;
import io.github.luizfelipe25.repository.Produtos;
import io.github.luizfelipe25.rest.dto.ItemPedidoDTO;
import io.github.luizfelipe25.rest.dto.PedidoDTO;
import io.github.luizfelipe25.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImp implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar( PedidoDTO dto ) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException() );
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}