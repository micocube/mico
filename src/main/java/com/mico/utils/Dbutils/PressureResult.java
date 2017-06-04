package com.mico.utils.Dbutils;

/**
 * Created by micocube on 2017/5/31.
 */
public class PressureResult {
    public String companyName;
    public String concurrent;
    public Integer sum;
    public Integer failed;
    public Integer success;
    public String postPerSeconds;
    public Integer responseTime;


    /**
     * 计算表达式
     * =IF(AND(D3<C3*0.01,E3>C3*0.99,G3<3000),"通过","未通过")
     *
     */

    @Override
    public String toString() {
        return "PressureResult{" +
                "companyName='" + companyName + '\'' +
                ", concurrent='" + concurrent + '\'' +
                ", sum='" + sum + '\'' +
                ", failed='" + failed + '\'' +
                ", success='" + success + '\'' +
                ", postPerSeconds='" + postPerSeconds + '\'' +
                ", responseTime='" + responseTime + '\'' +
                '}';
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(String concurrent) {
        this.concurrent = concurrent;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Integer responseTime) {
        this.responseTime = responseTime;
    }

    public String getPostPerSeconds() {
        return postPerSeconds;
    }

    public void setPostPerSeconds(String postPerSeconds) {
        this.postPerSeconds = postPerSeconds;
    }


}
