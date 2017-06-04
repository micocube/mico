package com.mico.utils.Dbutils;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by micocube on 2017/5/24.
 */
public class DbUtil {

    public static int CRU(String sql) throws Exception{
        Connection conn = ConnectDb.Connect();

        //创建SQL执行工具
        QueryRunner qRunner = new QueryRunner();

        int n = qRunner.update(conn, sql);
//      System.out.println("影响行数:" + n + "！");

        //关闭数据库连接
        DbUtils.closeQuietly(conn);

        return n;

    }

    public static ArrayList<Map<String,Object>> Select(String sql)throws Exception{
        Connection conn = ConnectDb.Connect();

        //创建SQL执行工具
        QueryRunner qRunner = new QueryRunner();

        @SuppressWarnings("unchecked")
        ArrayList<Map<String,Object>> list = (ArrayList<Map<String,Object>>) qRunner.query(conn, sql, new MapListHandler());
        //输出查询结果
//        System.out.println("查询数据条数："+ list.size());
//
//        for(Map<String,Object> l : list)
//            for (Map.Entry entry : l.entrySet()) {
//                System.out.println(entry.getKey()+":"+entry.getValue());
//            }

        //关闭数据库连接
        DbUtils.closeQuietly(conn);

        return list;

    }


}
