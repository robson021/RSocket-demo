package com.example.common;

import java.io.Serializable;
import java.util.UUID;

public class RequestData implements Serializable {

    private String uuid = UUID.randomUUID().toString();

    private String text;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
