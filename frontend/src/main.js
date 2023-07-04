import { createApp } from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faUser, faList, faCompass, faChevronLeft, faChevronRight, faCamera, faHome, faPlusCircle, faClose, faGamepad, faSignOut, faEdit, faTrashCan } from '@fortawesome/free-solid-svg-icons';

import App from './App.vue';
import router from './router/router';
import './assets/css/global.css';

library.add(faUser, faList, faCompass, faChevronLeft, faChevronRight, faCamera, faHome, faPlusCircle, faClose, faGamepad, faSignOut, faEdit, faTrashCan);

createApp(App)
  .use(router)
  .component('font-awesome-icon', FontAwesomeIcon)
  .mount('#app');
