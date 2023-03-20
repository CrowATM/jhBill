package cn.bx.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @Description 控制器日志追加默认信息
 * @Author ZK
 * @Date 2023/3/20 17:20
 */
@Aspect
@Component
public class LogAppendDefaultInfoAspect {

    @Pointcut("execution (* cn.bx.*.controller.*Controller.*(..)) " +
            "&& ((@annotation(org.springframework.web.bind.annotation.RequestMapping)) " +
            "|| (@annotation(org.springframework.web.bind.annotation.PostMapping)) " +
            "|| (@annotation(org.springframework.web.bind.annotation.GetMapping)))")
    public void controllerPointcut() {
    }

    @Before(value = "controllerPointcut()")
    public void getLogDefaultInfo(JoinPoint point) {
        String userName = "NaN";
        String ipAddress = "NaN";
        MDC.put("userName", userName);
        MDC.put("ipAddress", ipAddress);
    }
}
