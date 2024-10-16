package com.example.ProjetAPI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GoogleMapsService {

    @Value("${google.maps.api.key}") // Récupérer la clé d'API depuis application.properties
    private String apiKey;

    private static final String GEOCODE_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

    // Méthode pour obtenir les coordonnées géographiques d'une adresse
    public String getGeocode(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = UriComponentsBuilder.fromHttpUrl(GEOCODE_API_URL)
                .queryParam("address", address)
                .queryParam("key", apiKey)
                .toUriString();
        return restTemplate.getForObject(uri, String.class);
    }

    // Méthode pour obtenir les magasins à proximité d'une localisation
    public String getNearbyPlaces(double lat, double lng, int radius, String type) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = UriComponentsBuilder.fromHttpUrl(PLACES_API_URL)
                .queryParam("location", lat + "," + lng)
                .queryParam("radius", radius)
                .queryParam("type", type) // Ex : type = "store"
                .queryParam("key", apiKey)
                .toUriString();
        return restTemplate.getForObject(uri, String.class);
    }
}
