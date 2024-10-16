<template>
    <div>
      <h2>Points de Retrait à Proximité</h2>
      <p><strong>Adresse de l'utilisateur : </strong>{{ userAddress }}</p>
      <div v-if="nearbyStores.length === 0">
        <p>Aucun point de retrait trouvé autour de votre adresse.</p>
      </div>
      <ul>
        <li v-for="store in nearbyStores" :key="store.id">
          {{ store.name }} - {{ store.vicinity }}
        </li>
      </ul>
  
      <!-- Bouton pour revenir à la page d'accueil NewAccueil -->
      <button class="back-button" @click="goToNewAccueil">Retour à l'accueil</button>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'NearbyStores',
    data() {
      return {
        nearbyStores: [], // Stocke les points de retrait récupérés
        userAddress: '', // Adresse de l'utilisateur connecté
      };
    },
    async created() {
      await this.fetchUserAddress(); // Récupère l'adresse de l'utilisateur connecté
      await this.searchNearbyStores(); // Recherche les points de retrait à partir de l'adresse
    },
    methods: {
      // Récupère l'adresse de l'utilisateur depuis le backend
      async fetchUserAddress() {
        try {
          // Récupère l'ID de l'utilisateur depuis le localStorage
          const storedUser = JSON.parse(localStorage.getItem('userData'));
          const userId = storedUser ? storedUser.id : null;
  
          if (!userId) {
            console.error("Aucun utilisateur connecté.");
            return;
          }
  
          // Effectue une requête pour obtenir les détails de l'utilisateur
          const response = await axios.get(`http://localhost:8080/api/utilisateurs/${userId}`);
  
          if (response.data && response.data.adresse) {
            this.userAddress = response.data.adresse;
          } else {
            console.error("Adresse non trouvée pour l'utilisateur.");
          }
        } catch (error) {
          console.error("Erreur lors de la récupération de l'adresse de l'utilisateur :", error);
        }
      },
  
      // Recherche les points de retrait à proximité de l'adresse de l'utilisateur
      async searchNearbyStores() {
        try {
          // Étape 1 : Convertir l'adresse de l'utilisateur en latitude et longitude
          const geocodeResponse = await axios.get('https://maps.googleapis.com/maps/api/geocode/json', {
            params: {
              address: this.userAddress,
              key: 'AIzaSyCVbdSY603hpMdphqywerQk-kw8fDaI6JM' // Remplacez par votre clé API Google Maps
            }
          });
  
          if (geocodeResponse.data.results.length === 0) {
            console.error('Aucune coordonnée trouvée pour cette adresse.');
            return;
          }
  
          const location = geocodeResponse.data.results[0].geometry.location;
          const lat = location.lat;
          const lng = location.lng;
  
          // Étape 2 : Rechercher les points de retrait autour des coordonnées obtenues
          const placesResponse = await axios.get('http://localhost:8080/api/maps/places', {
            params: {
              lat: lat,
              lng: lng,
              radius: 5000, // Rayon de recherche en mètres
              type: 'store'
            }
          });
  
          this.nearbyStores = placesResponse.data.results; // Stocker les résultats dans nearbyStores
        } catch (error) {
          console.error('Erreur lors de la recherche des points de retrait :', error);
        }
      },
  
      // Fonction pour retourner à la page d'accueil NewAccueil
      goToNewAccueil() {
        this.$router.push('/new-accueil'); // Redirige vers la page d'accueil des utilisateurs connectés
      }
    }
  };
  </script>
  
  <style scoped>
  h2 {
    color: #2c3e50;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
  }
  
  li {
    padding: 10px;
    border-bottom: 1px solid #ddd;
  }
  
  .back-button {
    background-color: #3498db;
    color: white;
    border: none;
    padding: 10px 20px;
    margin-top: 20px;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .back-button:hover {
    background-color: #2980b9;
  }
  </style>
  