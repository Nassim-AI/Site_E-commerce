package com.example.ProjetAPI.service;

import com.example.ProjetAPI.Utilisateur;
import com.example.ProjetAPI.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Récupérer tous les utilisateurs
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // Récupérer un utilisateur par ID
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    // Récupérer un utilisateur par Email
    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email).orElse(null);
    }

    // Ajouter un nouvel utilisateur (inscription)
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    // Mettre à jour les informations d'un utilisateur
    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
        if (utilisateur != null) {
            utilisateur.setNom(utilisateurDetails.getNom());
            utilisateur.setPrenom(utilisateurDetails.getPrenom());
            utilisateur.setEmail(utilisateurDetails.getEmail());
            utilisateur.setAdresse(utilisateurDetails.getAdresse());
            utilisateur.setMotDePasse(utilisateurDetails.getMotDePasse());
            return utilisateurRepository.save(utilisateur);
        }
        return null;
    }

    // **NOUVEAU** : Mettre à jour l'adresse de l'utilisateur
    public Utilisateur updateAddress(Long id, String newAddress) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
        if (utilisateur != null) {
            utilisateur.setAdresse(newAddress);
            return utilisateurRepository.save(utilisateur);
        }
        return null;
    }

    // **NOUVEAU** : Mettre à jour le mot de passe de l'utilisateur
    public Utilisateur updatePassword(Long id, String currentPassword, String newPassword) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
        if (utilisateur != null && utilisateur.getMotDePasse().equals(currentPassword)) {
            utilisateur.setMotDePasse(newPassword);
            return utilisateurRepository.save(utilisateur);
        }
        throw new RuntimeException("Le mot de passe actuel est incorrect.");
    }

    // Supprimer un utilisateur
    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
}
