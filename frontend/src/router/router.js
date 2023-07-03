import { createRouter, createWebHistory } from 'vue-router';
import LoginForm from '../components/Auth/LoginForm.vue';
import RegisterForm from '../components/Auth/RegisterForm.vue';
import WelcomeComponent from '../components/Home/WelcomeComponent.vue';
import MainComponent from '../components/Home/MainComponent.vue';
import ProfileComponent from '../components/Profile/ProfileComponent.vue';
import MyListsComponent from '../components/Lists/MyListsComponent.vue';
import ListDetailsComponent from '../components/Lists/ListDetailsComponent.vue';
import NotFoundComponent from '../components/NotFound/NotFoundComponent.vue';
import MyGamesComponent from '../components/Games/MyGamesComponent.vue';
import DiscoverComponent from '@/components/Home/DiscoverComponent.vue';
import GameDetailsComponent from '@/components/Games/GameDetailsComponent.vue';

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
      component: MainComponent,
      meta: { requiresAuth: true }
    },
    {
      path: '/profile',
      name: 'ProfileComponent',
      component: ProfileComponent,
      meta: { requiresAuth: true }
    },
    {
      path: '/list/:listId',
      name: 'ListDetailsComponent',
      component: ListDetailsComponent,
      props: true,
      meta: { requiresAuth: true }
    },
    {
      path: '/my-lists',
      name: 'MyListsComponent',
      component: MyListsComponent,
      meta: { requiresAuth: true }
    },
    {
      path: '/my-games',
      name: 'MyGamesComponent',
      component: MyGamesComponent,
      meta: { requiresAuth: true }
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
    {
      path: '/discover',
      name: 'Discover',
      component: DiscoverComponent
    },
    {
      path: '/details',
      name: 'GameDetails',
      component: GameDetailsComponent
    },
    {
      path:'/:pathMatch(.*)*',
      component: NotFoundComponent

   }
  ]
});

const isAuthenticated = () => {
  const username = sessionStorage.getItem('username');
  const token = sessionStorage.getItem('jwtoken');
  
  const authenticated = username !== null && token !== null;

  if (!authenticated) {
    router.push({ name: 'LoginForm' });
  }

  return authenticated;
};

router.beforeEach((to, from, next) => {
  // Check if the route requires authentication
  if (to.meta.requiresAuth) {
    // If the user is authenticated, allow navigation
    if (isAuthenticated()) {
      next();
    } else {
      // If the user is not authenticated, redirect to the login page or any other route
      next({ name: 'LoginForm' });
    }
  } else {
    // If the route doesn't require authentication, allow navigation
    next();
  }
});

export default router;
