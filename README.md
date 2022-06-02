## easy-boot
spring-boot项目脚手架

### 内置功能
- 全局异常处理 com.easy.boot.core.api
- 基于token和自定义权限注解的认证授权 com.easy.boot.core.auth
- api请求日志 com.easy.boot.core.log
- redis工具类封装 com.easy.boot.core.redis
- 序列化相关 com.easy.boot.core.serializer
- 封装swagger com.easy.boot.core.swagger
- 文件上传(磁盘存储) com.easy.boot.core.upload
- 封装阿里云短信接口 com.easy.boot.core.util.ali
- 封装easyexcel表格操作 com.easy.boot.core.util.ExcelUtil
- api版本实现 com.easy.boot.core.version
- logback日志 logback-spring.xml

### Maven 引用方式
```xml
<project>
    
  <!--引入easy-boot-parent父pom-->
  <parent>
        <groupId>io.github.ordinarykai</groupId>
        <artifactId>easy-boot-parent</artifactId>
        <version>1.6.0</version>
        <relativePath/>
  </parent>
    
  <!--引入easy-boot核心包，包含了easy-boot全部功能-->
  <dependencies>
    <dependency>
        <groupId>io.github.ordinarykai</groupId>
        <artifactId>easy-boot-core</artifactId>
    </dependency>
  </dependencies>
    
</project>
```

