<template>
  <div>
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
      </nav>
    </header>

    <div class="product-detail-container">
      <h1>Détail du Produit</h1>
      <div v-if="product">
        <!-- Flèches de navigation et bouton de retour à l'accueil -->
        <div class="navigation-arrows">
          <button @click="previousProduct" class="arrow left-arrow">&larr;</button>
          <button @click="nextProduct" class="arrow right-arrow">&rarr;</button>
          <button @click="backToShop" class="back-home-button">Retour à l'accueil</button>
        </div>

        <!-- Image et informations du produit -->
        <div class="product-info-section">
          <img :src="product.image" class="product-detail-image" alt="Produit" />

          <div class="product-info-text">
            <h2>{{ product.name }}</h2>
            <p><strong>Prix : </strong>{{ product.price }}€</p>
            <p><strong>Description : </strong>{{ product.description }}</p>
            <div class="icon-container">
              <button class="icon-button" @click="toggleDescription">
                <img src="@/assets/info-icon.png" class="icon" alt="Description" /> Description
              </button>
              <button class="icon-button" @click="toggleReviews">
                <img src="@/assets/review-icon.png" class="icon" alt="Avis" /> Avis
              </button>
            </div>

            <!-- Section pour ajuster la quantité et ajouter au panier -->
            <div class="add-to-cart-section">
              <button @click="decreaseQuantity">-</button>
              <input type="number" v-model.number="quantity" min="1" class="quantity-input" />
              <button @click="increaseQuantity">+</button>
              <button class="add-to-cart-button" @click="addToCart(product)">Ajouter au Panier</button>
            </div>
          </div>
        </div>

        <!-- Section des avis et description -->
        <div v-if="showDescription" class="description-section">
          <h3>Description du Produit</h3>
          <p>{{ product.description }}</p>
        </div>

        <div v-if="showReviews" class="reviews">
          <h3>Avis des clients</h3>
          <div class="reviews-container">
            <div v-if="reviews.length === 0">
              <p>Pas d'avis pour ce produit pour le moment.</p>
            </div>
            <div class="review-card" v-for="review in reviews" :key="review.id">
              <div class="review-header">
                <span class="reviewer-name">{{ review.reviewerName }}</span>
                <span class="review-rating">★ {{ review.rating }}</span>
              </div>
              <p class="review-comment">{{ review.comment }}</p>
            </div>
          </div>

          <!-- Formulaire pour ajouter un avis si l'utilisateur est connecté -->
          <div v-if="isUserConnected">
            <h3>Ajouter un avis</h3>
            <form @submit.prevent="submitReview" class="review-form">
              <div class="input-group">
                <label for="rating">Note :</label>
                <select id="rating" v-model="newReview.rating" required>
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                </select>
              </div>

              <div class="input-group">
                <label for="comment">Commentaire :</label>
                <textarea id="comment" v-model="newReview.comment" required></textarea>
              </div>

              <button type="submit" class="submit-review-button">Ajouter l'avis</button>
            </form>
          </div>
          <div v-else>
            <p>Vous devez être connecté pour donner un avis.</p>
            <button @click="redirectToLogin" class="login-button">Se connecter</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ProductDetail',
  data() {
    return {
      product: null,  // Contiendra les détails du produit
      reviews: [],    // Contiendra les avis des clients
      quantity: 1,    // Quantité du produit à ajouter
      showDescription: true,
      showReviews: false,
      productList: [], // Liste des produits pour la navigation
      currentIndex: 0, // Index actuel du produit dans la liste
      cart: JSON.parse(localStorage.getItem('cart')) || [], // Gestion du panier
      newReview: { reviewerName: '', comment: '', rating: 1 }, // Nouveau commentaire
      isUserConnected: false,  // Indicateur de connexion de l'utilisateur
      userName: '',  // Nom de l'utilisateur connecté
    };
  },
  computed: {
    cartItemCount() {
      return this.cart.reduce((acc, item) => acc + item.quantity, 0);
    },
  },
  async created() {
    await this.fetchProductList(); // Récupère tous les produits
    this.loadProduct(this.$route.params.id);
    this.checkUserConnection();
  },
  methods: {
    async fetchProductList() {
      try {
        const response = await axios.get('http://localhost:3000/api/products');
        // Ajoute le préfixe /images/ à chaque image comme dans la page d'accueil
        this.productList = response.data.map((product) => {
          return {
            ...product,
            image: `/images/${product.image}`, // Ajoute le bon chemin ici
          };
        });
      } catch (error) {
        console.error('Erreur lors de la récupération de la liste des produits:', error);
      }
    },
    loadProduct(productId) {
      // Charge les détails du produit en fonction de l'ID
      this.currentIndex = this.productList.findIndex((product) => product.id == productId);
      if (this.currentIndex !== -1) {
        this.product = this.productList[this.currentIndex];
        this.fetchProductReviews(productId);
      }
    },
    async fetchProductReviews(productId) {
      try {
        const reviewsResponse = await axios.get(`http://localhost:8080/api/products/${productId}/reviews`);
        this.reviews = reviewsResponse.data;
      } catch (error) {
        console.error('Erreur lors de la récupération des avis du produit :', error);
      }
    },
    async submitReview() {
      if (!this.isUserConnected) return this.redirectToLogin();
      try {
        const response = await axios.post(`http://localhost:8080/api/products/${this.product.id}/reviews`, this.newReview);
        this.reviews.push(response.data); // Ajoute l'avis à la liste
        this.newReview = { reviewerName: '', comment: '', rating: 1 }; // Réinitialise le formulaire
        alert('Avis ajouté avec succès !');
      } catch (error) {
        console.error('Erreur lors de l\'ajout de l\'avis :', error);
        alert('Erreur lors de l\'ajout de l\'avis.');
      }
    },
    checkUserConnection() {
  const storedUser = JSON.parse(localStorage.getItem('userData'));
  if (storedUser && storedUser.prenom) {
    this.isUserConnected = true;
    this.newReview.reviewerName = storedUser.prenom; // Utilise le prénom de l'utilisateur
  } else {
    this.isUserConnected = false;
  }
},

    previousProduct() {
      if (this.currentIndex > 0) {
        this.currentIndex -= 1;
        this.loadProduct(this.productList[this.currentIndex].id);
      }
    },
    nextProduct() {
      if (this.currentIndex < this.productList.length - 1) {
        this.currentIndex += 1;
        this.loadProduct(this.productList[this.currentIndex].id);
      }
    },
    toggleDescription() {
      this.showDescription = !this.showDescription;
      this.showReviews = false;
    },
    toggleReviews() {
      this.showReviews = !this.showReviews;
      this.showDescription = false;
    },
    increaseQuantity() {
      this.quantity += 1;
    },
    decreaseQuantity() {
      if (this.quantity > 1) {
        this.quantity -= 1;
      }
    },
    addToCart(product) {
      const cart = JSON.parse(localStorage.getItem('cart')) || [];
      const productInCart = cart.find((item) => item.id === product.id);

      if (productInCart) {
        productInCart.quantity += this.quantity;
      } else {
        cart.push({ ...product, quantity: this.quantity });
      }

      localStorage.setItem('cart', JSON.stringify(cart));
      this.cart = cart;
      alert(`${product.name} a été ajouté au panier !`);
    },
    viewCart() {
      this.$router.push('/cart');
    },
    redirectToLogin() {
  this.$router.push('/login'); // Redirige vers la page de connexion
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
  },
  watch: {
    cart: {
      handler(newCart) {
        localStorage.setItem('cart', JSON.stringify(newCart)); // Sauvegarde le panier dans le localStorage à chaque modification
      },
      deep: true,
    },
  },
};
</script>


