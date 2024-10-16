<template>
    <div class="stripe-payment">
      <h2>Paiement sécurisé</h2>
      <form @submit.prevent="handlePayment">
        <label for="cardNumber">Numéro de Carte :</label>
        <input type="text" v-model="cardNumber" placeholder="1234 1234 1234 1234" id="cardNumber" required />
  
        <label for="expiry">Date d'expiration :</label>
        <input type="text" v-model="expiry" placeholder="MM/YY" id="expiry" required />
  
        <label for="cvc">CVC :</label>
        <input type="text" v-model="cvc" placeholder="123" id="cvc" required />
  
        <button type="submit" class="pay-button">Payer {{ totalPrice }} €</button>
      </form>
  
      <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    </div>
  </template>
  
  <script>
  export default {
    name: 'StripePayment',
    props: ['totalPrice'],  // Récupère le montant total à payer depuis UserCart
    data() {
      return {
        cardNumber: '',
        expiry: '',
        cvc: '',
        successMessage: '',
        errorMessage: '',
      };
    },
    methods: {
      handlePayment() {
        // Simple simulation d'un paiement sécurisé
        if (this.cardNumber && this.expiry && this.cvc) {
          this.successMessage = 'Paiement effectué avec succès !';
          this.errorMessage = '';
          // Envoie les informations de paiement au backend si nécessaire
          // axios.post('/api/process-payment', paymentData);
        } else {
          this.successMessage = '';
          this.errorMessage = 'Veuillez remplir toutes les informations de paiement.';
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .stripe-payment {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 20px;
    margin-top: 20px;
    max-width: 400px;
  }
  .pay-button {
    background-color: #4e67ab;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  .pay-button:hover {
    background-color: #35456b;
  }
  .success-message {
    color: green;
    margin-top: 10px;
  }
  .error-message {
    color: red;
    margin-top: 10px;
  }
  </style>
  