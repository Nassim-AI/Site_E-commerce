<template>
    <div class="admin-orders">
      <!-- Header de la page de gestion des commandes -->
      <header class="admin-header">
        <div class="header-content">
          <h1>Gestion des Commandes</h1>
          <p>Vous pouvez consulter les détails des commandes et mettre à jour leur statut.</p>
        </div>
      </header>

      
    <!-- Bouton de retour à l'accueil admin -->
    <div class="button-container">
      <button @click="backToAccueil" class="back-button">Retour à l'accueil</button>
    </div>
  
      <div class="orders-list">
        <!-- Affichage de chaque commande dans une carte -->
        <div v-for="order in orders" :key="order.id" class="order-card">
          <h2>Commande ID: {{ order.id }}</h2>
          <p><strong>Client :</strong> {{ order.customerName }}</p>
          <p><strong>Date de la commande :</strong> {{ formatDate(order.orderDate) }}</p>
          <p><strong>Montant total :</strong> {{ order.totalPrice.toFixed(2) }}€</p>
          
          <!-- Sélection du statut de la commande -->
          <div class="input-group">
            <label for="status">Statut :</label>
            <select v-model="order.status" @change="updateOrderStatus(order)">
              <option value="En traitement">En traitement</option>
              <option value="Expédiée">Expédiée</option>
              <option value="Livrée">Livrée</option>
              <option value="Annulée">Annulée</option>
            </select>
          </div>
  
          <!-- Bouton pour voir les détails de la commande -->
          <button @click="showOrderDetails(order)" class="details-button">Voir Détails</button>
          <p v-if="order.successMessage" class="success-message">{{ order.successMessage }}</p>
          <p v-if="order.errorMessage" class="error-message">{{ order.errorMessage }}</p>
        </div>
      </div>
  
      <!-- Modal pour afficher les détails de la commande -->
      <div v-if="showModal" class="modal">
        <div class="modal-content">
          <span class="close-button" @click="closeModal">&times;</span>
          <h2>Détails de la commande</h2>
          <p><strong>ID de la commande :</strong> {{ selectedOrder.id }}</p>
          <p><strong>Client :</strong> {{ selectedOrder.customerName }}</p>
          <p><strong>Date :</strong> {{ formatDate(selectedOrder.orderDate) }}</p>
          <p><strong>Montant total :</strong> {{ selectedOrder.totalPrice.toFixed(2) }}€</p>
          <h3>Produits commandés :</h3>
          <ul>
            <li v-for="product in selectedOrder.products" :key="product.id">
              {{ product.name }} 
            </li>
          </ul>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'AdminOrders',
    data() {
      return {
        orders: [], // Liste des commandes
        showModal: false, // Affichage du modal de détails
        selectedOrder: null // Commande sélectionnée
      };
    },
    async created() {
      await this.fetchOrders(); // Récupérer les commandes au chargement de la page
    },
    methods: {
      // Récupérer la liste des commandes depuis le backend
      async fetchOrders() {
        try {
          const response = await axios.get('http://localhost:8080/api/orders');
          this.orders = response.data.map((order) => ({
            ...order,
            successMessage: '',
            errorMessage: ''
          }));
        } catch (error) {
          console.error('Erreur lors de la récupération des commandes :', error);
        }
      },
      // Mettre à jour le statut de la commande
      async updateOrderStatus(order) {
        try {
          const response = await axios.put(`http://localhost:8080/api/orders/${order.id}/status?status=${order.status}`);
          if (response.status === 200) {
            order.successMessage = 'Statut mis à jour avec succès !';
            order.errorMessage = '';
          }
        } catch (error) {
          console.error('Erreur lors de la mise à jour du statut :', error);
          order.successMessage = '';
          order.errorMessage = 'Échec de la mise à jour du statut.';
        }
      },
      // Afficher les détails de la commande
      showOrderDetails(order) {
        this.selectedOrder = order;
        this.showModal = true;
      },
      backToAccueil() {
      this.$router.push({ name: 'AdminDashboard' });
    },
      // Fermer le modal de détails
      closeModal() {
        this.showModal = false;
        this.selectedOrder = null;
      },
      // Formater la date pour l'affichage
      formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('fr-FR', { year: 'numeric', month: 'long', day: 'numeric' });
      }
    }
  };
  </script>
  
  <style scoped>
  /* Styles généraux pour la page des commandes */
  .admin-orders {
    font-family: 'Arial', sans-serif;
    background-color: #f9f9f9;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
  }
  
  /* Header personnalisé */
  .admin-header {
    background-color: #ab5e4e;
    color: white;
    padding: 20px;
    text-align: center;
  }
  
  .header-content h1 {
    margin: 0;
  }
  
  .header-content p {
    margin: 5px 0;
    color: #dcdcdc;
  }
  
  /* Liste de commandes */
  .orders-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    padding: 20px;
  }
  
  /* Carte individuelle pour chaque commande */
  .order-card {
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 20px;
    width: 300px;
    margin: 20px;
    text-align: center;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .order-card h2 {
    margin: 0;
  }
  
  .input-group {
    margin: 10px 0;
  }
  
  .input-group label {
    display: block;
    font-weight: bold;
  }
  
  .input-group select {
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-sizing: border-box;
  }
  
  /* Bouton de mise à jour */
  .details-button {
    background-color: #ab5e4e;
    color: white;
    border: none;
    padding: 10px;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 10px;
  }
  
  .details-button:hover {
    background-color: #7e483a;
  }
  
  /* Messages de succès et d'erreur */
  .success-message {
    color: green;
    margin-top: 10px;
  }
  
  .error-message {
    color: red;
    margin-top: 10px;
  }
  
  /* Styles du modal */
  .modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .modal-content {
    background: #fff;
    padding: 20px;
    width: 50%;
    border-radius: 8px;
    text-align: left;
    position: relative;
  }
  
  .close-button {
    position: absolute;
    top: 10px;
    right: 20px;
    cursor: pointer;
  }

  .button-container {
  display: flex;
  justify-content: flex-start;
  margin: 20px;
}

.back-button {
  background-color: #727070;
  color: #ffffff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.back-button:hover {
  background-color: #5a5a5a;
}
  </style>
  