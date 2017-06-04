package com.mico.workutils.DatabaseOperation;



import com.mico.workutils.annotation.JdbcConnection;
import com.mico.workutils.util.StrUtils;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JdbcConnection(url = "jdbc:mysql://192.168.0.201:3305/jtyjy_mmhome",
        userName = "root", password = "root", tableName = "mm_delivery_history",
        jdbcDriver = "com.mysql.jdbc.Driver", tablePrimaryKey = "mm_delivery_history_id",
        ignoreJdbcColumn = "createtime,modifytime",entityName = "MMDeliveryHistory"
)
public class DatabaseSchame {
    private static Map<String, Object> paramMap;



    //列名
    private List<String> columns = new ArrayList<String>();
    //列的jdbc类型
    private List<String> colTypes = new ArrayList<String>();
    //列名-列类型 map
    private Map<String, String> colnameColtypes = new HashMap<String, String>();
    //列名 - 列size map
    private Map<String, Integer> colnameColsize = new HashMap<String, Integer>();
    //列名-属性名 map
    private Map<String, String> colnameProname = new HashMap<String, String>();
    //属性及属性类型不含主键
    private Map<String, String> pronameProtype = new HashMap<String, String>();
    //属性及属性类型 含主键
    private Map<String, String> pronameKeyProtype = new HashMap<String, String>();
    //属性及其数据库注释
    private Map<String, String> pronameColcomments = new HashMap<String, String>();
    //是否使用到了util包里的类型
    private boolean f_util = false;
    //是否使用的sql包里的类型
    private boolean f_sql = false;
    //数据库主键列名
    private String jdbcPrimaryKey;
    //实体类主键列名
    private String primaryKeyName;
    //表名
    private String tableName;
    //实体类名
    private String entityName;


    @Test
    public void test() throws Exception {
        DatabaseSchame schame = getSchame();
        System.out.println(schame);
    }



