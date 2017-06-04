package com.mico.web.controller;

import com.mico.web.domain.User;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/** 
 * <p>User: Zhang Kaitao 
 * <p>Date: 13-12-22 
 * <p>Version: 1.0 
 */
@RestController  
@RequestMapping("/user")  
public class UserController {  
  
    @RequestMapping("/{id}")
    @ResponseBody
    public User view(@PathVariable("id") Long id) {  
        User user = new User();  
        user.setId(id);  
        user.setName("zhang");  
        return user;  
    }
    @RequestMapping("/login")
    public String login(HttpServletRequest request, String userName, String password) {
        request.getSession().setAttribute("userName",userName);
        return "/p2p.jsp";
    }




}  