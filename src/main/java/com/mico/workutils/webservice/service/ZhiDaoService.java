package com.mico.workutils.webservice.service;

import com.mico.workutils.util.HttpClientUtil;
import com.mico.workutils.util.JsonUtils;
import com.mico.workutils.webservice.annotation.WebService;
import com.mico.workutils.webservice.util.ParserConfig;
import org.junit.Test;

import java.util.Map;

/**
 * @author laids on 2017-01-09 for velocity.
 */
@WebService("http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_length=600&bk_key=")
public class ZhiDaoService {

    public static Map getMessage(String key){
        String webServiceConfig = ParserConfig.getWebServiceConfig(ZhiDaoService.class);
        HttpClientUtil instance = HttpClientUtil.getInstance();
        String get = instance.sendHttpGet(webServiceConfig + key);
        Map<String, Object> map = JsonUtils.parseMap(get);
        return map;
    }

    @Test
    public void test() throws Exception {
        Map ipMessage = ZhiDaoService.getMessage("java");
        System.out.println("args = [" + ipMessage + "]");
    }

}
