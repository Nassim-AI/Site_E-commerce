package com.example.ProjetAPI.repository;

import com.example.ProjetAPI.C_Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository pour l'entité Order
public interface OrderRepository extends JpaRepository<C_Order, Long> {

    List<C_Order> findByUserId(Long userId);
}
