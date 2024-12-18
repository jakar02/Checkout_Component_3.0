package com.example.Checkout_Component.repository;

import com.example.Checkout_Component.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
