<template>
    <div>
      <!-- Header principal avec icône du panier, le texte centré, et l'icône de connexion à droite -->
      <header class="main-header">
        <nav class="header-content">
          <div class="cart-container">
            <button class="cart-icon" @click="viewCart">
              🛒 <span class="cart-count">{{ cartItemCount }}</span>
            </button>
          </div>
          <!-- Conteneur pour centrer le texte sur la page -->
          <div class="site-name-container">
            <span class="site-name">Pâtisserie de Nassim</span>
          </div>
          <!-- Conteneur de connexion avec l'icône de connexion -->
          <div class="login-container">
            <button class="rond-icon" @click="logoutUser">
  <img src="@/assets/logout-icon.png" alt="Déconnexion" />
</button>


          </div>
        </nav>
      </header>
  
      <!-- Contenu principal de la page d'accueil avec les produits -->
      <div class="accueil">
        <h1>Tu nous avais manqué {{ userName }}</h1>
        <p>Découvre nos délicieuses pâtisseries !</p>
        <div class="products-container">
          <div class="product" v-for="product in products" :key="product.id">
            <img :src="product.image" :alt="product.name" class="product-image" @click="goToProductDetail(product.id)" />
            <h2>{{ product.name }}</h2>
            <p>{{ product.price }}€ TTC</p>
            <p>{{ product.stock }} en stock </p>
            <button @click="addToCart(product)">Ajouter au panier</button>
          </div>
        </div>
      </div>
  
      <!-- Boutons placés en bas de la page -->
      <div class="bottom-buttons">
        <button class="history-button" @click="goToOrderHistory">Voir l'historique des commandes</button>
        <button class="account-info-button" @click="goToInformation">Voir les informations du compte</button>
        <button class="account-info-button" @click="goToNearbyStores">Points de retrait à proximité de chez moi</button>
      </div>
  
      <!-- Footer personnalisé -->
      <footer class="main-footer">
        <div class="footer-content">
          <h2>Pâtisserie de Nassim</h2>
          <p>Des pâtisseries délicieuses, faites avec passion.</p>
          <p>&copy; 2024 Pâtisserie de Nassim. Tous droits réservés.</p>
        </div>
      </footer>
    </div>
  </template>
  
  <script>
import axios from 'axios';

