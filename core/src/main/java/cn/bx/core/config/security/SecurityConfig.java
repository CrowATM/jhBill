package cn.bx.core.config.security;

import cn.bx.core.config.security.filter.CustomizeBasicAuthenticationFilter;
import cn.bx.core.config.security.filter.CustomizeUsernamePasswordAuthenticationFilter;
import cn.bx.core.config.security.filter.RequestInputStreamFilter;
import cn.bx.core.config.security.handler.*;
import cn.bx.core.config.security.manager.CustomizeAccessDecisionManager;
import cn.bx.core.config.security.manager.CustomizeFilterInvocationSecurityMetadataSource;
import cn.bx.core.config.security.model.SecurityModel;
import cn.bx.core.config.security.provider.CustomizeAuthenticationProvider;
import cn.bx.core.config.security.support.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;

/**
 * @Description security 配置
 * @Author ZK
 * @Date 2023/3/16 17:48
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //登录成功处理逻辑
    private final CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;
    //登录失败处理逻辑
    private final CustomizeAuthenticationFailureHandler authenticationFailureHandler;
    //权限拒绝处理逻辑
    private final CustomizeAccessDeniedHandler accessDeniedHandler;
    //匿名用户访问无权限资源时的异常
    private final CustomizeAuthenticationEntryPoint authenticationEntryPoint;
    //登出处理器
    private final CustomizeLogoutHandler logoutHandler;
    //登出成功处理逻辑
    private final CustomizeLogoutSuccessHandler logoutSuccessHandler;
    //访问决策管理器
    private final CustomizeAccessDecisionManager accessDecisionManager;
    //实现权限拦截
    private final CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;
    private final UserDetailsService userDetailsService;
    //配置项模型
    private final SecurityModel securityModel;

    @Autowired
    public SecurityConfig(CustomizeAuthenticationSuccessHandler authenticationSuccessHandler, CustomizeAuthenticationFailureHandler authenticationFailureHandler, CustomizeAccessDeniedHandler accessDeniedHandler, CustomizeAuthenticationEntryPoint authenticationEntryPoint, CustomizeLogoutSuccessHandler logoutSuccessHandler, CustomizeAccessDecisionManager accessDecisionManager, CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource,
                          UserDetailsServiceImpl userDetailsService, SecurityModel securityModel, CustomizeLogoutHandler logoutHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.accessDecisionManager = accessDecisionManager;
        this.securityMetadataSource = securityMetadataSource;
        this.userDetailsService = userDetailsService;
        this.securityModel = securityModel;
        this.logoutHandler = logoutHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false);
        auth.userDetailsService(userDetailsService);
    }

    /**
     * 密码加密方式
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 设置默认的加密方式（强hash方式加密）
        return new BCryptPasswordEncoder();
    }

    /*
     * 自定义用户名密码鉴定，支持json格式传入的用户名密码
     * {@link WebSecurityConfig#configure(HttpSecurity)} 配置方法里用到这个bean
     * @throws Exception
     */
    @Bean
    public CustomizeUsernamePasswordAuthenticationFilter customizeUsernamePasswordAuthenticationFilter() throws Exception {
        CustomizeUsernamePasswordAuthenticationFilter filter = new CustomizeUsernamePasswordAuthenticationFilter(authenticationManager());
        // 登录成功以及登录失败的处理器由下面configure()方法里移动到这里，否则不生效（不生效原因是因为这个自定义的过滤器会先于默认的过滤器执行）
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    // 配置跨域访问资源
    private UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.setExposedHeaders(Collections.singletonList(HttpHeaders.SET_COOKIE));
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return configSource;
    }

    /**
     * jwt version
     * 服务器重启时或手动修改时使原jwt失效
     */
    @Bean("jwtVersion")
    public String jwtVersion(){
        return new Date().getTime() + "";
    }

    @Override
    public void configure(WebSecurity web) {
        //不走 Spring Security 过滤器链
        web.ignoring().antMatchers(securityModel.getSecurityPermitAll());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(urlBasedCorsConfigurationSource()) // 配置跨域问题
                .and()
                .csrf().disable();//关闭默认登录页
        http
                .authenticationProvider(new CustomizeAuthenticationProvider(userDetailsService, passwordEncoder()))
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(accessDecisionManager);//决策管理器
                        o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
                        return o;
                    }
                })
                //登出
                .and()
                .logout().logoutUrl("/user/logout")
                .permitAll()//允许所有用户
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(logoutSuccessHandler)//登出成功处理逻辑
                .deleteCookies("JSESSIONID")//登出之后删除cookie
                //登入
                .and()
                .formLogin().loginPage("/login")
                .permitAll()//允许所有用户
                //异常处理(权限拒绝、登录失效等)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)//权限拒绝处理逻辑
                .authenticationEntryPoint(authenticationEntryPoint)//匿名用户访问无权限资源时的异常处理
                //会话管理
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//禁用session
        http
                .addFilterAt(customizeUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new CustomizeBasicAuthenticationFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new RequestInputStreamFilter(), CustomizeUsernamePasswordAuthenticationFilter.class);
    }
}
