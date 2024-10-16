package com.example.ProjetAPI;

import jakarta.persistence.*;

import java.util.List;

@Entity // cette annotation est utilisée dans le cadre de JPA. On indique avec ca à JPA qu'il
//faut mapper/relier la table product à une table dans une base de données
// c'est de cette manière qu'on va pouvoir réaliser les opérations CRUD
@Table(name = "Product")
public class Product {
    @Id //La clé primaire qui va identifier de manière unique les différents enregistrements d'une table
    // l'annotation se trouve juste avant l'attribut id donc il sera pris comme clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // l'annotation @generatedvalue indique que la clé primaire va être générée automatiquement
    // cependant on va spécifier la méthode de génération
    // la valeur id sera auto incrémentée par la bdd à chaque nouvel enregistrement
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock; // Quantité disponible du produit, si 0-->rupture de stock


    private String image;


    // Relation One-to-Many avec Review
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    // Getters et Setters pour la liste d'avis
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    // Constructeur sans paramètre
    public Product() {
    }

    // Constructeur avec paramètres
    public Product(String name, String description, double price, int stock, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.image = image;  // Initialisation du champ `image`
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {  // Getter pour l'image
        return image;
    }

    public void setImage(String image) {  // Setter pour l'image
        this.image = image;
    }
}