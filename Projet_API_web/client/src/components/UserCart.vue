<template>
    <div class="cart-container">
      <h1 class="cart-title">Votre Panier</h1>

      <!-- Affichage des messages de succès et d'erreur -->
    <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

    <div v-if="paymentStep === 1">
      <p v-if="cart.length === 0">Votre panier est vide.</p>
      <table v-else class="cart-table">
        <thead>
          <tr>
            <th>Produit</th>
            <th>Prix</th>
            <th>Quantité</th>
            <th>Sous-total</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in cart" :key="item.id">
            <td class="product-info">
              <img :src="item.image" alt="Produit" class="product-image" />
              <span class="product-name">{{ item.name }}</span>
            </td>
            <td>{{ item.price.toFixed(2) }}€</td>
            <td>
              <button @click="decreaseQuantity(item)">-</button>
              <input type="number" v-model.number="item.quantity" min="1" class="quantity-input" />
              <button @click="increaseQuantity(item)">+</button>
            </td>
            <td>{{ (item.price * item.quantity).toFixed(2) }}€</td>
            <td>
              <button @click="removeFromCart(item)" class="remove-button">Retirer</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="cart-total">
        <p>Total : <strong>{{ totalPrice }} €</strong></p>
        <div class="button-group">
          <button @click="backToShop" class="shop-button">Retourner à la boutique</button>
          <button @click="confirmOrder" class="confirm-button">Valider la commande</button>
        </div>
      </div>
    </div>
 <!-- Étape 2 : Affichage du formulaire de paiement -->
 <div v-else>
      <stripe-payment :totalPrice="totalPrice" @payment-success="confirmOrder"></stripe-payment>
      <button @click="backToCart" class="cancel-button">Annuler le paiement</button>
    </div>
  </div>

  </template>
  
  <script>
  import axios from 'axios';

