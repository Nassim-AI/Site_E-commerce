package com.example.ProjetAPI.service;

import com.example.ProjetAPI.C_Order;
import com.example.ProjetAPI.Transaction;
import com.example.ProjetAPI.repository.OrderRepository;
import com.example.ProjetAPI.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Créer une nouvelle transaction associée à une commande
    public Transaction createTransaction(Transaction transaction, Long orderId) {
        // Récupérer la commande associée en utilisant l'ID de commande (orderId)
        C_Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new RuntimeException("Commande avec l'ID " + orderId + " non trouvée !"));

        // Associer la commande à la transaction et synchroniser les informations
        transaction.setOrder(order);
        transaction.setCustomerName(order.getCustomerName());  // Utilise le customerName de la commande


        return transactionRepository.save(transaction);
    }


    // Récupérer toutes les transactions (historique complet)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Récupérer une transaction par ID
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public List<Map<String, Object>> getAllTransactionStatuses() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<Map<String, Object>> statusList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            Map<String, Object> statusMap = new HashMap<>();
            statusMap.put("id", transaction.getId());
            statusMap.put("status", transaction.getStatus());
            statusList.add(statusMap);
        }
        return statusList;
    }

    // Récupérer le statut d'une transaction par ID
    public String getTransactionStatusById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            return transaction.getStatus();
        }
        return "Statut non trouvé pour la transaction avec l'ID " + id;
    }

    // Mettre à jour le statut d'une transaction
    public Transaction updateTransactionStatus(Long id, String status) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            Transaction existingTransaction = transaction.get();
            existingTransaction.setStatus(status);
            return transactionRepository.save(existingTransaction);
        }
        return null;
    }

    // Supprimer une transaction : annulation du paiement avant traitement
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
