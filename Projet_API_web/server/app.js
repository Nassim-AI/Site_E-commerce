const express = require('express');  // Importation d'Express pour créer le serveur
const axios = require('axios');  // Importation d'Axios pour effectuer des requêtes HTTP vers l'API Spring Boot
const cors = require('cors');
const basicAuth = require('basic-auth');
const app = express();  // Initialisation d'une application Express
const PORT = 3000;  // Définition du port sur lequel le serveur Node.js va tourner

app.use(cors());  // Autorise toutes les origines pour simplifier et on implément l'annotation crossOrigin sur Java

// Middleware pour gérer les requêtes JSON
app.use(express.json()); 

//dans le code on va faire ca app.post('/login', ...) et app.post('/register', ...)
//donc on aura besoin de récupérer des données json pour les manipuler.
//les données seront récupérées avec req.body

// **Routes pour interagir avec l'API Spring Boot**

//Au début je me suis servi de mon serveur Express.js comme un proxy qui
// qui relaye les requêtes entre le front end et l'API SpringBoot
// Le front end envoie des requêtes HTTP à l'API via le serveur express
// **Route pour créer un produit en le redirigeant vers le serveur Spring Boot**


app.post('/api/products', async (req, res) => {
    try {
      // Envoie la requête POST au backend Spring Boot (port 8080)
      const response = await axios.post('http://localhost:8080/api/products', req.body);
      res.status(response.status).json(response.data);
    } catch (error) {
      console.error('Erreur lors de la création du produit :', error.message);
      res.status(error.response?.status || 500).json({ message: 'Erreur lors de la création du produit.' });
    }
  });


  
// Route pour récupérer tous les produits de l'API Spring Boot
app.get('/api/products', async (req, res) => {
  try {
    const response = await axios.get('http://localhost:8080/api/products');
    res.status(response.status).json(response.data);
  } catch (error) {
    console.error('Erreur lors de la récupération des produits :', error.message);
    res.status(error.response?.status || 500).json({ message: 'Erreur lors de la récupération des produits.' });
  }
});


// Récupérer tous les utilisateurs
app.get('/api/utilisateurs', async (req, res) => { // définit les routes get sur api/utilisateurs
  try {
    const response = await axios.get('http://localhost:8080/api/utilisateurs'); //Fait une requête GET vers l'API Spring Boot pour récupérer tous les utilisateurs.
    res.json(response.data); //Envoie la réponse de l'API Spring Boot au frontend sous forme de JSON.
  } catch (error) {
    res.status(500).json({ error: 'Erreur lors de la récupération des utilisateurs' });
  }
});

// Récupérer un utilisateur spécifique par ID
app.get('/api/utilisateurs/:id', async (req, res) => {
  try {
    const id = req.params.id;
    const response = await axios.get(`http://localhost:8080/api/utilisateurs/${id}`);
    res.json(response.data);
  } catch (error) {
    res.status(500).json({ error: 'Erreur lors de la récupération de l\'utilisateur' });
  }
});

// Inscription d'un nouvel utilisateur (envoie les données à Spring Boot)

app.post('/register', async (req, res) => { //route post pour gérer le formulaire d'inscription
  const newUser = req.body;  // Récupère les données du formulaire d'inscription

  try {
    // Appelle l'API Spring Boot pour créer un nouvel utilisateur
    const response = await axios.post('http://localhost:8080/api/utilisateurs/inscription', newUser); 
    //Envoie une requête POST à Spring Boot pour créer le nouvel utilisateur.
    res.json(response.data); //la réponse d'express lorsqu'une requete est recue et ici express renvoie une réponse au format json de la donnée du new user
    // Renvoie la réponse de Spring Boot au frontend
    //response correspond à la réponse de spring boot
  } catch (error) {
    res.status(500).json({ message: 'Erreur lors de l\'inscription de l\'utilisateur' });
  }
});

