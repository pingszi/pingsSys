
## 简介
- 基于ant design pro脚手架的后台管理系统
- ant design pro：http://pro.ant.design/index-cn
## 界面
![登录](https://github.com/pingszi/pingsSys/blob/master/pings-ui-admin/docker/login.png)
![主页](https://github.com/pingszi/pingsSys/blob/master/pings-ui-admin/docker/index.png)
## 权限
- 权限系统使用shiro进行控制，提供控制到按钮级别的权限；
- 用户权限保存到localStorage，供前端使用，后台也会进行相应的权限验证；
- 所有界面在router.config.js设置，暂没有从后台系统获取；
- 菜单权限在router.config.js的对应菜单配置
```
{
    path: '/sys/user',
    name: 'user',
    icon: 'user',
    //**查看权限
    authority: ['sys:user:list'],
    component: './Sys/User/User',
},
```
- 每个页面的修改/删除等权限在对应页面进行配置
```
//**引入权限组件
import Authorized from '@/utils/Authorized';

//**修改权限
<Authorized authority="sys:dept:save">
    <a onClick={() => this.handleModalVisible(true, record)}>修改</a>
</Authorized>
//**删除权限
<Authorized authority="sys:dept:delete">
    <a onClick={() => this.handleDelete(record.id)}>删除</a>
</Authorized>
```
