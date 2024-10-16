1-Mettre le fichier sur son bureau.


2-Importer la base de données "Website_API.sql" sur phpMyAdmin, MySQL Workbench ou n'importe quel outil qui permet de gérer des bases de données.

3-Ouvrir le dossier ProjetAPI. 
Puis changer les informations suivantes dans application.properties en fonction des vôtres:

spring.datasource.url=
spring.datasource.username=
spring.datasource.password=


Puis Lancer le code depuis la classe ProjetApiApplication.java.

4-Ensuite importer le Projet_API_web sur Visual Studio Code (ou autre). Puis, ouvrir un terminal et effectuer la commande suivante:

npm run dev 

Cela permettra de lancer simultanément Node.js sur le port 3000 ainsi que le front-end sur le port 8081.

5-Pour accéder à la partie admin qui a une sécurité Basic Auth, voici les codes:
Identifiant: admin
Mot de passe: patisserie2024


Pour que l'API externe de géolocalisation fonctionne bien, il faudra rentrer une vraie adresse lors de la création.

Voici un exemple de format d'adresse qui fonctionnera: 17 Rue de Rivoli, 75004 Paris, France

Voici un compte client qui vous permettra de vous connecter directement:
"email": "nassim.issad@gmail.com"
"motDePasse": "12345"

6- Ma partie front-end est constituée ainsi: dans la partie: client/src/components, il y a toutes mes pages vue.
Dans mon main.js j'ai importé un router. De cette manière, toutes mes routes sont dans mon router.js

7- Concernant ma partie backend, mes endpoints se trouvent sur java. Il arrive que j'utilise la partie server/app.js comme proxy pour faire la transition entre mon back-end java et mon front-end, tout comme il arrive que j'appelle directement mes endroits Java grâce à l'annotation @CrossOrigin que je n'ai découvert que en plein milieu de la réalisation de ce site. Voilà la raison pour laquelle j'ai alterné.


8-Les dépendances nécessaires à installer pour lancer le projet:


npm install express
npm install axios
npm install cors
npm install basic-auth 
npm install @vue/cli-service
npm install concurrently (pour exécuter en même temps le serveur et le client)
npm install stripe
npm install vue-router@4


