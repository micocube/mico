package com.mico.workutils.DatabaseOperation;

import org.junit.Test;

import java.util.*;

/**
 * @author mico on 2016-11-26.
 */
public class Select {

    //别名就是表名
    private  String tableName ;
    //左联表名列表
    private  List<String> leftJoinTable = new ArrayList<String>();
    //时间区间查询列名列表
    private  List<String> timeSectionQueryFields = new ArrayList<String>();
    //左联列名列表 查左联表的那些字段
    private  List<String> leftJoinFields = new ArrayList<String>();
    //批量获取的字段 用in 连接  一般是枚举字段或者主键或者外键
    private  List<String> batchFetchFields = new ArrayList<String>();
    //模糊查询的字段
    private  List<String> vagueSelectFields = new ArrayList<String>();
    //表名-左联对象map
    private Map<String,LeftJoin> joinMap = new HashMap<String, LeftJoin>();



    public class LeftJoin{
        private String tableName;
        private String leftValue;
        private String rightValue;


        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("LeftJoin{");
            sb.append("tableName='").append(tableName).append('\'');
            sb.append(", leftValue='").append(leftValue).append('\'');
            sb.append(", rightValue='").append(rightValue).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public LeftJoin(String tableName){
            this.tableName = tableName;
        }
        public  LeftJoin ON(String colname,String anathor){
            this.leftValue = colname;
            this.rightValue = anathor;
            return this;
        }
    }

    public Select(String tableName){
       this.tableName = tableName;
    }





    public Select leftJoin(String join,String letfOn,String rightOn){
        leftJoinTable.add(join);
        joinMap.put(join,new LeftJoin(join).ON(letfOn, rightOn));
        return this;
    }


    public Select timeSection(String field){
        timeSectionQueryFields.add(field);
        return this;
    }



    public Select batchFetch(String field){
        batchFetchFields.add(field);
        return this;
    }
    public Select vagueSelect(String field){
        vagueSelectFields.add(field);
        return this;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Select{");
        sb.append("tableName='").append(tableName).append('\'');
        sb.append(", leftJoinTable=").append(leftJoinTable);
        sb.append(", timeSectionQueryFields=").append(timeSectionQueryFields);
        sb.append(", leftJoinFields=").append(leftJoinFields);
        sb.append(", batchFetchFields=").append(batchFetchFields);
        sb.append(", vagueSelectFields=").append(vagueSelectFields);
        sb.append(", joinMap=").append(joinMap);
        sb.append('}');
        return sb.toString();
    }

    @Test
    public void test() throws Exception{
        System.out.println("args = [" + new Select("cap_handle").leftJoin("cap_resume","cap_handle_no","cap_resume_no").timeSection("createTime") + "]");





    }



}