export default {
  name: 'UserAccueil',
  data() {
    return {
      products: [],
      cart: JSON.parse(localStorage.getItem('cart')) || [],
      userName: '', // Nouvelle propriété pour stocker le prénom de l'utilisateur
    };
  },
  computed: {
    cartItemCount() {
      return this.cart.reduce((acc, item) => acc + item.quantity, 0);
    },
  },
  methods: {
    addToCart(product) {
      const cart = JSON.parse(localStorage.getItem('cart')) || [];
      const productInCart = cart.find((item) => item.id === product.id);

      if (productInCart) {
        productInCart.quantity += 1;
      } else {
        cart.push({ ...product, quantity: 1 });
      }

      localStorage.setItem('cart', JSON.stringify(cart));
      this.cart = cart;
      
    },
    viewCart() {
      this.$router.push('/cart');
    },
    goToLogin() {
      this.$router.push('/login'); // Redirection vers la page de connexion
    },
    goToOrderHistory() {
    this.$router.push('/user-history'); // Assurez-vous que cette route est définie dans votre fichier de routes
  },
  goToInformation() {
    this.$router.push('/modif-infos'); // Assurez-vous que cette route est définie dans votre fichier de routes
  },
  goToNearbyStores() {
      this.$router.push('/nearby-stores'); // Redirige vers les points de retrait à proximité
    },
  goToProductDetail(productId) {
    this.$router.push(`/product/${productId}`); // Navigation vers la page de détail du produit
  },

    logoutUser() {
      // Supprimer les informations de l'utilisateur du localStorage
      localStorage.removeItem('userData');
      localStorage.removeItem('cart'); // Si tu veux également vider le panier
      this.$router.push('/'); // Redirection vers la page de connexion après déconnexion
    },
    async fetchProducts() {
      try {
        const response = await axios.get('http://localhost:3000/api/products');
        this.products = response.data.map((product) => {
          return {
            ...product,
            image: `/images/${product.image}`,
          };
        });
      } catch (error) {
        console.error('Erreur lors de la récupération des produits :', error.message);
      }
    },
    fetchUserName() {
      // Récupérer le prénom de l'utilisateur depuis le localStorage
      const user = JSON.parse(localStorage.getItem('userData'));
      if (user && user.prenom) {
        this.userName = user.prenom; // Stocker le prénom de l'utilisateur
      }
    },
  },
  mounted() {
    this.fetchProducts();
    this.fetchUserName(); // Récupérer le prénom de l'utilisateur à chaque montage de la page
  },
  watch: {
    cart: {
      handler(newCart) {
        localStorage.setItem('cart', JSON.stringify(newCart));
      },
      deep: true,
    },
  },
};
</script>

  
  <style scoped>
  /* Styles pour la barre du haut */
  .top-bar {
    background-color: #727070;
    color: white;
    text-align: center;
    font-size: 14px;
    padding: 5px 0;
    display: flex;
    justify-content: center;
    padding: 10px 20px;
  }
  
  /* Styles pour le conteneur de l'en-tête */
  .main-header {
    background-color: #f8f8f8;
    border-bottom: 1px solid #ddd;
  }
  
  .header-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px 20px;
    position: relative;
  }
  
  /* Conteneur du panier */
  .cart-container {
    display: flex;
    align-items: center;
    flex: 0.75;
  }
  
  /* Styles pour le texte au centre */
  .site-name-container {
    flex: 20;
    display: flex;
    justify-content: center;
  }
  
  .site-name {
    font-size: 24px;
    font-family: 'Pacifico', cursive;
    color: #5a4d8b;
  }
  
  /* Conteneur de l'icône de connexion */
  .login-container {
    flex: 1;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
  
  /* Icône de connexion */
  .login-icon {
    width: 30px;
    height: 30px;
    cursor: pointer;
    margin-left: 15px;
  }
  
  .login-icon:hover {
    filter: brightness(1.2);
  }
  
  /* Icône du panier */
  .cart-icon {
    display: flex;
    align-items: center;
    font-size: 20px;
    border: none;
    background: none;
    cursor: pointer;
    position: relative;
  }
  
  .cart-count {
    background-color: #d32f2f;
    color: white;
    border-radius: 50%;
    padding: 5px 8px;
    font-size: 12px;
    position: absolute;
    top: -10px;
    right: -10px;
  }
  
  .cart-icon:hover {
    filter: brightness(1.2);
    background-color: #e0e0e0;
  }
  
  /* Styles de la section accueil */
  .accueil {
    text-align: center;
    margin-top: 30px;
  }
  
  /* Conteneur des boutons en bas */
  .bottom-buttons {
    text-align: center;
    margin: 30px 0;
  }
  
  .history-button,
  .account-info-button {
    background-color: #6a4ed9;
    color: white;
    border: none;
    padding: 10px 20px;
    margin: 0 10px;
    cursor: pointer;
  }
  
  .history-button:hover,
  .account-info-button:hover {
    background-color: #8a71e2;
  }
  
  /* Styles des produits */
  .products-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
  
  .product {
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 15px;
    margin: 15px;
    width: 200px;
  }
  
  .product-image {
    width: 100px;
    height: 100px;
    margin-bottom: 10px;
  }
  
  /* Bouton des produits */
  button {
    background-color: #c50eee;
    color: white;
    border: none;
    padding: 10px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #ef01fb;
  }
  
  /* Styles du pied de page */
  .main-footer {
    background-color: #727070;
    color: #ecf0f1;
    text-align: center;
    padding: 3px 0;
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

/* Styles pour le bouton rond de déconnexion */
.rond-icon {
  width: 40px; /* Largeur du bouton */
  height: 40px; /* Hauteur du bouton */
  background-color: #f44336; /* Couleur de fond (rouge) */
  border-radius: 50%; /* Pour rendre le bouton rond */
  border: none; /* Pas de bordure */
  display: flex; /* Pour centrer l'icône à l'intérieur */
  align-items: center; /* Centrer verticalement */
  justify-content: center; /* Centrer horizontalement */
  cursor: pointer; /* Curseur pointeur */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Légère ombre pour donner du relief */
  transition: background-color 0.3s, transform 0.3s; /* Animation de transition */
}

.rond-icon:hover {
  background-color: #d32f2f; /* Couleur de fond au survol */
  transform: scale(1.1); /* Légère mise à l'échelle au survol */
}

.rond-icon img {
  width: 24px; /* Largeur de l'image à l'intérieur */
  height: 24px; /* Hauteur de l'image à l'intérieur */
}

/* Styles des produits */
.products-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
  
  .product {
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 15px;
    margin: 15px;
    width: 200px;
    transition: transform 0.3s ease-in-out;
  }
  
  .product-image {
    width: 100px;
    height: 100px;
    margin-bottom: 10px;
    transition: transform 0.3s ease-in-out;
  }

  /* Animation de zoom sur l'image lors du survol */
.product-image:hover {
  transform: scale(1.15); /* Effet de zoom uniquement sur l'image */
  cursor: pointer; /* Indiquer que l'image est cliquable */
}

  </style>
  