package com.example.ProjetAPI;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewerName;  // Nom de l'utilisateur qui a laissé l'avis
    private String comment;       // Commentaire de l'utilisateur
    private int rating;           // Note (éventuellement de 1 à 5 étoiles)

    @ManyToOne
    @JoinColumn(name = "product_id") // Lien vers le produit associé
    @JsonIgnoreProperties("reviews")
    private Product product;

    // Constructeurs, Getters, et Setters
    public Review() {}

    public Review(String reviewerName, String comment, int rating, Product product) {
        this.reviewerName = reviewerName;
        this.comment = comment;
        this.rating = rating;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
