package com.example.ProjetAPI.service;

import com.example.ProjetAPI.C_Order;
import com.example.ProjetAPI.Product;
import com.example.ProjetAPI.repository.OrderRepository;
import com.example.ProjetAPI.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // Récupérer toutes les commandes
    public List<C_Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Récupérer les détails d'une commande par ID
    public C_Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // Méthode pour créer une nouvelle commande
    // Méthode pour créer une nouvelle commande avec userId
    public C_Order createOrder(C_Order order, Long userId) {
        order.setUserId(userId);  // Associer l'ID de l'utilisateur à la commande
        List<Product> existingProducts = new ArrayList<>();

        for (Product product : order.getProducts()) {
            Product existingProduct = productRepository.findById(product.getId()).orElseThrow(() ->
                    new RuntimeException("Produit avec l'ID " + product.getId() + " non trouvé !"));

            if (existingProduct.getStock() < 1) {
                throw new RuntimeException("Stock insuffisant pour le produit avec ID " + product.getId());
            }

            existingProduct.setStock(existingProduct.getStock() - 1);
            productRepository.save(existingProduct);
            existingProducts.add(existingProduct);
        }

        order.setProducts(existingProducts);
        return orderRepository.save(order);
    }


    // Mettre à jour le statut d'une commande
    public C_Order updateOrderStatus(Long id, String newStatus) {
        Optional<C_Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            C_Order existingOrder = order.get();
            existingOrder.setStatus(newStatus);
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    // Annuler une commande et restaurer le stock des produits
    public void cancelOrder(Long id) {
        Optional<C_Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            C_Order order = orderOptional.get();

            // Restaure le stock des produits dans la commande
            for (Product product : order.getProducts()) {
                Product existingProduct = productRepository.findById(product.getId()).orElse(null);
                if (existingProduct != null) {
                    existingProduct.setStock(existingProduct.getStock() + 1);  // Réaugmente le stock
                    productRepository.save(existingProduct);
                }
            }

            orderRepository.deleteById(id);  // Supprime la commande
        } else {
            throw new RuntimeException("Commande avec l'ID " + id + " non trouvée.");
        }
    }
    // Récupérer toutes les commandes d'un utilisateur par son ID
    public List<C_Order> findOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
