# pingsSys
## 简介
- pingsSys是一款基于spring-boot和dubbo的java微服务框架脚手架。采用前后端分离设计，连接无状态，前端页面采用ant design pro脚手架进行开发。本人会利用业务时间，持续更新。
- [源码](https://github.com/pingszi/pingsSys)
- [主页](http://pingssys.pings.fun)，用户名：pings，密码：123456
## 界面
- ![登录](https://github.com/pingszi/pingsSys/blob/master/pings-ui-admin/docker/login.png)
- ![主页](https://github.com/pingszi/pingsSys/blob/master/pings-ui-admin/docker/index.png)
## 项目说明
### pings-service-api
- dubbo接口和实体类，独立出来，方便Provider和Consumer共用
### pings-service-sys
- 权限管理服务(Provider)
### pings-web-admin
- 后台管理平台(Consumer)，所有系统的后台管理功能在此项目中
### pings-ui-admin
- 基于ant design pro脚手架的后台管理系统ui，所有系统的后台管理页面在此项目中
## 架构
- 基础框架：spring-boot-2.1.1 + dubbo-2.6.5
- 数据库：mysql + redis
- 数据库连接/持久层：druid + mybatis + mybatis-plus
- 权限认证：基于jwt和shiro的无状态认证
- 前端页面：基于ant design pro脚手架
## 功能
- 超级管理员权限
    - 角色"admin"拥有所有权限，不用进行权限验证；
    - 通过给用户分配"admin"角色，获取所有权限；
- 权限验证
    - 使用jwt token进行无状态权限认证
    - 配置token过期时间
    ```
    # sys config
    sys:
      # token过期时长(分钟)，<= 0时，使用默认配置180分钟
      jwt:
        expireTime: 500
    ```
- dubbo provider提供了分页，保存，根据编号删除的默认实现
```
//**接口继承BaseService
public interface DeptService extends BaseService<Dept> {}

//**实现类继承AbstractBaseService，不直接继承MyBaits-plus的ServiceImpl，是防止默认提供的服务太多
public class DeptServiceImpl extends AbstractBaseService<DeptMapper, Dept> implements DeptService {}
```
- Ant table分页
```
//**使用ReactPage接收分页参数
public ApiResponse list(ReactPage<User> page, User entity){
    ReactPage<User> list = (ReactPage<User>)this.iUserService.findPage(page, entity);
    //**ReactPage的toReactPageFormat方法转换为Ant table分页数据
    return new ApiResponse(200, list.toReactPageFormat());
}
```
- Ant tree树形结构
```
//**实体类继承AbstractReactTreeEntity
public class Dept extends AbstractReactTreeEntity{}

//**查询到所有数据后，使用AbstractTreeEntity.toTreeList静态方法转换为树形结构
List<Dept> list = this.baseMapper.selectList(new QueryWrapper<>());
AbstractTreeEntity.toTreeList(list)
```
- Ant TreeSelect树形结构
```
//**实体类继承AbstractReactTreeEntity
public class Dept extends AbstractReactTreeEntity
    //**获取树实际的值
    @Override
    public String getValue() {
        return this.getId() + "";
    }
    //**获取树显示的值
    @Override
    public String getTitle() {
        return this.getName();
}
```
## 更新记录
- 2019-02-10 搭建
- 2019-02-15 用户管理
- 2019-02-22 部门管理
- 2019-02-22 完善前端pings-ui-admin的权限
- 2019-03-11 角色管理、权限管理
