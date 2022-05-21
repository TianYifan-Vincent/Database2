import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import * as ElIconModules from '@element-plus/icons'

import axios from 'axios'


import '@/assets/css/global.css'


const app = createApp(App)
axios.defaults.baseURL='http://localhost:9090'
app.config.globalProperties.$http=axios


// 统一注册Icon图标
for (const iconName in ElIconModules) {
    if (Reflect.has(ElIconModules, iconName)) {
        const item = ElIconModules[iconName]
        app.component(iconName, item)
    }
}

createApp(App).use(store).use(router).use(ElementPlus, {locale:zhCn, size:"mini"}).mount('#app')
