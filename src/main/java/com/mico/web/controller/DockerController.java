package com.mico.web.controller;

import com.google.gson.Gson;
import com.mico.web.domain.Docker;
import com.mico.web.repository.DockerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * <p>User: laidingshan
 * <p>Date: 17-05-20
 * <p>Version: 1.0 
 */
@RestController
@RequestMapping("/docker")
public class DockerController {
    @Autowired
    DockerRepository repository;
    Gson gson = new Gson();
    Logger logger = LoggerFactory.getLogger(DockerController.class);
  

    @RequestMapping("/add")
    @ResponseBody
    public String add(HttpServletRequest request, String content) throws Exception{
        Docker docker = new Docker();
        docker.setContent(content);
        docker.setCreatetime(new Timestamp(System.currentTimeMillis()));
        repository.save(docker);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/vague")
    public String vagueSelect(String term){
        Map<String,String> stringList = new HashMap<>(10);
        List<Docker> dockers = repository.vagueSelect(term);
        dockers.stream().forEach(docker -> {
            String content = docker.getContent();
            if(null != content && (content.contains("\r\n") || content.contains("\r") || content.contains("\n"))){
                int index = content.indexOf("\r\n");
                if(index == -1){
                    index = content.indexOf("\r");
                }
                if(index == -1){
                    index = content.indexOf("\n");
                }
                String str = content.substring(0,index).replace("#","");
                stringList.put(docker.getId().toString(),str);
            }
        });
        String s = gson.toJson(stringList);
        logger.debug("autocomplete = [" + s + "]");
        return s;
    }

    @RequestMapping("/fetch")
    @ResponseBody
    public String add(Integer id) {
        Docker one = repository.findOne(id);
        return gson.toJson(one);
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(Integer id,String content) {
        Docker docker = new Docker();
        docker.setId(id);
        docker.setContent(content);
        docker.setCreatetime(new Timestamp(System.currentTimeMillis()));
        Docker one = repository.saveAndFlush(docker);
        return gson.toJson(one);
    }






}  