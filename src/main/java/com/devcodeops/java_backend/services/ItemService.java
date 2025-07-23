package com.devcodeops.java_backend.services;

import com.devcodeops.java_backend.dtos.ItemDTO;
import com.devcodeops.java_backend.exceptions.DuplicateFieldException;
import com.devcodeops.java_backend.exceptions.NotFoundException;
import com.devcodeops.java_backend.entities.Item;
import com.devcodeops.java_backend.entities.User;
import com.devcodeops.java_backend.repositories.ItemRepository;
import com.devcodeops.java_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepo;
    private final UserRepository userRepo;

    public Item create(ItemDTO dto) {
        if (itemRepo.existsByNameIgnoreCase(dto.getName())) {
            throw new DuplicateFieldException("Ya existe un ítem con el nombre: " + dto.getName());
        }

        User owner = userRepo.findById(dto.getOwnerId())
                .orElseThrow(() -> new NotFoundException("Usuario con ID " + dto.getOwnerId() + " no encontrado"));

        Item item = Item.builder()
                .name(dto.getName())
                .owner(owner)
                .build();

        return itemRepo.save(item);
    }

    public Item update(Long id, ItemDTO dto) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Item con ID " + id + " no encontrado"));

        if (dto.getName() != null && !item.getName().equalsIgnoreCase(dto.getName())) {
            if (itemRepo.existsByNameIgnoreCase(dto.getName())) {
                throw new DuplicateFieldException("Ya existe un ítem con el nombre: " + dto.getName());
            }
            item.setName(dto.getName());
        }

        if (dto.getOwnerId() != null && !dto.getOwnerId().equals(item.getOwner().getId())) {
            User owner = userRepo.findById(dto.getOwnerId())
                    .orElseThrow(() -> new NotFoundException("Usuario con ID " + dto.getOwnerId() + " no encontrado"));
            item.setOwner(owner);
        }

        return itemRepo.save(item);
    }

    public Item getById(Long id) {
        return itemRepo.findById(id).orElseThrow(() -> new NotFoundException("Ítem con ID " + id + " no encontrado"));
    }

    public List<Item> getItemsByUserId(Long userId) {
        return itemRepo.findByOwnerId(userId);
    }
}
