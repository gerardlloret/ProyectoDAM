package com.example.proyecto.Model;

public class Token {
    private String token;
    private String error;

    public Token(String data, String errorCode){
        this.token = data;
        this.error = errorCode;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }

    public String getError() {
        return error;
    }
    public void getError(int errorCode){
        this.error = error;
    }

}
