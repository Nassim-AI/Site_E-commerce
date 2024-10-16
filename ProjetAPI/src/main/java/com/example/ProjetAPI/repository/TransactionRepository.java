package com.example.ProjetAPI.repository;

import com.example.ProjetAPI.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository pour l'entité Transaction
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
