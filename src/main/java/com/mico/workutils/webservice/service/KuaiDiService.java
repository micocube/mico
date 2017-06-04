package com.mico.workutils.webservice.service;

import com.mico.workutils.util.HttpClientUtil;
import com.mico.workutils.util.JsonUtils;
import com.mico.workutils.webservice.annotation.WebService;
import com.mico.workutils.webservice.util.ParserConfig;
import org.junit.Test;

import java.util.Map;

/**
 * @author laids on 2017-01-09 for velocity.
 * ps:快递公司编码:申通="shentong" EMS="ems"
 * 顺丰="shunfeng" 圆通="yuantong"
 * 中通="zhongtong" 韵达="yunda"
 * 天天="tiantian" 汇通="huitongkuaidi"
 * 全峰="quanfengkuaidi" 德邦="debangwuliu"
 * 宅急送="zhaijisong"
 */
@WebService("http://www.kuaidi100.com/query")
public class KuaiDiService {

    public static Map getMessage(String companyId,String orderId){
        String webServiceConfig = ParserConfig.getWebServiceConfig(KuaiDiService.class);
        HttpClientUtil instance = HttpClientUtil.getInstance();
        String get = instance.sendHttpGet(webServiceConfig + "?type="+companyId+"&postid="+orderId );
                Map < String, Object > map = JsonUtils.parseMap(get);
        return map;
    }

    @Test
    public void test() throws Exception {
        Map ipMessage = KuaiDiService.getMessage("shengtong","1111111111111");
        System.out.println("args = [" + ipMessage + "]");
    }

}
