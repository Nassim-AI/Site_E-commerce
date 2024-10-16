<template>
    <div class="history-container">
      <h1 class="history-title">Historique des commandes</h1>
      <!-- Bouton pour retourner à la page d'accueil -->
      <button @click="backToShop" class="back-button">Retour à l'accueil</button>

      <div v-if="orders.length === 0" class="empty-history">
        <p>Aucune commande passée pour le moment.</p>
      </div>
      <table v-else class="history-table">
        <thead>
          <tr>
            <th>ID de la commande</th>
            <th>Date</th>
            <th>Montant Total</th>
            <th>Statut</th>
            <th>Détails</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.id">
            <td>{{ order.id }}</td>
            <td>{{ formatDate(order.orderDate) }}</td>
            <td>{{ order.totalPrice.toFixed(2) }}€</td>
            <td>
              <span :class="statusClass(order.status)">{{ order.status }}</span>
            </td>
            <td>
              <button @click="showOrderDetails(order)" class="details-button">Voir</button>
            </td>
            <td>
                <button v-if="order.status !== 'Annulée'" @click="cancelOrder(order.id)" class="cancel-button"> Annuler </button>
             </td>   

          </tr>
        </tbody>
      </table>
  
      <!-- Modal pour afficher les détails de la commande -->
      <div v-if="showModal" class="modal">
        <div class="modal-content">
          <span class="close-button" @click="closeModal">&times;</span>
          <h2>Détails de la commande</h2>
          <p><strong>ID de la commande :</strong> {{ selectedOrder.id }}</p>
          <p><strong>Date :</strong> {{ formatDate(selectedOrder.orderDate) }}</p>
          <p><strong>Statut :</strong> <span :class="statusClass(selectedOrder.status)">{{ selectedOrder.status }}</span></p>
          <h3>Produits commandés :</h3>
          <table class="product-table">
            <thead>
              <tr>
                <th>Produit</th>
                <th>Prix Unitaire</th>
                <th>Quantité</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="product in selectedOrder.products" :key="product.id">
                <td>{{ product.name }}</td>
                <td>{{ product.price.toFixed(2) }}€</td>
                <td>{{ product.quantityOrdered }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'UserHistory',
    data() {
      return {
        orders: [], // Stocker toutes les commandes de l'utilisateur
        showModal: false, // Gérer l'affichage du modal de détails
        selectedOrder: null // Stocker la commande sélectionnée pour les détails
      };
    },
    async created() {
      await this.fetchOrderHistory(); // Récupérer l'historique des commandes lors de la création du composant
    },
    methods: {
        async fetchOrderHistory() {
  try {
    // Récupère les informations de l'utilisateur depuis le localStorage
    const user = JSON.parse(localStorage.getItem('userData'));

    if (!user || !user.id) {
      console.error('Utilisateur non connecté ou ID utilisateur manquant.');
      return;
    }

    // Utilise axios.get pour récupérer l'historique des commandes de l'utilisateur
    const response = await axios.get(`http://localhost:8080/api/orders/user/${user.id}`);

    // Tri des commandes de la plus récente à la plus ancienne
    this.orders = response.data.sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));
  } catch (error) {
    console.error('Erreur lors de la récupération des commandes :', error);
  }
},
      async cancelOrder(orderId) {
  try {
    const url = `http://localhost:8080/api/orders/${orderId}/status?status=Annulée`;
    console.log("URL utilisée pour la requête PUT : ", url);

    await axios.put(url, null, {
      headers: {
        'Content-Type': 'application/json'
      }
    });

    // Mettre à jour le statut de la commande localement
    const order = this.orders.find(o => o.id === orderId);
    if (order) {
      order.status = 'Annulée';
      
    }
  } catch (error) {
    console.error('Erreur lors de l\'annulation de la commande :', error);
    alert('Erreur lors de l\'annulation de la commande.');
  }
}
,

      formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('fr-FR', { year: 'numeric', month: 'long', day: 'numeric' });
      },
      statusClass(status) {
        return {
          'status-processing': status === 'En traitement',
          'status-shipped': status === 'Expédiée',
          'status-delivered': status === 'Livrée',
          'status-cancelled': status === 'Annulée',
        };
      },
      showOrderDetails(order) {
        this.selectedOrder = order;
        this.showModal = true;
      },
      closeModal() {
        this.showModal = false;
        this.selectedOrder = null;
      },
      backToShop() {
  const userData = JSON.parse(localStorage.getItem('userData')); // Récupère les informations de l'utilisateur dans le localStorage
  const isUserLoggedIn = userData && userData.id; // Vérifie si les informations de l'utilisateur sont présentes
  
  if (isUserLoggedIn) {
    this.$router.push('/new-accueil'); // Redirige vers la page d'accueil des utilisateurs connectés
  } else {
    this.$router.push('/'); // Redirige vers la page d'accueil des non-connectés
  }
}
    }
  };
  </script>
  
  <style scoped>
  .history-container {
    max-width: 1000px;
    margin: 50px auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 10px;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  }
  
  .history-title {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
    color: #333;
  }
  
  .history-table {
    width: 100%;
    border-collapse: collapse;
    margin: 20px 0;
  }
  
  .history-table th,
  .history-table td {
    text-align: center;
    padding: 15px;
    border-bottom: 1px solid #ddd;
  }
  
  .history-table th {
    background-color: #3498db;
    color: white;
  }
  
  .empty-history {
    text-align: center;
    margin-top: 50px;
    color: #888;
  }
  
  .details-button {
    background-color: #3498db;
    color: white;
    border: none;
    padding: 10px;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .details-button:hover {
    background-color: #2980b9;
  }
  
  .modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .modal-content {
    background: white;
    padding: 30px;
    border-radius: 10px;
    width: 600px;
    position: relative;
  }
  
  .close-button {
    position: absolute;
    top: 10px;
    right: 20px;
    font-size: 24px;
    cursor: pointer;
  }
  
  .status-processing {
    background-color: #f39c12;
    padding: 5px 10px;
    border-radius: 5px;
    color: white;
  }
  
  .status-shipped {
    background-color: #3498db;
    padding: 5px 10px;
    border-radius: 5px;
    color: white;
  }
  
  .status-delivered {
    background-color: #2ecc71;
    padding: 5px 10px;
    border-radius: 5px;
    color: white;
  }
  
  .status-cancelled {
    background-color: #e74c3c;
    padding: 5px 10px;
    border-radius: 5px;
    color: white;
  }
  
  .product-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
  }
  
  .product-table th,
  .product-table td {
    text-align: center;
    padding: 10px;
    border-bottom: 1px solid #ddd;
  }
  
  .product-table th {
    background-color: #2980b9;
    color: white;
  }
  .cancel-button {
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.cancel-button:hover {
  background-color: #c0392b;
}
.back-button {
  background-color: #2ecc71;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  margin-bottom: 20px; /* Espace en bas pour le positionner correctement */
}

.back-button:hover {
  background-color: #27ae60;
}


  </style>
  