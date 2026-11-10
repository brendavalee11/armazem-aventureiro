package com.rpg.armazemaventureiro.exception;

public class ItemNaoEncontradoException extends RuntimeException {

    public ItemNaoEncontradoException() {
        super("Nilo procurou nas prateleiras, mas não encontrou esse item no catálogo.");
    }
}