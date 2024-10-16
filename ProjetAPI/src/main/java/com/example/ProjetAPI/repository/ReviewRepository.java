package com.example.ProjetAPI.repository;

import com.example.ProjetAPI.Review;
import com.example.ProjetAPI.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
}
