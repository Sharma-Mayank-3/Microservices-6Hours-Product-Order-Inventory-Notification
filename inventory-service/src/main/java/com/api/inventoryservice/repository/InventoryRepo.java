package com.api.inventoryservice.repository;

import com.api.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {

    Optional<Inventory> findByProductId(int productId);
}
