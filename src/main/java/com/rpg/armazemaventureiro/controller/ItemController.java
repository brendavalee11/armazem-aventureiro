package com.rpg.armazemaventureiro.controller;

import com.rpg.armazemaventureiro.model.Item;
import com.rpg.armazemaventureiro.repository.ItemRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/itens")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<Item> listar() {
        return itemRepository.findAll();
    }
}