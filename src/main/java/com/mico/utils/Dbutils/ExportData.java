package com.mico.utils.Dbutils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import com.mico.utils.Excel.ReadExcel;
import org.junit.Test;


public class ExportData {


    static String selectGSID = "select id from assurance_company where gsmc like ";

    static String insertSFZHM = "INSERT INTO user_assurance_company (`SFZHM`, `GSID`, `CREATE_TIME`, `UPDATE_TIME`,id_type) VALUES ('%s', '%s', '2017-05-23 00:00:00', '2017-05-23 00:00:00','0101')";

    @Test
    public void test()  throws Exception {

        //DbUtil.CRU("INSERT INTO user_assurance_company (`SFZHM`, `GSID`, `CREATE_TIME`, `UPDATE_TIME`,id_type) VALUES ('A2DAF77907D70A5D3D0D7AB05E636983', '554', '2017-05-23 00:00:00', '2017-05-23 00:00:00','0101')");
        InputStream inputStream = new FileInputStream("/Users/micocube/Documents/测试案例汇总all.xlsx");

        ReadExcel readExcel = ReadExcel.me(inputStream).setSheetIndex(0);

        List<ArrayList<String>> lists  = readExcel.readNextByTitle(0);

        for(ArrayList<String> arrayList : lists)
        {
            if(arrayList.size() != 2){
                System.err.println("Excel提供的公司证件号关系对应出错："+arrayList);
            }else{
                List select = DbUtil.Select(selectGSID+"'%"+arrayList.get(0)+"%'");
                if(select.size()!=1){
                    System.err.println("查询"+arrayList.get(0)+"公司id出错："+arrayList.get(0));
                }else{
                    Map<String,Object> o =(Map<String,Object>) select.get(0);

                    String sql = String.format(insertSFZHM, hexSFZHM(arrayList.get(1).trim()), o.get("id"));
                    System.out.println("查询"+arrayList.get(0)+"公司id,身份证号码:"+arrayList.get(1).trim()+"成功，查询结果 = [" + o.get("id") + "]"+"生成sql = [" + sql + "]");

                    //DbUtil.CRU(sql);
                }
            }

        }
    }


    public static String hexSFZHM(String args){
       return UPPER(MD5(UPPER(MD5(UPPER(args))) + "BDYZEQ"));
    }

    public static String UPPER(String args){

        return args.toUpperCase();
    }

    public static String MD5(String args){

        return MessageDigestUtil.md5Hex(args);
    }
      


}