    public static DatabaseSchame getSchame(String tableName) {
        DatabaseSchame db = new DatabaseSchame();
        Class<DatabaseSchame> entityHelperClass = DatabaseSchame.class;
        JdbcConnection annotation = entityHelperClass.getAnnotation(JdbcConnection.class);
        if (null != annotation) {
            String password = annotation.password();
            String url = annotation.url();
            String userName = annotation.userName();
            String jdbcDriver = annotation.jdbcDriver();
            String tablePrimaryKey = annotation.tablePrimaryKey();
            String ignoreJdbcColumn = annotation.ignoreJdbcColumn();

            db.entityName =!"".equals(annotation.entityName()) ? annotation.entityName() : StrUtils.processDataBaseLable(tableName, true);
            db.jdbcPrimaryKey = tablePrimaryKey;
            db.primaryKeyName = StrUtils.processDataBaseLable(tablePrimaryKey, false);
            db.tableName = tableName;
            //创建连接
            Connection con = null;
            //查要生成实体类的表
            String sql = "select * from " + tableName;
            PreparedStatement pStemt = null;
            ResultSet rs = null;
            try {
                try {
                    Class.forName(jdbcDriver);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                con = DriverManager.getConnection(url, userName, password);

                pStemt = con.prepareStatement(sql);
                ResultSetMetaData rsmd = pStemt.getMetaData();
                int size = rsmd.getColumnCount();   //统计列
                for (int i = 0; i < size; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    int columnDisplaySize = rsmd.getColumnDisplaySize(i + 1);
                    String columnTypeName = rsmd.getColumnTypeName(i + 1);
                    if (ignoreJdbcColumn.contains(columnName)) {
                        continue;
                    }
                    db.pronameKeyProtype.put(StrUtils.processDataBaseLable(columnName, false), StrUtils.sqlType2JavaType(columnTypeName));
                    if (tablePrimaryKey.equalsIgnoreCase(columnName)) {
                        continue;
                    }
                    db.columns.add(columnName);
                    db.colTypes.add(columnTypeName);
                    db.colnameColtypes.put(columnName, columnTypeName);
                    db.colnameColsize.put(columnName, columnDisplaySize);
                    db.colnameProname.put(columnName, StrUtils.processDataBaseLable(columnName, false));
                    db.pronameProtype.put(StrUtils.processDataBaseLable(columnName, false), StrUtils.sqlType2JavaType(columnTypeName));

                    if (columnTypeName.equalsIgnoreCase("datetime") || columnTypeName.equalsIgnoreCase("timestamp")) {
                        db.f_util = true;
                    }
                    if (columnTypeName.equalsIgnoreCase("image") || columnTypeName.equalsIgnoreCase("text")) {
                        db.f_sql = true;
                    }
                }

                rs = pStemt.executeQuery("show full columns from " + tableName);
                while (rs.next()) {
                    String field = rs.getString("Field");
                    String comment = rs.getString("Comment");
                    if (ignoreJdbcColumn.contains(field)) {
                        continue;
                    }
                    db.pronameColcomments.put(StrUtils.processDataBaseLable(field, false), comment);
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != pStemt) {
                        pStemt.close();
                    }
                    if (null != rs) {
                        rs.close();
                    }
                    if (null != con) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //数据库连接


        return db;
    }
    public static DatabaseSchame getSchame() {
        DatabaseSchame db = new DatabaseSchame();
        Class<DatabaseSchame> entityHelperClass = DatabaseSchame.class;
        JdbcConnection annotation = entityHelperClass.getAnnotation(JdbcConnection.class);
        if (null != annotation) {
            String password = annotation.password();
            String url = annotation.url();
            String userName = annotation.userName();
            String tableName = annotation.tableName();
            String jdbcDriver = annotation.jdbcDriver();
            String tablePrimaryKey = annotation.tablePrimaryKey();
            String ignoreJdbcColumn = annotation.ignoreJdbcColumn();

            db.entityName =!"".equals(annotation.entityName()) ? annotation.entityName() : StrUtils.processDataBaseLable(tableName, true);
            db.jdbcPrimaryKey = tablePrimaryKey;
            db.primaryKeyName = StrUtils.processDataBaseLable(tablePrimaryKey, false);
            db.tableName = tableName;
            //创建连接
            Connection con = null;
            //查要生成实体类的表
            String sql = "select * from " + tableName;
            PreparedStatement pStemt = null;
            ResultSet rs = null;
            try {
                try {
                    Class.forName(jdbcDriver);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                con = DriverManager.getConnection(url, userName, password);

                pStemt = con.prepareStatement(sql);
                ResultSetMetaData rsmd = pStemt.getMetaData();
                int size = rsmd.getColumnCount();   //统计列
                for (int i = 0; i < size; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    int columnDisplaySize = rsmd.getColumnDisplaySize(i + 1);
                    String columnTypeName = rsmd.getColumnTypeName(i + 1);
                    if (ignoreJdbcColumn.contains(columnName)) {
                        continue;
                    }
                    db.pronameKeyProtype.put(StrUtils.processDataBaseLable(columnName, false), StrUtils.sqlType2JavaType(columnTypeName));
                    if (tablePrimaryKey.equalsIgnoreCase(columnName)) {
                        continue;
                    }
                    db.columns.add(columnName);
                    db.colTypes.add(columnTypeName);
                    db.colnameColtypes.put(columnName, columnTypeName);
                    db.colnameColsize.put(columnName, columnDisplaySize);
                    db.colnameProname.put(columnName, StrUtils.processDataBaseLable(columnName, false));
                    db.pronameProtype.put(StrUtils.processDataBaseLable(columnName, false), StrUtils.sqlType2JavaType(columnTypeName));

                    if (columnTypeName.equalsIgnoreCase("datetime") || columnTypeName.equalsIgnoreCase("timestamp")) {
                        db.f_util = true;
                    }
                    if (columnTypeName.equalsIgnoreCase("image") || columnTypeName.equalsIgnoreCase("text")) {
                        db.f_sql = true;
                    }
                }

                rs = pStemt.executeQuery("show full columns from " + tableName);
                while (rs.next()) {
                    String field = rs.getString("Field");
                    String comment = rs.getString("Comment");
                    if (ignoreJdbcColumn.contains(field)) {
                        continue;
                    }
                    db.pronameColcomments.put(StrUtils.processDataBaseLable(field, false), comment);
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != pStemt) {
                        pStemt.close();
                    }
                    if (null != rs) {
                        rs.close();
                    }
                    if (null != con) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //数据库连接


        return db;
    }


    public static Map<String, Object> getParamMap() {
        return paramMap;
    }


    public static void setParamMap(Map<String, Object> paramMap) {
        DatabaseSchame.paramMap = paramMap;
    }


    public List<String> getColumns() {
        return columns;
    }


    public void setColumns(List<String> columns) {
        this.columns = columns;
    }


    public List<String> getColTypes() {
        return colTypes;
    }


    public void setColTypes(List<String> colTypes) {
        this.colTypes = colTypes;
    }


    public Map<String, String> getColnameColtypes() {
        return colnameColtypes;
    }


    public void setColnameColtypes(Map<String, String> colnameColtypes) {
        this.colnameColtypes = colnameColtypes;
    }


    public Map<String, Integer> getColnameColsize() {
        return colnameColsize;
    }


    public void setColnameColsize(Map<String, Integer> colnameColsize) {
        this.colnameColsize = colnameColsize;
    }


    public Map<String, String> getColnameProname() {
        return colnameProname;
    }


    public void setColnameProname(Map<String, String> colnameProname) {
        this.colnameProname = colnameProname;
    }


    public Map<String, String> getPronameProtype() {
        return pronameProtype;
    }


    public void setPronameProtype(Map<String, String> pronameProtype) {
        this.pronameProtype = pronameProtype;
    }


    public Map<String, String> getPronameColcomments() {
        return pronameColcomments;
    }


    public void setPronameColcomments(Map<String, String> pronameColcomments) {
        this.pronameColcomments = pronameColcomments;
    }


    public boolean isF_util() {
        return f_util;
    }


    public void setF_util(boolean f_util) {
        this.f_util = f_util;
    }


    public boolean isF_sql() {
        return f_sql;
    }


    public void setF_sql(boolean f_sql) {
        this.f_sql = f_sql;
    }


    public String getJdbcPrimaryKey() {
        return jdbcPrimaryKey;
    }


    public void setJdbcPrimaryKey(String jdbcPrimaryKey) {
        this.jdbcPrimaryKey = jdbcPrimaryKey;
    }


    public String getPrimaryKeyName() {
        return primaryKeyName;
    }


    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }


    public String getTableName() {
        return tableName;
    }


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public String getEntityName() {
        return entityName;
    }


    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }


    public Map<String, String> getPronameKeyProtype() {
        return pronameKeyProtype;
    }


    public void setPronameKeyProtype(Map<String, String> pronameKeyProtype) {
        this.pronameKeyProtype = pronameKeyProtype;
    }


    @Override
    public String toString() {
        return "DatabaseSchame{" +
                "\ncolumns=" + columns +
                ",\ncolTypes=" + colTypes +
                ", \ncolnameColtypes=" + colnameColtypes +
                ", \ncolnameColsize=" + colnameColsize +
                ", \ncolnameProname=" + colnameProname +
                ", \npronameProtype=" + pronameProtype +
                ", \npronameKeyProtype=" + pronameKeyProtype +
                ", \npronameColcomments=" + pronameColcomments +
                ", \nf_util=" + f_util +
                ", \nf_sql=" + f_sql +
                ", \njdbcPrimaryKey='" + jdbcPrimaryKey + '\'' +
                ", \nprimaryKeyName='" + primaryKeyName + '\'' +
                ", \ntableName='" + tableName + '\'' +
                ", \nentityName='" + entityName + '\'' +
                '}';
    }
}
