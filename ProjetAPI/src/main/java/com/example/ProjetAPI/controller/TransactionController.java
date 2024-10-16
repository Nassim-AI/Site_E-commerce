package com.example.ProjetAPI.controller;

import com.example.ProjetAPI.Transaction;
import com.example.ProjetAPI.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Créer une nouvelle transaction associée à une commande
    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction, @RequestParam Long orderId) {
        return transactionService.createTransaction(transaction, orderId);
    }


    // Récupérer toutes les transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }


    // Récupérer le statut de toutes les transactions avec leurs IDs
    @GetMapping("/status")
    public List<Map<String, Object>> getAllTransactionStatuses() {
        return transactionService.getAllTransactionStatuses();
    }

    // Récupérer le statut d'une transaction spécifique par ID
    @GetMapping("/{id}/status")
    public String getTransactionStatusById(@PathVariable Long id) {
        return transactionService.getTransactionStatusById(id);
    }

    // Récupérer une transaction spécifique par ID
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    // Mettre à jour le statut d'une transaction
    @PutMapping("/{id}/status")
    public Transaction updateTransactionStatus(@PathVariable Long id, @RequestParam String status) {
        return transactionService.updateTransactionStatus(id, status);
    }

    // Supprimer une transaction
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
