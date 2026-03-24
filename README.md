# 智能视频分享平台

## 项目简介
集视频上传、内容审核、流畅播放及社区互动于一体的视频平台，支持用户端和审核管理端双角色，解决UGC平台内容审核效率问题。

## 技术栈

### 后端
- SpringBoot
- Spring Security + JWT
- MyBatis / JPA
- MySQL
- Redis
- WebSocket

### 前端
- Vue 3
- Element Plus
- Video.js
- Axios

## 目录结构
backend/ # 后端代码（SpringBoot）
frontend/ # 前端代码（Vue3）
docs/ # 文档 - ql/ # 数据库脚本
README.md

text

## 快速启动

### 环境要求
- JDK 1.8+
- MySQL 5.7+
- Redis
- Node.js 16+

### 1. 数据库
sql
-- 创建数据库
CREATE DATABASE study_share;

-- 导入脚本
source docs/sql/study_share.sql
2. 后端启动
bash
cd backend
# 修改 application.yml 中的数据库连接信息
mvn spring-boot:run
后端默认端口：8080

3. 前端启动
bash
cd frontend
npm install
npm run dev
前端默认端口：8080

核心功能
模块	功能
用户认证	账号密码登录、邮箱登录
视频上传	拖拽上传、进度显示、分片上传
视频播放	流畅播放、弹幕互动
内容审核	管理员审核工作台
AI助手	智能问答、视频推荐

个人贡献
负责视频上传模块开发：实现拖拽上传、实时进度显示、大文件分片上传机制

参与用户认证模块：集成 Spring Security + JWT 实现登录认证

参与数据库设计：设计用户表、视频表、审核记录表、评论表等

使用 Redis 缓存热点视频数据，减少数据库查询压力

联系方式
邮箱：2078083730@qq.com

GitHub：https://github.com/2078083730
