package com.mico.utils.Dbutils;

import com.mico.utils.Excel.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by micocube on 2017/5/27.
 */
public class ExportDatabase {
    @Test
    public void test() throws Exception {
        List<Map<String,Object>> select = DbUtil.Select("select tt.company_name,tt.result from (select * from test_pressure_history order by time desc) tt " +
                "where result IS NOT NULL " +
                "and LENGTH(trim(result))>1 " +
                "and result != \"压测执行超时\" " +
                "and result != \"压测失败。请检查参数并重新尝试。\" " +
                "and result not like \"%Pi.Init() failed%\" " +
                "and result not like \"%Interface Uri%\" " +

                "group by username " +
                "order by time desc;");
        System.out.println("args = [" + select.size() + "]");

        List<PressureResult> pressureResults = new ArrayList<>();
        PressureResult pressureResult = null;

        for(Map<String,Object> map: select){

            pressureResult = new PressureResult();

            String name  = map.get("company_name").toString();
            String result = map.get("result").toString();
            int bflpos = result.indexOf("并发量");
            int bflspos= result.indexOf("<br/>",bflpos);

            int cgspos = result.indexOf("成功的请求数");
            int cgsspos= result.indexOf("<br/>",cgspos);

            int sbspos = result.indexOf("失败的请求数");
            int sbsspos= result.indexOf("<br/>",sbspos);


            int succespos = result.indexOf("业务解析正确的请求数");
            int successpos= result.indexOf("<br/>",succespos);





            int secpos = result.indexOf("平均每秒请求数");
            int secspos= result.indexOf("<br/>",secpos);


            int scpos = result.indexOf("平均响应时长");
            int scspos= result.indexOf("<br/>",scpos);








            String bfl = result.substring(bflpos, bflspos).replace("   :&nbsp;&nbsp;", "");
            String cgs = result.substring(cgspos, cgsspos).replace("   :&nbsp;&nbsp;", "");
            String sb = result.substring(sbspos, sbsspos).replace("     :&nbsp;&nbsp;", "");
            String success = result.substring(succespos,successpos).replace(":&nbsp;&nbsp;","");
            String sec = result.substring(secpos, secspos).replace(" :&nbsp;&nbsp;", "").replace(" [#/sec] [mean]","");
            String sc = result.substring(scpos, scspos).replace("    :&nbsp;&nbsp;", "").replace(" [ms] [mean]","");


            Integer successCount = Integer.valueOf(cgs.replace("成功的请求数", ""));
            Integer failedCount = Integer.valueOf(sb.replace("失败的请求数", ""));
            Integer finishedCount = Integer.valueOf(success.replace("业务解析正确的请求数", ""));
            Integer responseTime = Integer.valueOf(sc.replace("平均响应时长/ms", ""));

            if(failedCount<successCount*0.01 && finishedCount>successCount*0.99 && responseTime<3000) {
                pressureResult.setCompanyName(name);
                pressureResult.setConcurrent(bfl.replace("并发量",""));

                pressureResult.setFailed(failedCount);
                pressureResult.setSum(successCount);

                pressureResult.setPostPerSeconds(sec.replace("平均每秒请求数",""));
                pressureResult.setResponseTime(responseTime);

                pressureResult.setSuccess(finishedCount);
                pressureResults.add(pressureResult);
            }






        }

        System.out.println(pressureResults);

        FileOutputStream fos = new FileOutputStream("/Users/micocube/Documents/最后一次联调结果记录.xls");
        HSSFWorkbook workbook1 = ExcelUtil.makeExcelHead("最后一次联调结果记录", 7);
        String[] secondTitles = {"公司名称","并发量", "总请求数", "失败的请求数","业务解析正确的请求数","平均每秒请求数", "平均响应时长/ms"};
        HSSFWorkbook workbook2 = ExcelUtil.makeSecondHead(workbook1, secondTitles);

        String[] beanProperty = {"companyName","concurrent","sum","failed","success", "postPerSeconds", "responseTime"};

        HSSFWorkbook workbook = ExcelUtil.exportExcelData(workbook2, pressureResults, beanProperty);
        workbook.write(fos);

    }
}