// Route pour récupérer un produit spécifique par ID
app.get('/api/products/:id', async (req, res) => {
    const productId = req.params.id; // Récupère l'ID du produit depuis l'URL
    try {
      // Envoie la requête GET au backend Spring Boot (port 8080) pour récupérer le produit par son ID
      const response = await axios.get(`http://localhost:8080/api/products/${productId}`);
      res.status(response.status).json(response.data);
    } catch (error) {
      console.error('Erreur lors de la récupération du produit :', error.message);
      res.status(error.response?.status || 500).json({ message: 'Erreur lors de la récupération du produit.' });
    }
  });
  

// Route pour supprimer un produit en le redirigeant vers le serveur Spring Boot
app.delete('/api/products/:id', async (req, res) => {
    const productId = req.params.id;
    try {
      // Envoie la requête DELETE au backend Spring Boot (port 8080) avec l'ID du produit
      const response = await axios.delete(`http://localhost:8080/api/products/${productId}`);
      res.status(response.status).json({ message: 'Produit supprimé avec succès.' });
    } catch (error) {
      console.error('Erreur lors de la suppression du produit :', error.message);
      res.status(error.response?.status || 500).json({ message: 'Erreur lors de la suppression du produit.' });
    }
  });
  
// Route pour mettre à jour un produit existant
app.put('/api/products/:id', async (req, res) => {
    const productId = req.params.id; // Récupère l'ID du produit depuis les paramètres de la requête
    const updatedProduct = req.body; // Récupère les nouvelles données du produit depuis le corps de la requête
  
    try {
      // Envoie la requête PUT au backend Spring Boot (port 8080) avec l'ID du produit et les nouvelles données
      const response = await axios.put(`http://localhost:8080/api/products/${productId}`, updatedProduct);
      res.status(response.status).json(response.data);
    } catch (error) {
      console.error('Erreur lors de la mise à jour du produit :', error.message);
      res.status(error.response?.status || 500).json({ message: 'Erreur lors de la mise à jour du produit.' });
    }
  });
  

app.post('/login', async (req, res) => {
    console.log('Requête reçue pour /login :', req.body);
    const { email, motDePasse } = req.body;
  
    try {
      const response = await axios.get('http://localhost:8080/api/utilisateurs/connexion', {
        params: { email, motDePasse },
      });
  
      if (response && response.data) {
        res.json(response.data);
      } else {
        res.status(404).json({ message: 'Utilisateur non trouvé.' });
      }
    } catch (error) {
      res.status(401).json({ message: 'Email ou mot de passe incorrect !' });
    }
  });

  // Ce code utilise Express pour recevoir une requête de création de commande
  // puis le code la relaye à l'API Spring Boot via une requête HTTP
  // et on crée un endpoint POST envoyé à l'url
  // Donc lorsqu'une requete POST est envoyée à L'URL ce bloc de code sera executé
  // async (req, res) permet d'utiliser la syntaxe async pour permettre
  // l'utilisation de await de la fonction asynchrone avec requetes et réponse

  app.post('/api/orders', async (req, res) => {
    console.log("Commande reçue par Express :", req.body); // Ajoute ce log pour vérifier les données
    try {
      // Requête POST vers l'API Spring Boot pour créer une nouvelle commande
      const response = await axios.post('http://localhost:8080/api/orders', req.body);
      // permet de stocker la réponse envoyée par l'API SpringBoot
      res.status(response.status).json(response.data);
      // renvoie les données de la réponse Springboot sous format JSON au front end
    } catch (error) {
      console.error('Erreur lors de la création de la commande :', error.message);
      res.status(error.response?.status || 500).json({ message: 'Erreur lors de la création de la commande.' });
    }
  });
  
  // Récupérer toutes les commandes
  app.get('/api/orders', async (req, res) => {
    try {
      const response = await axios.get('http://localhost:8080/api/orders');
      res.status(response.status).json(response.data);
    } catch (error) {
      console.error('Erreur lors de la récupération des commandes :', error.message);
      res.status(error.response?.status || 500).json({ message: 'Erreur lors de la récupération des commandes.' });
    }
  });
  
  //  Récupérer une commande par ID
  app.get('/api/orders/:id', async (req, res) => {
    const orderId = req.params.id;
    try {
      const response = await axios.get(`http://localhost:8080/api/orders/${orderId}`);
      res.status(response.status).json(response.data);
    } catch (error) {
      console.error('Erreur lors de la récupération de la commande :', error.message);
      res.status(error.response?.status || 500).json({ message: 'Erreur lors de la récupération de la commande.' });
    }
  });
  
  // Mettre à jour le statut d'une commande
  app.put('/api/orders/:id/status', async (req, res) => {
    const orderId = req.params.id;
    const newStatus = req.body.status; // Le nouveau statut de la commande est passé dans le corps de la requête
  
    try {
      const response = await axios.put(`http://localhost:8080/api/orders/${orderId}/status`, null, {
        params: { status: newStatus }
      });
      res.status(response.status).json(response.data);
    } catch (error) {
      console.error('Erreur lors de la mise à jour du statut de la commande :', error.message);
      res.status(error.response?.status || 500).json({ message: 'Erreur lors de la mise à jour du statut de la commande.' });
    }
  });
  
  // Supprimer une commande
  app.delete('/api/orders/:id', async (req, res) => {
    const orderId = req.params.id;
    try {
      const response = await axios.delete(`http://localhost:8080/api/orders/${orderId}`);
      res.status(response.status).json({ message: 'Commande supprimée avec succès.' });
    } catch (error) {
      console.error('Erreur lors de la suppression de la commande :', error.message);
      res.status(error.response?.status || 500).json({ message: 'Erreur lors de la suppression de la commande.' });
    }
  });
  
 
  app.put('/api/products/:id/stock', async (req, res) => {
    const productId = req.params.id;
    const { stock } = req.body;
  
    try {
      // Envoie la requête vers Spring Boot pour mettre à jour le stock
      const response = await axios.put(`http://localhost:8080/api/products/${productId}/stock`, { stock });
      res.status(response.status).json(response.data);
    } catch (error) {
      console.error('Erreur lors de la mise à jour du stock du produit :', error.message);
      res.status(error.response?.status || 500).json({ message: 'Erreur lors de la mise à jour du stock du produit.' });
    }
  });


