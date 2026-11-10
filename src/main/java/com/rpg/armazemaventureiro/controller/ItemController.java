package com.rpg.armazemaventureiro.controller;

import com.rpg.armazemaventureiro.exception.ItemNaoEncontradoException;
import com.rpg.armazemaventureiro.exception.NomeDuplicadoException;
import com.rpg.armazemaventureiro.model.Item;
import com.rpg.armazemaventureiro.repository.ItemRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/{id}")
    public Item buscarPorId(@PathVariable Long id) {
        return itemRepository.findById(id).orElseThrow(ItemNaoEncontradoException::new);
    }

    @PostMapping
    public ResponseEntity<Item> criar(@Valid @RequestBody Item item) {
        if (itemRepository.existsByNomeIgnoreCase(item.getNome())) {
            throw new NomeDuplicadoException(item.getNome());
        }
        item.setId(null);
        Item salvo = itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public Item atualizar(@PathVariable Long id, @Valid @RequestBody Item dados) {
        Item existente = itemRepository.findById(id).orElseThrow(ItemNaoEncontradoException::new);
        if (itemRepository.existsByNomeIgnoreCaseAndIdNot(dados.getNome(), id)) {
            throw new NomeDuplicadoException(dados.getNome());
        }
        existente.setNome(dados.getNome());
        existente.setRaridade(dados.getRaridade());
        existente.setPreco(dados.getPreco());
        existente.setNivelMinimo(dados.getNivelMinimo());
        return itemRepository.save(existente);
    }
}