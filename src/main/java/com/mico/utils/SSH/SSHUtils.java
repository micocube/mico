package com.mico.utils.SSH;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by micocube on 2017/5/26.
 */
public class SSHUtils {

    @Test
    public void test() throws Exception  {
//        JSchUtil js = new JSchUtil("root", "2QTVraWRmc(CgAr", "114.115.204.16", 22);
        JSchUtil js = new JSchUtil("root", "XHfjENHQV8HbeTH", "114.115.153.246", 22);


        try {
            //114.115.204.16   192.168.1.16 2QTVraWRmc(CgAr
            js.connect();

//            habzrsbd2017
//                    HABz8691
//
//            接口地址：http://111.160.216.81/rest/veri-policyinfo
//            Token：ZBX_)p(o8I7U_HASL
//                    Aes key：ZBX_)p(o8I7U_HASL
//            String genHb = genHb(
//
//                    "accxyz2017",
//                    "http://wechatmalltest.e-acic.cn/wanshitong/PolicyQueryNew",
//                    "BDYZ_ACIC",
//                    "BDYZ_ACIC",
//                    "100"
//
//            );
//
           String genHb = genHb(

                    "zsxnrsyz2017",
                    "http://116.214.128.67/pcs/queryController/queryPolicy.do",
                    "CIGNACMB_PCS_ENDYTPTION_TOKEN",
                    "CIGNACMB_PCS_PWSD_KEY",
                    "100"

            );


            js.execCmd(genHb);


            js.disconnect();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                js.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static String genHb(String username,String url,String token,String key,String type)throws Exception{
        String str = "/usr/local/hb_policy/bin/hb_policy -c 1 -n 10 -d /data/www/iachina/pressure/%s.data -url %s -key %S -token %s";
        String str2 = "/usr/local/hb_policy/bin/hb_policy -c 100 -n 100 -d /data/www/iachina/pressure/%s.data -url %s -key %S -token %s";

        String back = "/usr/local/hb_policy/bin/hb_policy -c 100 -n 240000 -d /data/www/iachina/pressure/%s.data -url %s -key %S -token %s > /data/www/iachina/pressure/%s.log &";
        String test = String.format(str,username,url,genKey(key), genToken(token));
        String Test100 = String.format(str2,username,url,genKey(key),genToken(token));
        String hb = String.format(back,username,url,genKey(key),genToken(token),username);
        System.out.println("test:"+test);
        System.out.println("test100:"+Test100);
        System.out.println("hb:"+hb);
        if(type.equals("1"))
        return test;
        if(type.equals("100"))
            return Test100;
        if(type.equals("hb"))
            return hb;
        return test;
    }

    private static String genKey(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();

        return Hex.encodeHexString(secretKey.getEncoded());

    }

    private static String genToken(String token){
        return  Hex.encodeHexString(token.getBytes()).toLowerCase();
    }
}
