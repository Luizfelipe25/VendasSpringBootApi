package io.github.luizfelipe25.rest.controller;

import io.github.luizfelipe25.domain.Cliente;
import io.github.luizfelipe25.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    //    @RequestMapping(value = {"/hello/{nome}","/api/hello"}, method = RequestMethod.GET) msm coisa q usar GetMapping
    @GetMapping("/{id}")
    public Cliente getClienteById( @PathVariable("id") Integer id){
        return clientes.findById(id).orElseThrow( () ->
                                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );

//        throw new ResponseStatusException("Cliente não encontrado", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save( @RequestBody @Valid Cliente cliente){
            return clientes.save(cliente);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){
        clientes.findById(id)
                .map( cliente -> {
                    clientes.delete(cliente);
                    return cliente;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado") );

    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer id, @RequestBody @Valid Cliente cliente){
        clientes.findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return clienteExistente;
        }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }

    @GetMapping("/filter")
    public List<Cliente> find(Cliente filtro){
//        String sql = "Select * from cliente";
//
//        if (filtro.getNome() != null){
//            sql += "where nome like :nome";
//        }
//
//        if (filtro.getId() != null){
//            sql += "where id like :id";
//        }
//
//        if (filtro.getCpf() != null){
//            sql += "where cpf like :cpf";
//        }
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);

      return clientes.findAll(example);
    }


}
