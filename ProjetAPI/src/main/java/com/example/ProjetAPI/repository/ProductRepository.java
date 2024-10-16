package com.example.ProjetAPI.repository;

import com.example.ProjetAPI.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// Le repository pour interagir avec la table Product dans la base de données
public interface ProductRepository extends JpaRepository<Product, Long> {
}
// on crée un repository pour l'entité product
// Le but de ce repository est de gérer
// les opérations (CRUD) de manipulation de la BDD
// extends Jpa Repository permet de faire appel au module Spring Data JPA
//module qui est concu pour intéragir avec les bases de données
//cette interface nous permet d'intéragir avec la table product
//sans utiliser de requêtes SQL