<style scoped>

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
  
.product-detail-container {
  max-width: 900px;
  margin: auto;
  padding: 20px;
}

.navigation-arrows {
  text-align: center;
  margin-bottom: 20px;
}

.arrow {
  background: #f3f3f3;
  border: 1px solid #ddd;
  padding: 10px;
  cursor: pointer;
  margin: 0 10px;
}

.arrow:hover {
  background-color: #ddd;
}

/* Style pour le bouton de retour à l'accueil */
.back-home-button {
  background: #3498db;
  color: white;
  border: none;
  padding: 10px 15px;
  margin-left: 20px;
  border-radius: 5px;
  cursor: pointer;
}

.back-home-button:hover {
  background: #2980b9;
}

.product-info-section {
  display: flex;
}

.product-detail-image {
  width: 300px;
  height: 300px;
  border: 1px solid #ddd;
  border-radius: 10px;
}

.product-info-text {
  margin-left: 30px;
}

.icon-container {
  margin: 20px 0;
}

.icon-button {
  background: none;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.icon {
  width: 24px;
  height: 24px;
  margin-right: 8px;
}

.add-to-cart-section {
  display: flex;
  align-items: center;
  gap: 5px;
}

.add-to-cart-button {
  background-color: #d32f2f;
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  border-radius: 5px;
}

.add-to-cart-button:hover {
  background-color: #b71c1c;
}

.description-section,
.reviews {
  margin-top: 30px;
}
.input-group {
  margin-bottom: 15px;
}

input, select, textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-sizing: border-box;
}

.submit-review-button {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
}

.submit-review-button:hover {
  background-color: #2980b9;
}

.reviews-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-top: 20px;
}

/* Style pour chaque carte d'avis */
.review-card {
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}


/* En-tête de chaque avis */
.review-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

/* Nom du client */
.reviewer-name {
  font-weight: bold;
  font-size: 16px;
  color: #333;
}

/* Style pour la note de l'avis */
.review-rating {
  background-color: #ffd700;
  padding: 5px;
  border-radius: 5px;
  color: white;
}

/* Commentaire de l'avis */
.review-comment {
  font-style: italic;
  color: #555;
}

/* Formulaire d'ajout d'avis */
.review-form {
  margin-top: 20px;
}

</style>
