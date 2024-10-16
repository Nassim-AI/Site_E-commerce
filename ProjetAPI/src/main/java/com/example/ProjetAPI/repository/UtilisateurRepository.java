package com.example.ProjetAPI.repository;

import com.example.ProjetAPI.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository pour l'entité Utilisateur
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
}