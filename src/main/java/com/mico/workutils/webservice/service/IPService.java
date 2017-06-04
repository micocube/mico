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
@WebService("http://ip.taobao.com/service/getIpInfo.php")
public class IPService {

    public static Map getIpMessage(String ip){
        String webServiceConfig = ParserConfig.getWebServiceConfig(IPService.class);
        HttpClientUtil instance = HttpClientUtil.getInstance();
        String get = instance.sendHttpGet(webServiceConfig + "?ip=" + ip);
        Map<String, Object> map = JsonUtils.parseMap(get);
        return map;
    }

    @Test
    public void test() throws Exception {
        Map ipMessage = IPService.getIpMessage("59.52.24.124");
        System.out.println("args = [" + ipMessage + "]");
    }

}
