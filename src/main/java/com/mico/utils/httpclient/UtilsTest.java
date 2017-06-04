package com.mico.utils.httpclient;

import org.junit.Test;

/**
 * @author laids on 2017-02-08 for test.
 */
public class UtilsTest {

    @Test
    public void test() throws Exception {
//        String s = HttpClientUtil.httpGetRequest("https://web.wechat.com/");\

        //http://blog.csdn.net/wx_mdq/article/details/46473227
        System.setProperty ("jsse.enableSNIExtension", "false");
        String s = HttpClientUtil.httpGetRequest("https://web.wechat.com");
        System.out.println(s);
    }
}
