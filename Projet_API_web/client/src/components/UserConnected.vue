<template>
    <div class="user-page">
      <!-- Nouveau Header pour la page utilisateur -->
      <header class="user-header">
        <div class="header-content">
          <h1>Bienvenue, {{ userData.prenom }} {{ userData.nom }} !</h1>
          <p>Voici les services disponibles pour gérer votre compte.</p>
        </div>
      </header>
  
      <!-- Section de services avec les icônes -->
      <div class="services-section">
        <div class="service-card" @click="viewOrders">
          <img src="@/assets/order-icon.png" class="service-icon" alt="Commandes" />
          <h2>Vos Commandes</h2>
          <p>Suivre, retourner ou acheter à nouveau</p>
        </div>
  
        <div class="service-card" @click="viewSecurity">
          <img src="@/assets/security-icon.png" class="service-icon" alt="Connexion et sécurité" />
          <h2>Connexion & Sécurité</h2>
          <p>Modifier l'adresse e-mail, le mot de passe, etc.</p>
        </div>
  
        <div class="service-card" @click="viewPayments">
          <img src="@/assets/payment-icon.png" class="service-icon" alt="Paiements" />
          <h2>Vos Paiements</h2>
          <p>Afficher toutes les transactions et les paramètres</p>
        </div>
      </div>
  
      <!-- Footer personnalisé -->
      <footer class="main-footer">
        <div class="footer-content">
          <h2>Flexi Pâtisserie</h2>
          <p>Des pâtisseries délicieuses, faites avec amour.</p>
          <div class="footer-links">
            <a href="#">Accueil</a> |
            <a href="#">Nos Pâtisseries</a> |
            <a href="#">Contact</a>
          </div>
          <p>&copy; 2024 Flexi Pâtisserie. Tous droits réservés.</p>
        </div>
      </footer>
    </div>
  </template>
  
  <script>
  export default {
    name: 'UserConnected',
    data() {
      return {
        userData: {},
      };
    },
    methods: {
      fetchUserData() {
        const user = localStorage.getItem('userData');
        if (user) {
          this.userData = JSON.parse(user);
        }
      },
      viewOrders() {
        this.$router.push('/orders');
      },
      viewSecurity() {
        this.$router.push('/security-settings');
      },
      viewPayments() {
        this.$router.push('/payment-settings');
      },
    },
    mounted() {
      this.fetchUserData();
    },
  };
  </script>
  
  <style scoped>
  /* Styles généraux pour la page utilisateur */
  .user-page {
    font-family: 'Arial', sans-serif;
    background-color: #f9f9f9;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  
  /* Nouveau Header */
  .user-header {
    background-color: #5a4d8b;
    color: white;
    padding: 20px;
    text-align: center;
  }
  
  .header-content h1 {
    margin: 0;
  }
  
  .header-content p {
    margin: 5px 0;
    color: #ddd;
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
  