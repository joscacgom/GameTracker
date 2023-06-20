import { createApp } from 'vue'
import App from './App.vue'
import routes from './router/router'

createApp(App).use(routes).mount('#app')
