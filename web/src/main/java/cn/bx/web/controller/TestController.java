package cn.bx.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description test
 * @Author ZK
 * @Date 2023/3/16 15:59
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/wow")
    public String wow(){
        log.error("wow");
        return "hello world!";
    }
}
