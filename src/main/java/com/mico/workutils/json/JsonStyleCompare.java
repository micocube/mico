package com.mico.workutils.json;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by micocube on 2017/5/4.
 */
public class JsonStyleCompare {
    @Test
    public void test() throws Exception {
        Gson gson = new Gson();
        String str = "{\"respData\":{\"errorCode\":\"00\",\"errorReason\":\"成功\",\"policyCount\":3,\"serialNo\":\"allianz201705040209393458\",\"policyLevel\":[{\"policyNo\":\"000002577860\",\"groupFlag\":\"02\",\"insuredFlag\":\"03\",\"realSign\":\"有效保单\",\"startDate\":\"2014/09/17\",\"startTime\":\"00:00:00\",\"endDate\":\"2034/09/17\",\"endTime\":\"00:00:00\",\"policyType\":\"联众恒佑定期寿险(B款)\",\"poPrem\":2019.1,\"currencyCode\":\"RMB\",\"payDate\":\"2017/09/17\",\"appntName\":\"3576585 3576585\",\"insuredPeoples\":\"1\",\"insuredList\":{\"insuredLevel\":[{\"insuredName\":\"35765853576585\",\"riskList\":{\"riskLevel\":[{\"riskCode\":\"AKFY01\",\"riskType\":\"安联附加安康福佑长期重大疾病保险\",\"riskName\":\"安联附加安康福佑长期重大疾病保险\",\"amnt\":300000,\"coPrem\":1497},{\"riskCode\":\"HYOUB01\",\"riskType\":\"联众恒佑定期寿险(B款)\",\"riskName\":\"联众恒佑定期寿险(B款)\",\"amnt\":230000,\"coPrem\":522.1}]}}]}},{\"policyNo\":\"000002688974\",\"groupFlag\":\"02\",\"insuredFlag\":\"03\",\"realSign\":\"有效保单\",\"startDate\":\"2015/08/14\",\"startTime\":\"00:00:00\",\"endDate\":\"2043/08/14\",\"endTime\":\"00:00:00\",\"policyType\":\"安联超级随心两全保险（分红型）\",\"poPrem\":2333.88,\"currencyCode\":\"RMB\",\"payDate\":\"2017/08/14\",\"appntName\":\"3576585 3576585\",\"insuredPeoples\":\"2\",\"insuredList\":{\"insuredLevel\":[{\"insuredName\":\"35765853576585\",\"riskList\":{\"riskLevel\":[{\"riskCode\":\"AWOP03\",\"riskType\":\"安联附加投保人豁免保险费失能收入损失保险\",\"riskName\":\"安联附加投保人豁免保险费失能收入损失保险\",\"amnt\":2236.86,\"coPrem\":37.22}]}},{\"insuredName\":\"35765853576585\",\"riskList\":{\"riskLevel\":[{\"riskCode\":\"AWOP03\",\"riskType\":\"安联附加投保人豁免保险费失能收入损失保险\",\"riskName\":\"安联附加投保人豁免保险费失能收入损失保险\",\"amnt\":2236.86,\"coPrem\":37.22}]}}]}},{\"policyNo\":\"000002689038\",\"groupFlag\":\"02\",\"insuredFlag\":\"03\",\"realSign\":\"有效保单\",\"startDate\":\"2015/08/31\",\"startTime\":\"00:00:00\",\"endDate\":\"2069/08/31\",\"endTime\":\"00:00:00\",\"policyType\":\"安联安康福瑞两全保险（B款）\",\"poPrem\":5184,\"currencyCode\":\"RMB\",\"payDate\":\"2017/08/31\",\"appntName\":\"3576585 3576585\",\"insuredPeoples\":\"1\",\"insuredList\":{\"insuredLevel\":[{\"insuredName\":\"35765853576585\",\"riskList\":{\"riskLevel\":[{\"riskCode\":\"AKFRB02\",\"riskType\":\"安联安康福瑞两全保险（B款）\",\"riskName\":\"安联安康福瑞两全保险（B款）\",\"amnt\":200000,\"coPrem\":1970},{\"riskCode\":\"AKFRR02\",\"riskType\":\"安联附加安康福瑞长期重大疾病保险（B款）\",\"riskName\":\"安联附加安康福瑞长期重大疾病保险（B款）\",\"amnt\":200000,\"coPrem\":3214}]}}]}}]}}\n";
        HashMap hashMap = gson.fromJson(str, HashMap.class);


        System.out.println("args = [" + hashMap + "]");


        String type = getType(hashMap);

        System.out.println("args = [" + type + "]");

    }

    public static String getType(HashMap map){
        Iterator<Map.Entry> i = map.entrySet().iterator();
        if (! i.hasNext())
            return "{}";


        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            Map.Entry e = i.next();
            Object key = e.getKey();
            Object value = e.getValue();
            sb.append(key   == map ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == map ? "(this Map)" : value);
            System.out.println("key:"+key.getClass()+"\t"+"value:"+value.getClass());
            if (! i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }

    }
}
