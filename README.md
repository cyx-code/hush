# Hush

### 个人博客

- 基于springboot、thymeleaf、mybatis、mybatis-plus搭建
- 使用maven管理项目
- 数据库采用mysql，druid数据源
- 接口文档使用swagger生成
- 使用七牛云搭建图床
- 使用shiro完成登录验证
- 博客主题采用本人很喜欢的[pinghsu](https://github.com/chakhsu/pinghsu)
- 博客后台管理基于[layuimini](https://github.com/zhongshaofa/layuimini)构建

### 如何使用

如果想本地打开。环境准备（jdk1.8，maven，mysql），如果遇到什么问题可以联系我，个人微信cyx1741914950

1. 先添加到你自己的本地

   ```bash
   git clone https://github.com/cyx-code/hush.git
   ```

2. 将项目路径下sql脚本执行后，将数据库连接改成你自己的。

3. 项目路径下依次执行以下命令

   ```bash
   mvn clean
   mvn install
   cd target
   java -jar hsuh.jar
   ```

4. 访问localhost:8080和localhost:8080/admin（管理员账号admin/111111）

### 致谢

- 感谢[chakhsu](https://github.com/chakhsu)的[pinghsu](https://github.com/chakhsu/pinghsu)主题
- 感谢[zhongshaofa](https://github.com/zhongshaofa)开源的[layuimini](https://github.com/zhongshaofa/layuimini)后台管理模版