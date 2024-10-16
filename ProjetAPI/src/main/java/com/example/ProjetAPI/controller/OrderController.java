package com.example.ProjetAPI.controller;

import com.example.ProjetAPI.C_Order;
import com.example.ProjetAPI.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Récupérer toutes les commandes
    @GetMapping
    public List<C_Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Récupérer une commande spécifique par ID
    @GetMapping("/{id}")
    public C_Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Créer une nouvelle commande avec userId comme paramètre
    @PostMapping
    public C_Order createOrder(@RequestBody C_Order order) {
        Long userId = order.getUserId();  // Récupère l'ID utilisateur directement depuis l'objet `order`
        return orderService.createOrder(order, userId);
    }



    // Mettre à jour le statut d'une commande
    @PutMapping("/{id}/status")
    public C_Order updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

    // Annuler une commande
    @DeleteMapping("/{id}")
    public void cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }

    // Nouvelle route pour récupérer les commandes par ID utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<C_Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<C_Order> userOrders = orderService.findOrdersByUserId(userId);
        if (userOrders == null || userOrders.isEmpty()) {
            return ResponseEntity.noContent().build();  // Retourne 204 si aucune commande n'est trouvée
        }
        return ResponseEntity.ok(userOrders);  // Retourne 200 avec la liste des commandes
    }


}