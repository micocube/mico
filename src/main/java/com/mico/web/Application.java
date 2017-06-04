package com.mico.web;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/** 
 * <p>User: Mico</p>
 * <p>Date: 13-12-22 </p>
 * <p>Version: 1.0 </p>
 * <p>通过@Configuration+@ComponentScan开启注解扫描并自动注册相应的注解Bean</p>
 * <p>http://blog.csdn.net/javahighness/article/details/53055149</p>#=4;Mq=+(uPK
 */  
@Configuration  
@ComponentScan
/**
 * 如果配置了web3.0的@WebFileter需要这个注解
 */
@ServletComponentScan
@EnableAutoConfiguration  
public class Application {
    public static void main(String[] args) {

        Logger logger = Logger.getLogger(Application.class);
        logger.info("test log");

        SpringApplication.run(Application.class);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

            container.addErrorPages(error401Page, error404Page, error500Page);
        });
    }
}  