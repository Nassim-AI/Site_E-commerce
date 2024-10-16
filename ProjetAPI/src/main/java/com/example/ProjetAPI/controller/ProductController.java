package com.example.ProjetAPI.controller;

import com.example.ProjetAPI.Product;
import com.example.ProjetAPI.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:8081")

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Récupérer tous les produits
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Récupérer un produit par ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // Récupérer tous les stocks des produits
    @GetMapping("/stocks")
    public List<Integer> getAllProductStocks() {
        return productService.getAllProductStocks();
    }

    @PutMapping("/products/{id}")
    public Product updateProductAdmin(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productService.getProductById(id);
        product.setName(productDetails.getName());
        product.setStock(productDetails.getStock());
        return productService.updateProduct(id, product);
    }


    // Récupérer le stock d'un produit spécifique par ID
    @GetMapping("/{id}/stock")
    public int getStockByProductId(@PathVariable Long id) {
        return productService.getStockByProductId(id);
    }

    // Ajouter un nouveau produit
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // Mettre à jour un produit existant
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productService.updateProduct(id, productDetails);
    }
    @PutMapping("/{id}/stock")
    public ResponseEntity<Product> updateProductStock(@PathVariable Long id, @RequestBody Map<String, Integer> stockUpdate) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        product.setStock(stockUpdate.get("stock"));
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }


    // Supprimer un produit
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
