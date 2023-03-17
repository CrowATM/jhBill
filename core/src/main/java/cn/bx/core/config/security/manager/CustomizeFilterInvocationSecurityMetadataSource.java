package cn.bx.core.config.security.manager;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;


/**
 * 获取可以访问当前接口的所有角色
 */
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//    @Autowired
//    private SysPermissionService sysPermissionService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

//        //获取请求地址
//        String requestUrl = ((FilterInvocation) o).getRequestUrl();
//        //转换
//        String perms = requestUrl.replaceAll("/", ":");
//        //获取访问路径所需的权限对应的所有角色
//        List<String> roleKeys = sysPermissionService.selectRolesByStrInMenuAndRole(perms);
//        if (!CollectionUtils.isEmpty(roleKeys)){
//            String[] attributes = new String[roleKeys.size()];
//            roleKeys.toArray(attributes);
//            return SecurityConfig.createList(attributes);
//        }
        //返回空不走决策管理器,即无权限控制
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
