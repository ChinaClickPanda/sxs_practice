package com.sxs.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeaderUtil {

    private static final String defaultSymbol = ">>";

    /**
     * 处理请求头
     * @return 请求头
     */
    public static Map<String, List<String>> getHeader(List<String> headerList, String splitSymbol) {
        if (splitSymbol == null || "".equals(splitSymbol)) {
            splitSymbol = defaultSymbol;
        }
        Map<String, List<String>> headerMap = new HashMap<>();
        // 处理请求
        for (String header: headerList) {
            String[] subList = header.split(splitSymbol);
            String headerKey = subList[0];
            if (headerKey == null || "".equals(headerKey) || subList.length <= 1) {
                continue;
            }
            List<String> varList = headerMap.get(headerKey);
            for (int i = 1; i < subList.length; i++) {
                if (varList == null) {
                    varList = new ArrayList<>();
                    headerMap.put(headerKey, varList);
                }
                varList.add(subList[i]);
            }
        }
        return headerMap;
    }

}
