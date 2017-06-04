package com.mico.web.controller;

import com.google.gson.Gson;
import com.mico.web.domain.Docker;
import com.mico.web.repository.DockerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/** 
 * <p>User: mico
 * <p>Date: 17/05/20
 * <p>Version: 1.0 
 */
@Controller
public class IndexController {


    @RequestMapping("/")
    public String index(HttpServletRequest request, String content) throws Exception{
        return "web/docer";
    }

    @RequestMapping("/code")
    public String code() throws Exception{
        return "web/code";
    }

    @ResponseBody
    @RequestMapping("/test2")
    public String test(HttpServletRequest request,@RequestBody String content,String nonce,String timestamp,String signature) throws Exception{
        System.out.println("request = [" + request + "], content = [" + content + "], nonce = [" + nonce + "], timestamp = [" + timestamp + "], signature = [" + signature + "]");
        System.out.println("request = [" + request + "], content = [" + request.getContentType() + "], nonce = [" + nonce + "], timestamp = [" + timestamp + "], signature = [" + signature + "]");
        return content;
    }



}