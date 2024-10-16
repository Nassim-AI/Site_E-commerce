import { createRouter, createWebHistory } from 'vue-router';


import UserAccueil from './components/UserAccueil.vue';
import UserLogin from './components/UserLogin.vue';
import ProductList from './components/ProductList.vue';
import UserCart from './components/UserCart.vue';
import UserRegister from './components/UserRegister.vue';
import UserConnected from './components/UserConnected.vue';
import NewAccueil from './components/NewAccueil.vue';
import ProductDetail from './components/ProductDetail.vue';
import UserHistory from './components/UserHistory.vue';
import ModifInfos from './components/ModifInfos.vue';
import NearbyStores from './components/NearbyStores.vue';
import AdminOrders from './components/AdminOrders.vue'; 
import AdminProducts from './components/AdminProducts.vue'; 
import AdminDashboard from './components/AdminDashboard.vue'; 
import AdminLogin from './components/AdminLogin.vue';

const routes = [
  { path: '/', name: 'Home', component: UserAccueil },
  { path: '/login', name: 'Login', component: UserLogin },
  { path: '/products', name: 'Products', component: ProductList },
  { path: '/cart', name: 'UserCart', component: UserCart },
  { path: '/register', component: UserRegister },
  { path: '/user-connected', name: 'UserConnected', component: UserConnected },
  { path: '/new-accueil', name: 'NewAccueil', component: NewAccueil },
  { path: '/product/:id', name: 'ProductDetail', component: ProductDetail },
  { path: '/user-history', name: 'user-history', component: UserHistory },
  { path: '/modif-infos', name: 'ModifInfos', component: ModifInfos },
  { path: '/nearby-stores', name: 'NearbyStores', component: NearbyStores },
  { path: '/admin', name: 'AdminDashboard', component: AdminDashboard, meta: { requiresAdmin: true } },
  { path: '/admin/orders', name: 'AdminOrders', component: AdminOrders, meta: { requiresAdmin: true }},
  { path: '/admin/products', name: 'AdminProducts', component: AdminProducts, meta: { requiresAdmin: true }},
  { path: '/admin-login', name: 'AdminLogin', component: AdminLogin },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});



router.beforeEach((to, from, next) => {
    const userData = JSON.parse(localStorage.getItem('userData'));
  
    if (to.matched.some(record => record.meta.requiresAdmin)) {
        // meta.requiresAdmin permet de vérifier le rôle de l'utilisateur
        //middleware de navigation qui ajoute une couche de sécurité pour vérifier 
        // rôles avant d'accéder à certaines routes
        //Si l'utilisateur est un admin alors l'accès est authorisé
      if (userData && userData.role === 'admin') {
        next(); // L'utilisateur est un admin, il est autorisé à accéder
      } else {
        next({ name: 'AdminLogin' }); // Rediriger vers la page de connexion admin
      }
    } else {
      next(); // Pas besoin d'authentification particulière
    }
  });
  
  

export default router;

