import { createRouter, createWebHistory } from 'vue-router';
import LoginForm from '../components/Auth/LoginForm.vue';
import RegisterForm from '../components/Auth/RegisterForm.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
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
