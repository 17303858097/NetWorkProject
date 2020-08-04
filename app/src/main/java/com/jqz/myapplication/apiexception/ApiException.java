package com.jqz.myapplication.apiexception;

public class ApiException extends Throwable {
    private String msg;
    private int code;
    public ApiException(String msg, int code){
        this.msg=msg;
        this.code=code;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
