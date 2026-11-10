package com.rpg.armazemaventureiro.exception;

import java.util.List;

  public record ErroResposta(int status, String erro, List<String> mensagens) {
}