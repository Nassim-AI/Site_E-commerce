package com.example.ProjetAPI.controller;

import com.example.ProjetAPI.Utilisateur;
import com.example.ProjetAPI.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/utilisateurs") // Point de terminaison pour les utilisateurs
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public Utilisateur getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id);
    }

    // Inscrire un nouvel utilisateur
    @PostMapping("/inscription")
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.createUtilisateur(utilisateur);
    }

    // Connexion d'un utilisateur existant
    @GetMapping("/connexion")
    public Utilisateur loginUtilisateur(@RequestParam String email, @RequestParam String motDePasse) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(email);
        if (utilisateur != null && utilisateur.getMotDePasse().equals(motDePasse)) {
            return utilisateur;
        }
        throw new RuntimeException("Email ou mot de passe incorrect !");
    }

    // Mettre à jour les informations personnelles d'un utilisateur
    @PutMapping("/{id}")
    public Utilisateur updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateurDetails) {
        return utilisateurService.updateUtilisateur(id, utilisateurDetails);
    }

    // **NOUVEAU** : Mettre à jour l'adresse de l'utilisateur
    @PutMapping("/{id}/address")
    public Utilisateur updateUtilisateurAddress(@PathVariable Long id, @RequestParam String address) {
        return utilisateurService.updateAddress(id, address);
    }

    // **NOUVEAU** : Mettre à jour le mot de passe de l'utilisateur
    @PutMapping("/{id}/password")
    public Utilisateur updateUtilisateurPassword(@PathVariable Long id, @RequestParam String currentPassword, @RequestParam String newPassword) {
        return utilisateurService.updatePassword(id, currentPassword, newPassword);
    }

    // Supprimer un utilisateur par ID
    @DeleteMapping("/{id}")
    public void deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
    }
}
