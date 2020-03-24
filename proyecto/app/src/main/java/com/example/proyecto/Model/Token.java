package com.example.proyecto.Model;

public class Token {
    private String data;
    private int errorCode;
    private String errorMsg;

    public Token(String data, int errorCode, String errorMsg){
        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getData() {
        return data;
    }
    public void setData(String data){
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(int errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg){
        this.errorMsg = errorMsg;
    }
}
