package com.sxs.http.open;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpConnectionPool {

    // 当前线程资源（某个线程固定使用一个连接）
    private final static ThreadLocal<Map<String, HttpURLConnection>> connectionThreadLocal = new ThreadLocal<>();

    /**
     * 获取http连接
     * @param url 请求地址
     * @return http连接
     * @throws IOException io异常
     */
    protected static HttpURLConnection getHttpConnection(String url) throws IOException {
        if (connectionThreadLocal.get() == null || connectionThreadLocal.get().get(url) == null) {
            // 首次执行就去创建
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            // 封装数据
            Map<String, HttpURLConnection> threadMap = new HashMap<>();
            threadMap.put(url, connection);
            connectionThreadLocal.set(threadMap);
        }
        return connectionThreadLocal.get().get(url);
    }

}