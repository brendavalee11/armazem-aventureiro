package com.rpg.armazemaventureiro.repository;

import com.rpg.armazemaventureiro.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByNomeIgnoreCase(String nome);
}