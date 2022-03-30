import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import 'element-plus/dist/index.css'
const app = createApp(App)

app.config.globalProperties.$isOnline = false;
app.use(router)
app.mount('#app')
