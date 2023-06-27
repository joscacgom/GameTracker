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
      path: '/profile',
      name: 'ProfileComponent',
      component: ProfileComponent
    },
    {
      path: '/list/:listId',
      name: 'ListDetailsComponent',
      component: ListDetailsComponent,
      props: true
    },
    {
      path: '/my-lists',
      name: 'MyListsComponent',
      component: MyListsComponent
    },
    {
      path: '/my-games',
      name: 'MyGamesComponent',
      component: MyGamesComponent
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
      path:'/:pathMatch(.*)*',
      component: NotFoundComponent
   }
  ]
});

export default router;
