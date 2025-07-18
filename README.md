

```markdown
# StudentManagementSystemTest

这是一个学生管理系统的测试项目，使用 Spring Boot 框架构建后端服务，集成了 MyBatis Plus、Redisson、JWT、Maven 等技术栈。系统支持课程管理、学生管理、成绩管理、考试信息管理、选课功能等常见模块，并实现了异常统一处理、跨域配置、拦截器等功能。

## 技术栈

- **后端**: Java, Spring Boot, MyBatis Plus, Redisson, JWT
- **前端**: Vue3, Vite, Pinia, Axios
- **数据库**: MyBatis Plus (集成)
- **构建工具**: Maven
- **部署**: 可使用标准 Spring Boot 部署方式

## 主要功能模块

### 课程管理
- 查询所有课程
- 动态搜索课程
- 创建、更新、删除课程
- 学生选课功能
- 选课时库存扣减与事务管理
- 课程时间冲突检测（支持应用层和数据库层检测）

### 学生管理
- 学生信息增删改查
- 学生信息展示与搜索

### 成绩管理
- 添加、更新、删除、查询学生成绩
- 支持按学生 ID 和课程 ID 查询成绩

### 考试信息管理
- 教师考试信息的增删改查
- 学生考试信息查询

### 用户认证与权限
- 登录与注册功能
- JWT 拦截器验证用户身份
- 全局异常处理器（BusinessException、ValidationException、AuthException 等）

## 项目结构

```
src/main/java
├── controller        // 控制器层，处理 HTTP 请求
├── service           // 服务层，业务逻辑实现
├── mapper            // 数据访问层，MyBatis Plus Mapper 接口
├── entity            // 数据模型，与数据库表映射
├── util              // 工具类，如 JWT 工具、错误码、响应封装等
├── config            // 配置类，如跨域配置、MyBatis Plus 配置
└── exception         // 自定义异常类和全局异常处理器
```

前端部分基于 Vue3 框架，使用了 Vite 构建工具和 Pinia 状态管理器，实现了多个页面组件，如课程管理、成绩管理、考试管理、学生信息管理等。

## 快速开始

### 后端环境要求
- Java 17 或以上
- Maven 3.x
- Redis（用于库存扣减和选课逻辑）
- MySQL（或其他支持的数据库）

### 后端启动步骤
1. 克隆项目：
   ```bash
   git clone https://gitee.com/GUIHJIU/StudentManagementSystemTest
   ```
2. 进入项目目录并启动：
   ```bash
   cd StudentManagementSystemTest
   mvn spring-boot:run
   ```

### 前端环境要求
- Node.js 16.x 或以上
- Vite、Vue3、Pinia、Axios 等依赖

### 前端启动步骤
1. 进入前端目录：
   ```bash
   cd vue3/SMST
   ```
2. 安装依赖并启动：
   ```bash
   npm install
   npm run dev
   ```

## API 接口文档

使用 SpringDoc OpenAPI 可查看完整的接口文档，支持 Swagger UI。

## 异常处理

- **BaseException**: 所有异常的基类，包含错误码和上下文信息
- **AuthException**: 认证失败异常
- **BusinessException**: 业务逻辑异常
- **DaoException**: 数据访问层异常
- **ValidationException**: 参数校验异常，返回字段错误信息
- **GlobalExceptionHandler**: 全局异常处理器，统一返回 `ResponseResult`

## 配置与拦截器

- **JwtInterceptor**: 拦截请求并验证 JWT token
- **CrosConfig**: 全局跨域配置
- **WebConfig**: 拦截器注册配置
- **MybatisPlusConfig**: MyBatis Plus 配置，包括分页插件和配置自定义器

## 数据模型

- **Course**: 课程信息，包含 ID、名称、学分、容量、剩余人数、时间等字段
- **Student**: 学生信息，包含学号、姓名、性别、联系方式等
- **Score**: 学生成绩，关联学生 ID 和课程 ID
- **Teacher**: 教师考试信息，包含考试科目、时间、地点等
- **User**: 用户信息，包含用户名、密码、职位和盐值
- **SelectionRecord**: 选课记录，包含学生 ID、课程 ID 和时间冲突检测信息

## 工具类

- **JwtUtil**: JWT 生成、解析和验证工具类
- **TimeConflictDetector**: 时间冲突检测工具类，用于判断课程时间是否重叠
- **ResponseResult**: 统一响应封装类，包含状态码、消息、数据和时间戳
- **PageResult**: 分页查询结果封装类，包含总记录数和数据列表

## 许可证

该项目使用 MIT 许可证，请遵守开源协议。

## 联系方式

如有问题，请提交 Issues 或联系项目负责人。
```