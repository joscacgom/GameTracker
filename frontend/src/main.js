import { createApp } from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faUser, faList, faCompass, faChevronLeft, faChevronRight, faCamera, faHome, faPlusCircle, faClose, faSadCry } from '@fortawesome/free-solid-svg-icons';

import App from './App.vue';
import router from './router/router';
import './assets/css/global.css';

library.add(faUser, faList, faCompass, faChevronLeft, faChevronRight, faCamera, faHome, faPlusCircle, faClose, faSadCry);

createApp(App)
  .use(router)
  .component('font-awesome-icon', FontAwesomeIcon)
  .mount('#app');
