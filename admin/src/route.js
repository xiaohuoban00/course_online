import Vue from 'vue'
import Router from 'vue-router'
import Login from './view/login.vue'
import Admin from './view/admin.vue'
import Welcome from './view/admin/welcome.vue'
import Chapter from './view/admin/chapter'
import Section from './view/admin/section'

Vue.use(Router);

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [{
        path: "*",
        redirect: "/login"
    }, {
        path: "/login",
        component: Login
    }, {
        path: "/",
        name:"admin",
        component: Admin,
        children: [{
            path: "welcome",
            name: "welcome",
            component: Welcome
        },{
            path: "business/chapter",
            name: "business/chapter",
            component: Chapter
        },{
            path: "business/section",
            name: "business/section",
            component: Section
        }]
    }]
})