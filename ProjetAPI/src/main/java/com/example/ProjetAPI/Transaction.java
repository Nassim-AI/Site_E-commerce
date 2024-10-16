package com.example.ProjetAPI;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;  // Nom du client
    private double amount;        // Montant de la transaction
    private String status;        // Statut de la transaction (validée, échouée)
    private Date transactionDate; // Date de la transaction


    // Relation avec la commande
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private C_Order order;  // Lien vers la commande associée

    // Constructeurs
    public Transaction() {}

    public Transaction(String customerName, double amount, String status, Date transactionDate, C_Order order) {
        this.customerName = customerName;
        this.amount = amount;
        this.status = status;
        this.transactionDate = transactionDate;
        this.order = order;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public C_Order getOrder() {
        return order;
    }

    public void setOrder(C_Order order) {
        this.order = order;
    }

}
