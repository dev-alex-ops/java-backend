package com.devcodeops.java_backend.controllers;

import com.devcodeops.java_backend.dtos.ItemDTO;
import com.devcodeops.java_backend.entities.Item;
import com.devcodeops.java_backend.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<Item> list() {
        return itemService.list();
    }

    @PostMapping
    public Item create(@RequestBody ItemDTO dto) {
        return itemService.create(dto);
    }

    @PatchMapping("/{id}")
    public Item update(@PathVariable Long id, @RequestBody ItemDTO dto) {
        return itemService.update(id, dto);
    }

    @GetMapping("/{id}")
    public Item getById(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Item> getItemsByUser(@PathVariable Long userId) {
        return itemService.getItemsByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { itemService.delete(id); }
}
