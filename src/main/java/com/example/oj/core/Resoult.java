package com.example.oj.core;

//用来返回运行结果的类
public class Resoult {

    //0表示没出错通过，1表示编译出错，2表示结果出错，4表示超时,3表示运行出错
    private int error;

    //出错原因
    private String reason;

    public int getError() {
        return error;
    }

    public String getReason() {
        return reason;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "resoult{" +
                "error=" + error +
                ", reason='" + reason + '\'' +
                '}';
    }
}
