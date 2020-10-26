import Vue from 'vue'
import Router from 'vue-router'
import Login from './view/login.vue'
import Admin from './view/admin.vue'
import Welcome from './view/admin/welcome.vue'
import Chapter from './view/admin/chapter'
import Section from './view/admin/section'
import Course from './view/admin/course'
import Category from "./view/admin/category"
import Teacher from './view/admin/teacher'
import File from './view/admin/file'

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
        },{
            path: "business/course",
            name: "business/course",
            component: Course
        },{
            path: "business/category",
            name: "business/category",
            component: Category
        },{
            path: "business/teacher",
            name: "business/teacher",
            component: Teacher
        },{
            path: "file/file",
            name: "file/file",
            component: File
        }]
    }]
})