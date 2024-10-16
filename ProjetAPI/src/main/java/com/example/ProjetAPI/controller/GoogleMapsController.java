package com.example.ProjetAPI.controller;

import com.example.ProjetAPI.service.GoogleMapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081") // Adaptez si nécessaire
@RestController
@RequestMapping("/api/maps")
public class GoogleMapsController {

    @Autowired
    private GoogleMapsService googleMapsService;

    // Point de terminaison pour récupérer la géolocalisation d'une adresse
    @GetMapping("/geocode")
    public String getGeocode(@RequestParam String address) {
        return googleMapsService.getGeocode(address);
    }

    // Point de terminaison pour obtenir les magasins à proximité
    @GetMapping("/places")
    public String getNearbyPlaces(@RequestParam double lat, @RequestParam double lng,
                                  @RequestParam int radius, @RequestParam String type) {
        return googleMapsService.getNearbyPlaces(lat, lng, radius, type);
    }
}
