package com.example.ProjetAPI.controller;

import com.example.ProjetAPI.Product;
import com.example.ProjetAPI.Review;
import com.example.ProjetAPI.repository.ProductRepository;
import com.example.ProjetAPI.repository.ReviewRepository;
import com.example.ProjetAPI.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/products/{productId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    // Récupérer tous les avis pour un produit spécifique
    @GetMapping
    public List<Review> getReviews(@PathVariable Long productId) {
        return reviewService.getReviewsByProduct(productId);
    }

    // Ajouter un nouvel avis pour un produit spécifique
    @PostMapping
    public Review addReview(@PathVariable Long productId, @RequestBody Review review) {
        return reviewService.addReview(productId, review);
    }
    // Endpoint pour ajouter un avis à un produit
    @PostMapping ("/new")
    public Review addReviewToProduct(@PathVariable Long productId, @RequestBody Review review) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        review.setProduct(product);
        return reviewRepository.save(review);
    }
}
