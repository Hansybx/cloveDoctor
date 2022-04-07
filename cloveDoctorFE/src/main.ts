import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import 'element-plus/dist/index.css'
import { createPinia } from 'pinia'
import piniaPersist from 'pinia-plugin-persist'

const app = createApp(App)
const pinia = createPinia();
pinia.use(piniaPersist)

app.use(router)
app.use(pinia)

app.mount('#app')
