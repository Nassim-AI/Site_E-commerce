<template>
    <div class="modif-container">
      <h1 class="modif-title">Mes Informations Personnelles</h1>
      
      <!-- Affichage des informations personnelles sous forme de résumé -->
      <div class="info-section">
        <h2>Adresse :</h2>
        <div class="info-row">
          <p>{{ user.address || 'Non renseignée' }}</p>
          <button @click="toggleEdit('address')" class="edit-button">
            <img src="@/assets/settings-icon.png" alt="Modifier l'adresse" class="icon" />
          </button>
        </div>
        
        <!-- Formulaire pour modifier l'adresse -->
        <div v-if="editMode.address" class="edit-section">
          <label for="address">Nouvelle adresse :</label>
          <input type="text" v-model="user.address" id="address" placeholder="Entrez votre nouvelle adresse" />
          <button @click="updateAddress" class="save-button">Sauvegarder</button>
        </div>
      </div>
  
      <hr />
  
      <!-- Section pour le mot de passe -->
      <div class="info-section">
        <h2>Mot de passe :</h2>
        <div class="info-row">
          <p>********</p>
          <button @click="toggleEdit('password')" class="edit-button">
            <img src="@/assets/settings-icon.png" alt="Modifier le mot de passe" class="icon" />
          </button>
        </div>
        
        <!-- Formulaire pour modifier le mot de passe -->
        <div v-if="editMode.password" class="edit-section">
          <label for="currentPassword">Mot de passe actuel :</label>
          <input type="password" v-model="currentPassword" id="currentPassword" placeholder="Entrez votre mot de passe actuel" />
          
          <label for="newPassword">Nouveau mot de passe :</label>
          <input type="password" v-model="newPassword" id="newPassword" placeholder="Entrez votre nouveau mot de passe" />
          
          <button @click="updatePassword" class="save-button">Changer le mot de passe</button>
        </div>
      </div>
      
      <!-- Bouton retour à l'accueil -->
      <div class="bottom-buttons">
        <button @click="backToShop" class="back-button">Retour à l'accueil</button>
      </div>
  
      <!-- Messages de succès ou d'erreur -->
      <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    </div>
  </template>
  
  
  <script>
import axios from 'axios';

