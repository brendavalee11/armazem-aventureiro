package com.rpg.armazemaventureiro.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;

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

    @ExceptionHandler(NomeDuplicadoException.class)
    public ResponseEntity<ErroResposta> tratarNomeDuplicado(NomeDuplicadoException ex) {
        ErroResposta corpo = new ErroResposta(
                HttpStatus.CONFLICT.value(),
                "Nome duplicado",
                List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(corpo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarValidacao(MethodArgumentNotValidException ex) {
        List<String> mensagens = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        ErroResposta corpo = new ErroResposta(HttpStatus.BAD_REQUEST.value(),"Dados inválidos", mensagens);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> tratarHttpMessageNotReadableTemporario(HttpMessageNotReadableException ex) {
        Throwable causa = ex.getCause();
        if (causa instanceof InvalidFormatException) {
            ErroResposta corpo = new ErroResposta(HttpStatus.BAD_REQUEST.value(),"Raridade Inválida", List.of("As raridades reconhecidas são: COMUM, INCOMUM, RARO, ÉPICO, LENDÁRIO"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
        }
        ErroResposta corpo = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "JSON INVÁLIDO", List.of("Verifique a estrutura do JSON antes de entregá-lo"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
        
    }
}