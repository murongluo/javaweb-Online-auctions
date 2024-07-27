import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/index'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/dashboard',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/table',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/BaseTable.vue'),
                    meta: { title: '基础表格' }
                },
                {
                    // 富文本编辑器组件
                    path: '/editor',
                    component: () => import(/* webpackChunkName: "editor" */ '../components/page/VueEditor.vue'),
                    meta: { title: '富文本编辑器' }
                },
                {
                    path: '/userm',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/UserManagement.vue'),
                    meta: { title: '用户管理' }
                },
                {
                    path: '/itemm',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/ItemManagement.vue'),
                    meta: { title: '拍品管理' }
                },
                {
                    path: '/bidm',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/Biding.vue'),
                    meta: { title: '竞价管理' }
                },
                {
                    path: '/auctionm',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/Auction.vue'),
                    meta: { title: '拍卖会管理' }
                },
                {
                    path: '/auitm',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/AuIt.vue'),
                    meta: { title: '拍卖管理' }
                },
                {
                    path: '/chartsm',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/charts.vue'),
                    meta: { title: '数据分析' }
                },
                {
                    path: '/paym',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/Pay.vue'),
                    meta: { title: '支付订单' }
                },
                {
                    path: '/bid',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/bid.vue'),
                    meta: { title: '支付订单' }
                }

            ]
        },
        {
            path: '/admin/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/AdminLogin.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        },
        {
            path: '/404',
            component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
            meta: { title: '404' }
        },
        {
            path: '/syslogin',
            component: () => import(/* webpackChunkName: "login" */ '../components/pmsys/SysLogin.vue'),
            meta:{title:'校园拍卖平台登录'}
        },
        {
            path:'/sysregister',
            component: () => import(/* webpackChunkName: "login" */ '../components/pmsys/SysRegister.vue'),
            meta:{title:'校园拍卖平台注册'}
        },
        {
            path:'/forgot',
            component: () => import(/* webpackChunkName: "login" */ '../components/pmsys/Forgot.vue'),
            meta:{title:'重重密码'}
            
        },
        {
            path:'/test',
            component: () => import(/* webpackChunkName: "login" */ '../components/pmsys/Test.vue'),
            meta:{title:'测试页面'}
            
        },
        {
            path:'/about',
            component: () => import(/* webpackChunkName: "login" */ '../components/pmsys/AboutUs.vue'),
            meta:{title:'简介'}
            
        },
        {
            path:'/uplode',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/uplode.vue'),
            meta:{title:'上传'}
            
        },
        {
            path:'/my',
            component: () => import(/* webpackChunkName: "login" */ '../components/pmsys/My.vue'),
            meta:{title:'我的'}
            
        },
        {
            path:'/items',
            component: () => import(/* webpackChunkName: "login" */ '../components/pmsys/Items.vue'),
            meta:{title:'商品展示'}
            
        },
        {
            path:'/auction',
            component: () => import(/* webpackChunkName: "login" */ '../components/pmsys/Auction.vue'),
            meta:{title:'商品展示'}
            
        },
        {
            path:'/home',
            component: () => import(/* webpackChunkName: "login" */ '../components/pmsys/home.vue'),
            meta:{title:'主页'}
            
        },
        {
            path:'/index',
            component: () => import(/* webpackChunkName: "login" */ '../components/pmsys/index.vue'),
            meta:{title:'index'}
            
        },
        {
            path:'/auit',
            component: () => import(/* webpackChunkName: "login" */ '../components/pmsys/AuIt.vue'),
            meta:{title:'auit'}
            
        },

       
   
    ]
});

