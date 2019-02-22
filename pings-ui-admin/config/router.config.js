export default [
  // user
  {
    path: '/user',
    component: '../layouts/UserLayout',
    routes: [
      { path: '/user', redirect: '/user/login' },
      { path: '/user/login', component: './User/Login' },
    ],
  },
  // sys
  {
    path: '/',
    component: '../layouts/BasicLayout',
    Routes: ['src/pages/Authorized'],
    routes: [
      { path: '/', redirect: '/sys/user' },
      // exception
      {
        name: 'exception',
        icon: 'warning',
        path: '/exception',
        hideInMenu: true,
        routes: [
          {
            path: '/exception/403',
            name: 'not-permission',
            component: './Exception/403',
          },
          {
            path: '/exception/404',
            name: 'not-find',
            component: './Exception/404',
          },
          {
            path: '/exception/500',
            name: 'server-error',
            component: './Exception/500',
          },
        ],
      },
      //后台管理
      {
        path: '/sys',
        name: 'sys',
        icon: 'setting',
        authority: ['admin', 'user'],
        routes: [
          {
            path: '/sys/user',
            name: 'user',
            icon: 'user',
            component: './Sys/User/User',
          },
          {
            path: '/sys/dept',
            name: 'dept',
            icon: 'team',
            component: './Sys/Dept/Dept',
          },
        ],
      },
    ],
  },
];
