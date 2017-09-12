package com.blackflagbin.common.http.interceptor;


import com.blackflagbin.common.entity.http.CookieResult;
import com.blackflagbin.common.util.CookieDbUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * gson持久化截取保存数据
 */
public class CookieInterceptor implements Interceptor {
    private CookieDbUtil dbUtil;
    /*是否缓存标识*/
    private boolean      cache;
    /*url*/
    private String       url;

    public CookieInterceptor(boolean cache) {
        dbUtil = CookieDbUtil.getInstance();
        this.cache = cache;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        url = request.url().url().toString();
        Response response = chain.proceed(request);
        if (cache) {
            ResponseBody body = response.body();
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.defaultCharset();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String bodyString = buffer.clone().readString(charset);
            CookieResult result = dbUtil.queryCookieBy(this.url);
            long time = System.currentTimeMillis();
            /*保存和更新本地数据*/
            if (result == null) {
                result = new CookieResult(this.url, bodyString, time);
                dbUtil.saveCookie(result);
            } else {
                result.setResult(bodyString);
                result.setTime(time);
                dbUtil.updateCookie(result);
            }
        }
        return response;
    }
}
