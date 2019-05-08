# pingsSys
## 简介
- pingsSys是一款基于spring-boot和dubbo的java微服务框架脚手架。采用前后端分离设计，连接无状态，前端页面采用ant design pro脚手架进行开发。本人会利用业务时间，持续更新。
- [源码](https://github.com/pingszi/pingsSys)
- [主页](http://pingssys.pings.fun)，用户名：pings，密码：123456
- [专栏](https://blog.csdn.net/zhouping118/column/info/34277)
## 界面
- ![登录](https://github.com/pingszi/pingsSys/blob/master/pings-ui-admin/docker/login.png)
- ![主页](https://github.com/pingszi/pingsSys/blob/master/pings-ui-admin/docker/index.png)
## 运行项目
### 后端
- 说明：
    - 先启动Provider，然后启动Consumer
    - 开发模式依赖的运行环境(mysql,redis,zookeeper)均可以直接连接，你可以先在本地运行起来，然后在自己搭建运行环境
    - [运行环境搭建过程](https://blog.csdn.net/zhouping118/article/details/88032298)
- 步骤：
    - 开发模式启动springboot项目pings-service-sys
    - 开发模式启动springboot项目pings-service-bill
    - 开发模式启动springboot项目pings-web-admin
    - 开发模式启动springboot项目pings-web-bill
    - 开发模式启动的方法，添加vm启动参数：-Dspring.profiles.active=dev
### 前端
- 说明：前端为项目pings-ui-admin，基于ant design pro脚手架
- 步骤：
    - 安装nodejs
    - 进入pings-ui-admin目录
    - 安装依赖：npm install(需要比较长的时间)
    - 启动：npm run start:no-mock
    - 访问：http://localhost:8000
    - 账号：pings/123456
## 项目说明
### pings-sys-commons
- 公共的工具类
### pings-service-api
- dubbo接口和实体类，独立出来，方便Provider和Consumer共用
### pings-service-sys
- 权限管理服务(Provider)
### pings-service-bill
- 账单管理服务(Provider)
### pings-web-admin
- 后台管理系统(Consumer)
### pings-web-bill
- 账单管理系统(Consumer)
### pings-ui-admin
- 基于ant design pro脚手架的后台管理系统ui(前端界面)
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
        - 用户登录成功生成token(生成访问令牌和刷新令牌，刷新令牌保存到redis)
        - 用户提交请求，验证访问令牌是否过期
            - 如果没有过期，已登录，流程完
            - 如果过期，判断刷新令牌是否过期
                - 如果过期，没有登录，流程完
                - 如果没有过期，重新生成访问token
    - 配置token过期时间
    ```
    # sys config
    sys:
      jwt:
        secret: ==SFddfenfV2FuZzkyNjQ1NGRTQkFQSUpXVA==
         # 访问令牌过期时长(分钟)，<= 0时，使用默认配置5分钟
        access-token:
          expire-time: 1
        # 刷新令牌过期时长(分钟)，<= 0时，使用默认配置60分钟
        refresh-token:
          expire-time: 5
    ```
- dubbo provider提供了查询全部，分页，保存，根据编号删除的默认实现
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
- 2019-03-13 完善用户管理、角色管理
- 2019-03-20 添加账单管理提供者
- 2019-03-22 更新token规则，增加安全性
- 2019-03-26 添加账单管理消费者；开发基础数据管理菜单；退出登录时，使token无效
- 2019-03-28 欠款单管理、还款单管理和消费明细管理
- 2019-04-04 开发UserServiceStub，调用redis缓存，避免每个项目定义访问redis缓存逻辑
- 2019-04-10 在UserServiceStub中使用Guava Cache堆缓存缓存用户信息
- 2019-04-11 dubbo从2.6.5升级为2.7.1
- 2019-04-20 把公共类提取到pings-sys-commons