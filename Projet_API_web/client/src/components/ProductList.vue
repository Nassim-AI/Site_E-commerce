<template>
    <div>
      <h1>Liste des produits</h1>
      <button @click="fetchProducts">Afficher les produits</button>
      <ul v-if="products.length">
        <li v-for="product in products" :key="product.id">
          <strong>{{ product.name }}</strong> - {{ product.price }} €
        </li>
      </ul>
      <p v-else>Aucun produit disponible</p>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        products: [],
      };
    },
    methods: {
      fetchProducts() {
        axios
          .get('http://localhost:3000/api/products')
          .then((response) => {
            this.products = response.data;
          })
          .catch((error) => {
            console.error('Erreur lors de la récupération des produits :', error);
          });
      },
    },
  };
  </script>
  
  <style scoped>
  h1 {
    color: #42b983;
  }
  </style>
  