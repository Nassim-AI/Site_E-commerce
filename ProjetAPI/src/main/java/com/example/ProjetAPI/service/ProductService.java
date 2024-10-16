package com.example.ProjetAPI.service;

import com.example.ProjetAPI.Product;
import com.example.ProjetAPI.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Récupérer tous les produits
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Récupérer un produit par ID, donc un produit spécifique
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Ajouter un nouveau produit
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Supprimer un produit
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Mettre à jour un produit existant
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStock(productDetails.getStock());
            return productRepository.save(product);
        }
        return null;
    }

    // Méthode pour récupérer uniquement les stocks de tous les produits
    public List<Integer> getAllProductStocks() {
        // Récupérer tous les produits et extraire uniquement le stock de chacun
        return productRepository.findAll().stream()
                .map(Product::getStock) // On utilise le getter pour obtenir le stock
                .collect(Collectors.toList()); // Transformer en liste d'entiers
    }


    // Méthode pour vérifier le stock d'un produit spécifique par ID
    public int getStockByProductId(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return product.getStock();
        }
        return 0; // Retourne 0 si le produit n'existe pas
    }
}
