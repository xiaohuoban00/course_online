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
import User from './view/admin/user'
import Resource from "@/view/admin/resource";
import Role from "@/view/admin/role";
import Member from "@/view/admin/member";
import Sms from "@/view/admin/sms"

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
        path: "",
        component: Login
    }, {
        path: "/",
        name: "admin",
        component: Admin,
        meta: {
            loginRequire: true
        },
        children: [{
            path: "welcome",
            name: "welcome",
            component: Welcome
        }, {
            path: "business/chapter",
            name: "business/chapter",
            component: Chapter
        }, {
            path: "business/section",
            name: "business/section",
            component: Section
        }, {
            path: "business/course",
            name: "business/course",
            component: Course
        }, {
            path: "business/category",
            name: "business/category",
            component: Category
        }, {
            path: "business/teacher",
            name: "business/teacher",
            component: Teacher
        }, {
            path: "file/file",
            name: "file/file",
            component: File
        }, {
            path: "system/user",
            name: "system/user",
            component: User
        }, {
            path: "system/resource",
            name: "system/resource",
            component: Resource
        }, {
            path: "system/role",
            name: "system/role",
            component: Role
        }, {
            path: "business/member",
            name: "business/member",
            component: Member
        },{
            path: "business/sms",
            name: "business/sms",
            component: Sms
        }]
    }]
})