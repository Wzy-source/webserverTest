import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";

Vue.config.productionTip = false;
Vue.http.options.credentials = true;
axios.defaults.withCredentials = true
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')



/*main.js文件不需要进行改动，否则出现白屏*/
