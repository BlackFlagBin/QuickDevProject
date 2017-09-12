package com.blackflagbin.common.entity.http;

public class EventBusBean {
    private int    code;
    private String name;
    private Object content;

    public EventBusBean(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public EventBusBean(int code, String name, Object content) {
        this.code = code;
        this.name = name;
        this.content = content;
    }

    public EventBusBean(int code, Object content) {
        this.code = code;
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
