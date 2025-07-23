package com.devcodeops.java_backend.repositories;

import com.devcodeops.java_backend.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByNameIgnoreCase(String name);
    List<Item> findByOwnerId(Long ownerId);
}
