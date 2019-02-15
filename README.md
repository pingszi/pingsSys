# pingsSys
## 简介
- pingsSys是一款基于spring-boot和dubbo的java微服务框架脚手架。采用前后端分离设计，连接无状态，前端页面采用ant design pro脚手架进行开发。
- 主页：建设中
## 项目说明
### pings-service-api
- dubbo接口和实体类，独立出来，方便Provider和Consumer共用
### pings-service-sys
- 权限管理服务(Provider)
### pings-web-admin
- 后台管理平台(Consumer)，所有系统的后台管理功能在此项目中
### pings-ui-admin
- 基于ant design pro脚手架的后台管理系统ui
## 架构
- 基础框架：spring-boot-2.1.1 + dubbo-2.6.5
- 数据库：mysql + redis
- 数据库连接/持久层：druid + mybatis + mybatis-plus
- 权限认证：基于jwt和shiro的无状态认证
- 前端页面：基于ant design pro脚手架
## 更新记录
- 2019-02-10 搭建
- 2019-02-15 用户管理
