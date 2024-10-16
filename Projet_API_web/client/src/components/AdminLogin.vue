<template>
    <div class="admin-login">
      <h2>Connexion Administrateur</h2>
      <form @submit.prevent="loginAdmin">
        <div class="input-group">
          <label for="username">Nom d'utilisateur :</label>
          <input type="text" v-model="username" required />
        </div>
        <div class="input-group">
          <label for="password">Mot de passe :</label>
          <input type="password" v-model="password" required />
        </div>
        <button type="submit" class="login-button">Se connecter</button>
        <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'AdminLogin',
    data() {
      return {
        username: '',
        password: '',
        errorMessage: ''
      };
    },
    methods: {
      async loginAdmin() {
        try {
          // Envoie la requête au backend pour vérifier les informations d'identification de l'admin
          const response = await axios.get('http://localhost:3000/admin', {
            auth: {
              username: this.username,
              password: this.password
            }
          });
  
          if (response.status === 200) {
            // Si la connexion est réussie, stocker les informations de l'administrateur
            const adminData = {
              username: 'admin',
              role: 'admin'
            };
            localStorage.setItem('userData', JSON.stringify(adminData));
            this.$router.push('/admin'); // Rediriger vers le tableau de bord administrateur
          }
        } catch (error) {
          this.errorMessage = 'Nom d\'utilisateur ou mot de passe incorrect.';
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .admin-login {
    max-width: 400px;
    margin: 50px auto;
    padding: 30px;
    border: 1px solid #ddd;
    border-radius: 10px;
    background-color: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  
  h2 {
    text-align: center;
    margin-bottom: 20px;
  }
  
  .input-group {
    margin-bottom: 15px;
  }
  
  .input-group label {
    display: block;
    font-weight: bold;
    margin-bottom: 5px;
  }
  
  .input-group input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
  }
  
  .login-button {
    width: 100%;
    background-color: #5a4d8b;
    border: none;
    padding: 10px;
    font-weight: bold;
    color: white;
    cursor: pointer;
    border-radius: 5px;
    margin-top: 10px;
  }
  
  .login-button:hover {
    background-color: #4b3e72;
  }
  
  .error-message {
    color: red;
    text-align: center;
    margin-top: 10px;
  }
  </style>
  