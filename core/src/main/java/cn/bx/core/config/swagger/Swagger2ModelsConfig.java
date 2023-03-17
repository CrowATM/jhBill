package cn.bx.core.config.swagger;

import cn.bx.core.config.swagger.support.AppDoc;
import cn.bx.core.config.swagger.support.CustomizeContact;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;

/**
 * @Description swagger核心配置
 * @Author ZK
 * @Date 2023/3/16 14:44
 */
@EnableSwagger2
@Configuration
public class Swagger2ModelsConfig {

    private final Environment environment;
    private final AppDoc appDoc;
    private final CustomizeContact customizeContact;

    //spring推荐注入方式
    @Autowired
    public Swagger2ModelsConfig(Environment environment, AppDoc appDoc, CustomizeContact customizeContact) {
        this.environment = environment;
        this.appDoc = appDoc;
        this.customizeContact = customizeContact;
    }

    public Docket docket(String moduleCode, String moduleName, String... basePackage){
        // 设置要显示的Swagger环境
        Profiles profiles = Profiles.of("dev", "test");
        // 通过 environment.acceptsProfiles(profiles) 判断是否处在自己设定的环境当中
        boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)//制定api类型为swagger2类型
                .apiInfo(apiInfo(moduleName)) //定义api文档汇总信息
                .enable(flag)
                .groupName(moduleCode)
                .select().apis(basePackage(basePackage))//扫描哪个包下面
                .paths(PathSelectors.any())//所有接口
                .build()
                //全局token wait...
//                .securitySchemes()
//                .securityContexts()
                ;
    }

    private ApiInfo apiInfo(String moduleName) {
        return new ApiInfoBuilder()
                .title(moduleName)  //标题
                .contact(customizeContact.getContact())//联系方式
                .description(appDoc.getDescription())//描述
                .version(appDoc.getVersion())//版本
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }

    /**
     * 声明基础包
     * @param basePackage 基础包路径
     */
    @SuppressWarnings("all")
    private Predicate<RequestHandler> basePackage(final String[] basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)::apply).orElse(true);
    }

    /**
     * 校验基础包
     * @param basePackage 基础包路径
     */
    @SuppressWarnings("all")
    private Function<Class<?>, Boolean> handlerPackage(final String[] basePackage) {
        return input -> {
            for (String strPackage : basePackage) {
                //校验包的前缀存在
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * 检验基础包实例
     * @param requestHandler 请求处理类
     */
    @SuppressWarnings("deprecation")
    private Optional<Class<?>> declaringClass(RequestHandler requestHandler) {
        return Optional.ofNullable(requestHandler.declaringClass());
    }
}
