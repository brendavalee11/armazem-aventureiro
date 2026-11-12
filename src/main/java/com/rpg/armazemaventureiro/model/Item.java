package com.rpg.armazemaventureiro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nilo não consegue registrar uma mercadoria sem nome. Informe o nome do item.")
    @Size(max = 100, message = "Esse nome é longo demais para o livro de registros! Utilize no máximo 100 caracteres.")

    @Column(unique = true)
    private String nome;

    @NotNull(message = "Nilo precisa saber a classificação da sua mercadoria! Escolha uma raridade: COMUM, INCOMUM, RARO, EPICO ou LENDARIO.")

    @Enumerated(EnumType.STRING)
    private Raridade raridade;

    @NotNull(message = "Toda mercadoria tem seu valor! Nilo precisa que você informe o preço do item antes de registrá-lo.")

    @DecimalMin(value = "0.0", message = "Nilo não vende mercadorias por valores negativos. Informe um preço maior ou igual a zero.")

    private BigDecimal preco;

    @Min(value = 1, message = "Esse requisito não cabe nas regras do armazém! O nível mínimo deve estar entre 1 e 100.")
    @Max(value = 100, message = "Esse requisito não cabe nas regras do armazém! O nível mínimo deve estar entre 1 e 100.")

    private Integer nivelMinimo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Raridade getRaridade() {
        return raridade;
    }

    public void setRaridade(Raridade raridade) {
        this.raridade = raridade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getNivelMinimo() {
        return nivelMinimo;
    }

    public void setNivelMinimo(Integer nivelMinimo) {
        this.nivelMinimo = nivelMinimo;
    }
}