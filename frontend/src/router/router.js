import { createRouter, createWebHistory } from 'vue-router';
import LoginForm from '../components/Auth/LoginForm.vue';
import RegisterForm from '../components/Auth/RegisterForm.vue';
import WelcomeComponent from '../components/Home/WelcomeComponent.vue';
import MainComponent from '../components/Home/MainComponent.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'WelcomeComponent',
      component: WelcomeComponent
      
    },
    {
      path: '/home',
      name: 'MainComponent',
      component: MainComponent
    },
    {
      path: '/login',
      name: 'LoginForm',
      component: LoginForm
    },
    {
      path: '/register',
      name: 'RegisterForm',
      component: RegisterForm
    },
  ]
});

export default router;
