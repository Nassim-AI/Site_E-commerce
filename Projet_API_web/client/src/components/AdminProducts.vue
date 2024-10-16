<template>
  <div class="admin-products">
    <!-- Header de la page des produits -->
    <header class="admin-header">
      <div class="header-content">
        <h1>Gestion des Produits</h1>
        <p>Vous pouvez modifier les noms des produits, ajuster leurs stocks ou changer leurs prix ici.</p>
      </div>
    </header>

    <div class="products-list">
      <!-- Affichage de chaque produit dans une carte -->
      <div v-for="product in products" :key="product.id" class="product-card">
        <h2>Produit ID: {{ product.id }}</h2>
        <div class="input-group">
          <label>Nom :</label>
          <input type="text" v-model="product.name" />
        </div>
        <div class="input-group">
          <label>Prix en € :</label>
          <input type="number" v-model.number="product.price" step="0.01" min="0" /> 
        </div>
        <div class="input-group">
          <label>Stock :</label>
          <input type="number" v-model="product.stock" min="0" />
        </div>
        <button @click="updateProduct(product)" class="update-button">Mettre à jour</button>
        <p v-if="product.successMessage" class="success-message">{{ product.successMessage }}</p>
        <p v-if="product.errorMessage" class="error-message">{{ product.errorMessage }}</p>
      </div>
    </div>

    <!-- Bouton pour retourner à l'accueil -->
    <div class="button-container">
      <button @click="backToAccueil" class="back-button">Retour à l'accueil</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AdminProducts',
  data() {
    return {
      products: [] // Liste des produits
    };
  },
  async created() {
    await this.fetchProducts(); // Charger la liste des produits au chargement de la page
  },
  methods: {
    // Récupérer la liste des produits depuis l'API
    async fetchProducts() {
      try {
        const response = await axios.get('http://localhost:8080/api/products');
        this.products = response.data.map((product) => ({
          ...product,
          successMessage: '',
          errorMessage: ''
        }));
      } catch (error) {
        console.error('Erreur lors de la récupération des produits :', error);
      }
    },
    // Mettre à jour un produit (nom, stock, et prix)
    async updateProduct(product) {
      try {
        const updateData = {
          name: product.name,
          stock: product.stock,
          price: product.price // Inclure le prix dans la mise à jour
        };
        const response = await axios.put(`http://localhost:8080/api/products/${product.id}`, updateData);
        if (response.status === 200) {
          product.successMessage = 'Produit mis à jour avec succès !';
          product.errorMessage = '';
        }
      } catch (error) {
        console.error('Erreur lors de la mise à jour du produit :', error);
        product.successMessage = '';
        product.errorMessage = 'Échec de la mise à jour du produit.';
      }
    },
    backToAccueil() {
      this.$router.push({ name: 'AdminDashboard' });
    }
  }
};
</script>

<style scoped>
/* Styles pour la page de gestion des produits */
.admin-products {
  font-family: 'Arial', sans-serif;
  background-color: #f9f9f9;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* Header personnalisé */
.admin-header {
  background-color: #4e67ab;
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

/* Liste de produits */
.products-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  padding: 20px;
}

/* Carte individuelle pour chaque produit */
.product-card {
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  width: 300px;
  margin: 20px;
  text-align: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.product-card h2 {
  margin: 0;
}

.input-group {
  margin: 10px 0;
}

.input-group label {
  display: block;
  font-weight: bold;
}

.input-group input {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-sizing: border-box;
}

/* Bouton de mise à jour */
.update-button {
  background-color: #4e67ab;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
}

.update-button:hover {
  background-color: #35456b;
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

/* Bouton de retour */
.button-container {
  display: flex;
  justify-content: center;
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
