##security+swagger2通用项目
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
```