import StripePayment from './StripePayment.vue';
  
  export default {
    name: 'UserCart',
    components: {
    StripePayment,  
  },
    data() {
      return {
        cart: JSON.parse(localStorage.getItem('cart')) || [], // Récupère le panier depuis le localStorage
        successMessage: '', // Message de succès
    errorMessage: '', // Message d'erreur
    paymentStep: 1,  // Étape de paiement (1: Panier, 2: Paiement)
      };
    },
    computed: {
      totalPrice() {
        return this.cart.reduce((acc, item) => acc + item.price * item.quantity, 0).toFixed(2);
      }
    },
    methods: {

        
      // Met à jour le stock de chaque produit après la commande
      async updateStockAfterOrder(products) {
        try {
          await Promise.all(
            products.map(async (item) => {
              const newStockValue = item.stock - item.quantityOrdered; // Calcul du nouveau stock pour chaque produit
              await axios.put(`http://localhost:3000/api/products/${item.id}/stock`, { stock: newStockValue });
            })
          );
          console.log('Stock mis à jour avec succès pour tous les produits.');
        } catch (error) {
          console.error('Erreur lors de la mise à jour du stock :', error);
        }
      },
  
      // Valide la commande et met à jour le stock
      async confirmOrder() {
        const user = JSON.parse(localStorage.getItem('userData'));
  
        if (!user) {
          
          this.$router.push('/login');
          return;
        }
        console.log("Utilisateur détecté :", user.nom);
  
        try {
          const newOrder = {
            customerName: user.nom || 'Nom du client',
            orderDate: new Date(),
            status: 'En traitement',
            totalPrice: parseFloat(this.totalPrice),
            products: this.cart.map(item => ({
              id: item.id,
              name: item.name,
              price: item.price,
              quantityOrdered: item.quantity, // Ajoute la quantité commandée pour chaque produit
              stock: item.stock
            })),
            userId: user.id
          };

          console.log("Commande envoyée au backend :", newOrder); // Ajoute ce log
  
          // Envoie la requête POST pour créer la commande
          const response = await axios.post('http://localhost:8080/api/orders', newOrder);
  
          if (response && response.data) {
            // Met à jour le stock après la commande
            await this.updateStockAfterOrder(newOrder.products);
            
            localStorage.removeItem('cart');
            this.cart = [];
            this.successMessage = 'Votre commande a été validée avec succès';
            
          } else {
            this.successMessage = '';
      this.errorMessage = 'Erreur lors de la création de la commande.';
          }
        } catch (error) {
          console.error('Erreur lors de la création de la commande :', error);
          this.successMessage = '';
    this.errorMessage = 'Une erreur est survenue lors de la création de la commande.';
        }
      },
  
      removeFromCart(product) {
        this.cart = this.cart.filter(item => item.id !== product.id);
        localStorage.setItem('cart', JSON.stringify(this.cart)); // Mettre à jour le localStorage
      },
      backToShop() {
  const userData = JSON.parse(localStorage.getItem('userData')); // Récupère les informations de l'utilisateur dans le localStorage
  const isUserLoggedIn = userData && userData.id; // Vérifie si les informations de l'utilisateur sont présentes
  
  if (isUserLoggedIn) {
    this.$router.push('/new-accueil'); // Redirige vers la page d'accueil des utilisateurs connectés
  } else {
    this.$router.push('/'); // Redirige vers la page d'accueil des non-connectés
  }
},
      increaseQuantity(item) {
        item.quantity += 1;
      },
      decreaseQuantity(item) {
        if (item.quantity > 1) {
          item.quantity -= 1;
        }
      }
    },
    watch: {
      cart: {
        handler(newCart) {
          localStorage.setItem('cart', JSON.stringify(newCart)); // Sauvegarde le panier dans le localStorage à chaque modification
        },
        deep: true
      }
    }
  };
  </script>
  
  <style scoped>
  /* Conteneur principal du panier */
  .cart-container {
    max-width: 900px;
    margin: auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 10px;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  }
  
  /* Titre du panier */
  .cart-title {
    text-align: center;
    color: #4b4b4b;
    margin-bottom: 20px;
    font-size: 24px;
  }
  
  /* Table des produits */
  .cart-table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
  }
  
  .cart-table th,
  .cart-table td {
    text-align: center;
    padding: 10px;
    border-bottom: 1px solid #ddd;
  }
  
  /* Style pour la première colonne (produit) */
  .product-info {
    display: flex;
    align-items: center;
  }
  
  .product-image {
    width: 60px;
    height: 60px;
    margin-right: 15px;
    border-radius: 10px;
    border: 1px solid #ddd;
  }
  
  .product-name {
    font-weight: bold;
    color: #333;
  }
  
  /* Style pour les boutons de quantité */
  button {
    background-color: #d32f2f;
    color: white;
    border: none;
    padding: 5px 10px;
    cursor: pointer;
    margin: 0 5px;
    border-radius: 5px;
    transition: background-color 0.3s;
  }
  
  button:hover {
    background-color: #b71c1c;
  }
  
  .quantity-input {
    width: 50px;
    text-align: center;
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 5px;
  }
  
  /* Style pour le bouton de suppression */
  .remove-button {
    background-color: #e74c3c;
  }
  
  .remove-button:hover {
    background-color: #c0392b;
  }
  
  /* Style pour le total */
  .cart-total {
    text-align: right;
    margin-top: 20px;
  }
  
  .cart-total p {
    font-size: 20px;
    color: #333;
  }
  
  /* Groupe de boutons */
  .button-group {
    display: flex;
    justify-content: flex-end;
    gap: 15px;
    margin-top: 20px;
  }
  
  /* Style des boutons de la page */
  .shop-button {
    background-color: #27ae60;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .shop-button:hover {
    background-color: #2ecc71;
  }
  
  /* Bouton de validation de la commande */
  .confirm-button {
    background-color: #3498db;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .confirm-button:hover {
    background-color: #2980b9;
  }


.success-message {
  background-color: #d4edda;
  color: #155724;
  padding: 15px;
  border: 1px solid #c3e6cb;
  margin-bottom: 20px;
  border-radius: 5px;
  text-align: center;
}

.error-message {
  background-color: #f8d7da;
  color: #721c24;
  padding: 15px;
  border: 1px solid #f5c6cb;
  margin-bottom: 20px;
  border-radius: 5px;
  text-align: center;
}
  </style>
  