import Vue from 'vue'
import App from './App.vue'
import router from './route'
import axios from "axios";
import filter from './filter/filter'

Vue.config.productionTip = false
//Vue.prototype.xxx可以理解为Vue组件的全局变量。可以在任意Vue组件中，使用this.xxx来获取这个值。$是Vue全局属性的一个约定
Vue.prototype.$ajax = axios

// 全局过滤器
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')