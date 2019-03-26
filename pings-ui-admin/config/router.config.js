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
        routes: [
          {
            path: '/sys/updatePassword',
            name: 'updatePassword',
            hideInMenu: true,
            component: './Sys/UpdatePassword/UpdatePassword',
          },
          {
            path: '/sys/user',
            name: 'user',
            icon: 'user',
            authority: ['sys:user:list'],
            component: './Sys/User/User',
          },
          {
            path: '/sys/dept',
            name: 'dept',
            icon: 'team',
            authority: ['sys:dept:list'],
            component: './Sys/Dept/Dept',
          },
          {
            path: '/sys/role',
            name: 'role',
            icon: 'solution',
            authority: ['sys:role:list'],
            component: './Sys/Role/Role',
          },
          {
            path: '/sys/right',
            name: 'right',
            icon: 'copyright',
            authority: ['sys:right:list'],
            component: './Sys/Right/Right',
          },
        ],
      },

      //账单管理
      {
        path: '/bill',
        name: 'bill',
        icon: 'file-done',
        routes: [
          {
            path: '/bill/basData',
            name: 'basData',
            icon: 'appstore',
            authority: ['bill:basData:list'],
            component: './Bill/BasData/BasData',
          },
        ],
      },
    ],
  },
];
