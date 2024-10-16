<template>
    <div class="admin-page">
      <!-- Header personnalisé pour l'administrateur -->
      <header class="admin-header">
      <div class="header-content">
        <h1>Bienvenue sur le Tableau de Bord Administrateur !</h1>
        <button class="logout-button" @click="logout">
          <img src="@/assets/logout-icon.png" alt="Logout" class="logout-icon" />
        </button> <!-- Bouton de déconnexion avec une icône -->
      </div>
    </header>
  
      <!-- Section de services avec les icônes -->
      <div class="services-section">
        <div class="service-card" @click="viewAdminOrders">
          <img src="@/assets/order-icon.png" class="service-icon" alt="Commandes" />
          <h2>Commandes</h2>
          <p>Gérer toutes les commandes des clients</p>
        </div>
  
        <div class="service-card" @click="viewAdminProducts">
          <img src="@/assets/security-icon.png" class="service-icon" alt="Produits" />
          <h2>Produits</h2>
          <p>Ajouter, modifier ou supprimer des produits</p>
        </div>
      </div>
  
      <!-- Footer personnalisé pour l'administrateur -->
      <footer class="main-footer">
        <div class="footer-content">
          <h2>Pâtisserie de Nassim - Administration</h2>
          <p>Des pâtisseries délicieuses, faites avec amour et gérées avec professionnalisme.</p>
          <p>&copy; 2024 Flexi Pâtisserie. Tous droits réservés.</p>
        </div>
      </footer>
    </div>
  </template>
  
  <script>
  export default {
    name: 'AdminDashboard',
    methods: {
      viewAdminOrders() {
        this.$router.push('/admin/orders');
      },
      viewAdminProducts() {
        this.$router.push('/admin/products');
      },
      logout() {
        localStorage.removeItem('userData'); // Supprime les informations de session de l'administrateur
        this.$router.push('/'); // Redirige vers la page de connexion
      }
    },
  };
  </script>
  
  <style scoped>
/* Styles pour la page administrateur */
.admin-page {
  font-family: 'Arial', sans-serif;
  background-color: #f9f9f9;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

/* Header personnalisé */
.admin-header {
  background-color: #4e67ab;
  color: white;
  padding: 20px;
  text-align: center;
  position: relative;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-content h1 {
  margin: 0;
}

/* Bouton de déconnexion en cercle violet */
.logout-button {
  background-color: transparent; /* Fond transparent */
  border: none;
  width: 40px;
  height: 40px;
  border-radius: 50%; /* Forme de cercle */
  cursor: pointer;
  margin-right: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: transform 0.3s ease; /* Ajoute une transition pour l'animation */
}

/* Effet au survol */
.logout-button:hover {
  transform: scale(1.1); /* Agrandit légèrement le bouton au survol */
}

/* Style de l'icône de déconnexion */
.logout-icon {
  width: 30px; /* Taille de l'icône */
  height: 30px;
}

/* Section de services avec icônes */
.services-section {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  padding: 20px;
}

.service-card {
  background: #fff;
  width: 300px;
  height: 200px;
  margin: 20px;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  text-align: center;
  cursor: pointer;
  transition: box-shadow 0.3s ease-in-out;
}

.service-card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.service-icon {
  width: 50px;
  height: 50px;
  margin-bottom: 15px;
}

/* Styles du footer */
.main-footer {
  background-color: #727070;
  color: #ecf0f1;
  text-align: center;
  padding: 20px 0;
}

.footer-content {
  max-width: 800px;
  margin: auto;
}

.footer-links a {
  color: #ecf0f1;
  text-decoration: none;
  margin: 0 10px;
}

.footer-links a:hover {
  text-decoration: underline;
}
</style>