export default {
  name: 'ModifInfos',
  data() {
    return {
      user: {
        id: '', // ID de l'utilisateur connecté
        address: '', // Nouvelle adresse
      },
      currentPassword: '', // Mot de passe actuel
      newPassword: '', // Nouveau mot de passe
      editMode: {
        address: false, // Activer/désactiver le mode édition pour l'adresse
        password: false, // Activer/désactiver le mode édition pour le mot de passe
      },
      successMessage: '',
      errorMessage: '',
    };
  },
  async created() {
  try {
    // Récupérer les informations de l'utilisateur connecté depuis le localStorage
    const storedUser = JSON.parse(localStorage.getItem('userData'));
    if (storedUser) {
      this.user.id = storedUser.id; // Stocker l'ID de l'utilisateur

      // **Changement 1** : Appel à l'API pour récupérer les informations de l'utilisateur spécifique
      const response = await axios.get(`http://localhost:8080/api/utilisateurs/${this.user.id}`);
      const userDataFromAPI = response.data;

      // **Changement 2** : Vérifier que l'adresse existe dans la réponse de l'API
      if (userDataFromAPI && userDataFromAPI.adresse) {
        this.user.address = userDataFromAPI.adresse; // Mettre à jour l'adresse de l'utilisateur
      } else {
        this.user.address = 'Non renseignée';
      }

      // **Changement 3** : Mettre à jour le localStorage avec la nouvelle adresse
      localStorage.setItem('userData', JSON.stringify({ ...storedUser, address: this.user.address }));

      // **Changement 4** : Afficher l'adresse dans la console pour vérification
      console.log("Adresse de l'utilisateur mise à jour :", this.user.address);
    } else {
      console.error("Aucun utilisateur trouvé dans le localStorage.");
    }
  } catch (error) {
    console.error('Erreur lors de la récupération des informations utilisateur :', error);
  }
}
,
  methods: {
    // Activer ou désactiver le mode d'édition
    toggleEdit(section) {
      this.editMode[section] = !this.editMode[section];
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

    // Mettre à jour l'adresse de l'utilisateur
    async updateAddress() {
      try {
        const addressUrl = `http://localhost:8080/api/utilisateurs/${this.user.id}/address?address=${encodeURIComponent(this.user.address)}`;
        await axios.put(addressUrl, null, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        
        // Sauvegarder la nouvelle adresse dans le localStorage
        localStorage.setItem('userData', JSON.stringify({ ...JSON.parse(localStorage.getItem('userData')), address: this.user.address }));
        
        // Affichage d'un message de succès
        this.successMessage = 'Adresse mise à jour avec succès !';
        this.errorMessage = '';
        this.editMode.address = false; // Désactiver le mode édition après la mise à jour
      } catch (error) {
        console.error('Erreur lors de la mise à jour de l\'adresse :', error);
        this.errorMessage = 'Une erreur est survenue lors de la mise à jour de l\'adresse.';
        this.successMessage = '';
      }
    },

    // Mettre à jour le mot de passe de l'utilisateur
    async updatePassword() {
      try {
        const passwordUrl = `http://localhost:8080/api/utilisateurs/${this.user.id}/password?currentPassword=${this.currentPassword}&newPassword=${this.newPassword}`;
        await axios.put(passwordUrl, null, {
            headers: {
            'Content-Type': 'application/json'
          }
        });
        
        // Affichage d'un message de succès et réinitialisation des champs
        this.successMessage = 'Mot de passe mis à jour avec succès !';
        this.errorMessage = '';
        this.currentPassword = '';
        this.newPassword = '';
        this.editMode.password = false; // Désactiver le mode édition après la mise à jour
      } catch (error) {
        console.error('Erreur lors de la mise à jour du mot de passe :', error);
        this.errorMessage = 'Une erreur est survenue lors de la mise à jour du mot de passe.';
        this.successMessage = '';
      }
    }
  }
};
</script>

<style scoped>
.modif-container {
  max-width: 600px;
  margin: 50px auto;
  padding: 30px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
}

.modif-title {
  text-align: center;
  font-size: 28px;
  margin-bottom: 20px;
  color: #2c3e50;
}

.info-section {
  margin: 20px 0;
}

.info-section h2 {
  font-size: 20px;
  margin-bottom: 10px;
  color: #2980b9;
}

.info-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
}

.info-row p {
  font-size: 16px;
  margin: 0;
  color: #333;
}

.edit-button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
}

.icon {
  width: 24px;
  height: 24px;
  filter: grayscale(100%);
  transition: filter 0.3s ease-in-out;
}

.icon:hover {
  filter: none;
}

.edit-section {
  margin-top: 10px;
}

.edit-section label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}

.edit-section input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-bottom: 10px;
}

.save-button {
  background-color: #3498db;
  border: none;
  border-radius: 5px;
  padding: 10px;
  font-weight: bold;
  color: white;
  cursor: pointer;
  width: 100%;
  margin-bottom: 10px;
  transition: background-color 0.3s ease-in-out;
}

.save-button:hover {
  background-color: #2980b9;
}

hr {
  border: 1px solid #ddd;
  margin: 20px 0;
}

.bottom-buttons {
  text-align: center;
  margin-top: 20px;
}

.back-button {
  background-color: #e74c3c;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  color: white;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}

.back-button:hover {
  background-color: #c0392b;
}

.success-message {
  color: #2ecc71;
  margin-top: 20px;
  text-align: center;
  font-size: 16px;
}

.error-message {
  color: #e74c3c;
  margin-top: 20px;
  text-align: center;
  font-size: 16px;
}
</style>

  
  