##security+swagger2通用项目
注意
--
```
使用外部tomcat容器时启动器必须重写configure
```
###swagger2配置
```
swagger.yml中添加配置覆盖默认配置即可
    app-doc:
      version: '@project.version@'
      description: 项目简介
    contact:
      name: xxx
      url:
      email: xxxxx
添加模块时,需要配置类添加Docket注册到springIOC
swagger访问路径: /swagger-ui.html
```

###多模块pom版本更新
```    
执行以下命令
    mvn versions:set -DnewVersion=0.5-SNAPSHOT
    mvn versions:update-child-modules
    生成pom.xml.versionsBackup的备份文件
执行提交命令
    mvn versions:commit
```
