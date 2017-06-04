package com.mico.web; /**
 * @author laids on 2017-02-10 for spring.
 */

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过在UserController中加上@EnableAutoConfiguration开启自动配置，然后通过SpringApplication.run(UserController.class);运行这个控制器；这种方式只运行一个控制器比较方便.
 */
@RestController
@EnableAutoConfiguration
public class SingleApplication {

    @RequestMapping("/in")
    String home() {
        return "forward:/index.html";
    }


    @RequestMapping("/test")
    @ResponseBody
    String test() {
        Gson gson = new Gson();
        String s = gson.toJson("Hello World!");
        return s;
    }


    @Test
    public void test2()  {
        String[] args = new String[]{};
        SpringApplication.run(SingleApplication.class, args);
    }

}