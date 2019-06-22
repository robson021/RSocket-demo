package com.example.common;

import java.io.Serializable;

public class ResponseData implements Serializable {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
