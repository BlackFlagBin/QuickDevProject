package com.blackflagbin.common.entity.event;

/**
 * Created by blackflagbin on 2017/7/11.
 */

public class EventBusEntity {
    public String name;
    public Object content;

    public EventBusEntity(String name, Object content) {
        this.name = name;
        this.content = content;
    }
}
