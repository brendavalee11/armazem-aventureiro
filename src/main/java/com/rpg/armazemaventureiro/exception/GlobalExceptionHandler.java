package com.rpg.armazemaventureiro.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> tratarItemNaoEncontrado(ItemNaoEncontradoException ex) {
        ErroResposta corpo = new ErroResposta(
                HttpStatus.NOT_FOUND.value(),
                "Item não encontrado",
                List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(corpo);
    }
}