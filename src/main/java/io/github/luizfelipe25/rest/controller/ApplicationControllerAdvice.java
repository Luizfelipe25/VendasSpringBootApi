package io.github.luizfelipe25.rest.controller;

import io.github.luizfelipe25.exception.PedidoNaoEncontradoException;
import io.github.luizfelipe25.exception.RegraNegocioException;
import io.github.luizfelipe25.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex){
        return new ApiErrors(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerMethodNotValidException(MethodArgumentNotValidException ex){
       List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map( erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(errors);
    }
}
