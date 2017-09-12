package com.blackflagbin.common.entity.http;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by blackflagbin on 2017/9/11.
 */

@Entity
public class CookieResult {
    @Id
    private Long   id;
    private String url;
    private String result;
    private long   time;

    @Generated(hash = 430401114)
    public CookieResult(Long id, String url, String result, long time) {
        this.id = id;
        this.url = url;
        this.result = result;
        this.time = time;
    }

    @Generated(hash = 43459054)
    public CookieResult() {
    }

    public CookieResult(String url, String bodyString, long time) {
        this.url=url;
        this.result=bodyString;
        this.time=time;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
