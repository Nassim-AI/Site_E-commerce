package com.example.ProjetAPI.service;

import com.example.ProjetAPI.Product;
import com.example.ProjetAPI.Review;
import com.example.ProjetAPI.repository.ProductRepository;
import com.example.ProjetAPI.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    // Ajouter un avis pour un produit
    public Review addReview(Long productId, Review review) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            review.setProduct(product.get());
            return reviewRepository.save(review);
        }
        throw new RuntimeException("Produit non trouvé pour l'ID : " + productId);
    }

    // Récupérer tous les avis pour un produit spécifique
    public List<Review> getReviewsByProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.map(reviewRepository::findByProduct).orElse(null);
    }
}
