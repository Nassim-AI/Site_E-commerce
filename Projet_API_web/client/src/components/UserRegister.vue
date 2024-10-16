<template>
  <div class="register-container">
    <!-- Section principale de l'inscription -->
    <div class="register-box">
      <h2 class="register-title">Créer un compte</h2>
      <form @submit.prevent="registerUser" class="register-form">
        <div class="input-group">
          <label for="nom">Nom :</label>
          <input type="text" v-model="nom" required />
        </div>
        <div class="input-group">
          <label for="prenom">Prénom :</label>
          <input type="text" v-model="prenom" required />
        </div>
        <div class="input-group">
          <label for="email">Adresse e-mail :</label>
          <input type="email" v-model="email" required />
        </div>
        <div class="input-group">
          <label for="adresse">Adresse :</label>
          <input type="text" v-model="adresse" required />
        </div>
        <div class="input-group">
          <label for="motDePasse">Mot de passe :</label>
          <input type="password" v-model="motDePasse" required />
        </div>

        <!-- Nouveau champ pour confirmation de mot de passe -->
        <div class="input-group">
          <label for="motDePasseConfirm">Confirmer le mot de passe :</label>
          <input type="password" v-model="motDePasseConfirm" required />
        </div>

        <button type="submit" class="register-button">Continuer</button>
      </form>

      <!-- Message d'erreur ou de succès -->
      <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    </div>

    <!-- Section d'informations supplémentaires -->
    <div class="info-section">
      <p class="info-text">
        En créant un compte, vous acceptez les 
        <a href="#">conditions d'utilisation</a> et la 
        <a href="#">politique de confidentialité</a> de Pâtisserie de Nassim.
      </p>
      <hr class="separator" />
      <div class="existing-account">
        <p>Vous possédez déjà un compte ?</p>
        <button @click="goToLogin" class="login-button">Identifiez-vous</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'UserRegister',
  data() {
    return {
      nom: '',
      prenom: '',
      email: '',
      adresse: '',
      motDePasse: '',
      motDePasseConfirm: '', // Ajout d'un champ de confirmation de mot de passe
      successMessage: '',
      errorMessage: '',
    };
  },
  methods: {
    async registerUser() {
      // Vérification des mots de passe
      if (this.motDePasse !== this.motDePasseConfirm) {
        this.errorMessage = 'Les mots de passe ne correspondent pas.';
        this.successMessage = '';
        return;
      }

      try {
        const response = await axios.post('http://localhost:3000/register', {
          nom: this.nom,
          prenom: this.prenom,
          email: this.email,
          adresse: this.adresse,
          motDePasse: this.motDePasse,
        });

        this.successMessage = 'Inscription réussie, Bienvenue ' + response.data.prenom;
        this.errorMessage = '';
        this.clearForm();
      } catch (error) {
        this.errorMessage = 'Erreur lors de l\'inscription. Veuillez réessayer.';
        this.successMessage = '';
      }
    },
    clearForm() {
      this.nom = '';
      this.prenom = '';
      this.email = '';
      this.adresse = '';
      this.motDePasse = '';
      this.motDePasseConfirm = '';
    },
    goToLogin() {
      this.$router.push('/login');
    },
  },
};
</script>

<style scoped>
/* Conteneur principal pour la page d'inscription */
.register-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
}

/* Boîte de formulaire pour l'inscription */
.register-box {
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 20px;
  width: 350px;
  background-color: white;
}

/* Titre de la page d'inscription */
.register-title {
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

/* Bouton d'inscription */
.register-button {
  width: 100%;
  background-color: #c966e1;
  border: 1px solid #1d1c1c;
  border-radius: 5px;
  padding: 10px;
  font-weight: bold;
  cursor: pointer;
}

/* Message de succès */
.success-message {
  color: green;
  margin: 15px 0;
}

/* Message d'erreur */
.error-message {
  color: red;
  margin: 15px 0;
}

/* Section d'informations supplémentaires */
.info-section {
  text-align: center;
  margin-top: 20px;
  width: 350px;
}

.info-text {
  font-size: 12px;
  line-height: 1.5;
}

.info-text a {
  color: #0066c0;
  text-decoration: none;
}

.info-text a:hover {
  text-decoration: underline;
}

/* Séparateur horizontal */
.separator {
  margin: 20px 0;
}

/* Section de connexion pour compte existant */
.existing-account {
  text-align: center;
}

.login-button {
  width: 100%;
  background-color: #c966e1;
  border: 1px solid #0f0f0f;
  border-radius: 5px;
  padding: 10px;
  font-weight: bold;
  cursor: pointer;
}

.login-button:hover {
  background-color: #d5d7da;
}
</style>
