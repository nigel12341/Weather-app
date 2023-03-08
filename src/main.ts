import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from "./router/index"
// Import our custom CSS
import '../src/scss/styles.scss'

// Import all of Bootstrap's JS
import * as bootstrap from 'bootstrap'

createApp(App).use(router).mount('#app')
