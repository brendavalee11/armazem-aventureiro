package com.rpg.armazemaventureiro.exception;

public class NomeDuplicadoException extends RuntimeException {

    public NomeDuplicadoException(String nome) {
        super("Esse item já está registrado no livro de Nilo: '" + nome + "'. Cada mercadoria precisa de um nome exclusivo!");
    }
}