// Middleware d'authentification basique pour accéder à la partie admin
const adminAuth = (req, res, next) => {
    const user = basicAuth(req);
  
    // Vérifie si l'utilisateur est bien 'admin' avec le mot de passe 'patisserie2024'
    if (!user || user.name !== 'admin' || user.pass !== 'patisserie2024') {
      res.set('WWW-Authenticate', 'Basic realm="example"');
      return res.status(401).send('Accès refusé : Informations d\'authentification incorrectes');
    }
    next(); // Passe à l'étape suivante si l'authentification est correcte
  };
  
  // Application du middleware sur les routes d'administration
  app.use('/admin', adminAuth);
  app.get('/admin', (req, res) => {
    res.send('Bienvenue sur le tableau de bord administrateur !');
  });
  
module.exports = adminAuth;


// Payement avec stripe, je ne l'ai pas complètement implanté 

const stripe = require('stripe')('CLE_SECRETE_STRIPE'); // <---- Mettre une clé secrète Stripe

app.post('/create-payment-intent', async (req, res) => {
  const { amount } = req.body;  // Le montant de la commande doit être envoyé depuis le frontend

  try {
    // Crée une Intent de paiement avec le montant fourni
    const paymentIntent = await stripe.paymentIntents.create({
      amount: amount * 100, // Stripe prend les montants en centimes
      currency: 'eur',  // Remplace 'eur' par une autre devise
      payment_method_types: ['card']  // Accepte toutes les cartes
    });

    // Envoie l'ID du PaymentIntent au frontend pour le traitement final
    res.json({ clientSecret: paymentIntent.client_secret });
  } catch (error) {
    console.error('Erreur lors de la création du Payment Intent :', error);
    res.status(500).json({ error: 'Échec lors de la création du paiement.' });
  }
});



// Démarrage du serveur Node.js
app.listen(PORT, () => {
  console.log(`Serveur Node.js démarré sur le port ${PORT}`);
});
