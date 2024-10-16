<template>
  <div class="login-container">
    <!-- Section de la connexion -->
    <div class="login-box">
      <h2 class="login-title">S'identifier</h2>
      <form @submit.prevent="loginUser" class="login-form">
        <div class="input-group">
          <label for="email">Adresse e-mail</label>
          <input type="email" v-model="email" required />
        </div>
        <div class="input-group">
          <label for="motDePasse">Mot de passe</label>
          <input type="password" v-model="motDePasse" required />
        </div>
        <button type="submit" class="login-button">Continuer</button>
      </form>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <p class="login-info">
        En continuant, vous acceptez les <a href="#">conditions d'utilisation</a> et la <a href="#">politique de confidentialité</a> de Pâtisserie de Nassim.
      </p>
      <div class="help-section">
        <a href="#">Besoin d'aide ?</a>
      </div>
    </div>

    <!-- Section pour la création de compte -->
    <div class="register-section">
      <p class="new-customer">Vous êtes nouveau ?</p>
      <button @click="goToRegister" class="register-button">Créer votre compte</button>
      <br>
      <br>
      <button @click="goToAdmin" class="register-button">Se connecter en tant qu'admin</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'UserLogin',
  data() {
    return {
      email: '',
      motDePasse: '',
      errorMessage: '',
    };
  },

  methods: {
    async loginUser() {
      try {
        const response = await axios.post('http://localhost:3000/login', {
          email: this.email,
          motDePasse: this.motDePasse,
        });

        if (response && response.data) {
          // Sauvegarder les données de l'utilisateur connecté dans le localStorage
          localStorage.setItem('userData', JSON.stringify(response.data));
          

          // Redirection vers la page de l'utilisateur connecté
          this.$router.push('/new-accueil'); 
        } else {
          this.errorMessage = 'La réponse de l\'API est vide ou incorrecte.';
        }
      } catch (error) {
        console.error('Erreur lors de la connexion:', error);
        this.errorMessage = 'Erreur lors de la connexion : ' + (error.response?.data?.message || error.message);
      }
    },
    goToAdmin () {
      this.$router.push('/admin-login');
    },
    // Méthode pour rediriger vers la page d'inscription
    goToRegister() {
      this.$router.push('/register');
    },
  },
};
</script>

<style scoped>
/* Conteneur principal pour la page de connexion */
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
}

/* Conteneur de la boîte de connexion */
.login-box {
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 20px;
  width: 350px;
  background-color: white;
}

/* Titre de la page de connexion */
.login-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

/* Groupes de saisie pour les champs */
.input-group {
  margin-bottom: 15px;
}

/* Style pour les labels */
.input-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

/* Style pour les champs de saisie */
.input-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-sizing: border-box;
}

/* Bouton de connexion */
.login-button {
  width: 100%;
  background-color: #c966e1;
  border: 1px solid #a88734;
  border-radius: 5px;
  padding: 10px;
  font-weight: bold;
  cursor: pointer;
}

/* Message d'erreur */
.error-message {
  color: red;
  margin: 15px 0;
}

/* Informations supplémentaires */
.login-info {
  font-size: 12px;
  margin-top: 20px;
  line-height: 1.5;
}

.login-info a {
  color: #0066c0;
  text-decoration: none;
}

.login-info a:hover {
  text-decoration: underline;
}

/* Section de demande d'aide */
.help-section {
  margin-top: 10px;
}

/* Style du lien d'aide */
.help-section a {
  font-size: 14px;
  color: #0066c0;
  text-decoration: none;
}

.help-section a:hover {
  text-decoration: underline;
}

/* Section de création de compte */
.register-section {
  text-align: center;
  margin-top: 20px;
}

/* Texte pour le nouveau client */
.new-customer {
  font-size: 14px;
  margin-bottom: 10px;
}

/* Bouton pour créer un compte */
.register-button {
  width: 100%;
  background-color: #e7e9ec;
  border: 1px solid #a88734;
  border-radius: 5px;
  padding: 10px;
  font-weight: bold;
  cursor: pointer;
}

/* Style pour le bouton au survol */
.register-button:hover {
  background-color: #d5d7da;
}
</style>
