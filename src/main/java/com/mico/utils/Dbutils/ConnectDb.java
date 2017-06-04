package com.mico.utils.Dbutils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;   
  
public class ConnectDb {  
    private static String driveClassName = "com.mysql.jdbc.Driver";  
    private static String url = "jdbc:mysql://114.115.204.16:8066/iachina_server?autoReconnect=true&useUnicode=true&characterEncoding=utf-8&cachePrepStmts=true&zeroDateTimeBehavior=convertToNull";
      
    private static String user = "iachina";
    private static String password = "nDcdC7jn@q39J=";
      
    public static Connection Connect(){  
        Connection conn = null;  
          
        //load driver  
        try {  
            Class.forName(driveClassName);  
        } catch (ClassNotFoundException  e) {  
            System.out.println("load driver failed!");  
            e.printStackTrace();  
        }  
          
        //connect db  
        try {  
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {  
            System.out.println("connect failed!");  
            e.printStackTrace();  
        }         
          
        return conn;  
    }  
}  