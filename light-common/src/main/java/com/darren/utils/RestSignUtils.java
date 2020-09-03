package com.darren.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;
import java.util.*;

public class RestSignUtils {
    /**
     * 对实体类进行签名
     * @param o
     * @param token
     */
    public static String sign(Object o, String token) {
        JsonElement jsonElement = JsonUtils.parseJsonFromObj(o);
        try {
            Map<String, String> paramMap = new HashMap<>();
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Iterator<Map.Entry<String, JsonElement>> iterator = jsonObject.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                if (entry.getValue().isJsonPrimitive()) {
                    paramMap.put(entry.getKey(), entry.getValue().getAsString());
                } else {
                    paramMap.put(entry.getKey(), entry.getValue().toString());
                }
            }

            return getSign(paramMap, token);
        } catch (Exception e) {
            CommonLog.error("sign failed. error [{}]", e.getMessage());
            throw new RuntimeException("签名计算错误");
        }
    }

    public static String  getSign(Map<String, String> paramterMap, String payToken) {
        List<Map.Entry<String, String>> paramsList = new ArrayList<>(paramterMap.entrySet());
        try {
            Collections.sort(paramsList, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });
            List<String> params = new ArrayList<>();
            for (Map.Entry<String, String> singleOne: paramsList) {
                if (!"sign".equals(singleOne.getKey())) {
                    params.add(String.format("%s=%s", singleOne.getKey(), singleOne.getValue()));
                }
            }
            params.add("token=" + payToken);

            String orignalSign = StringUtils.join(params, "&");
            // TODO remove
//            CommonLog.warn("encodestring [{}]", orignalSign);
            return Md5Utils.GetMD5Code(orignalSign).toLowerCase();
        } catch (Exception e) {
            CommonLog.warn("parameter sort failed. {}", e.getMessage());
        }
        return null;
    }

    public static String sign(Map<String, Object> map, String payToken) {
        return Md5Utils.GetMD5Code(signStr(map, payToken)).toLowerCase();
    }

    public static String signStr(Map<String, Object> map, String payToken) {
        StringBuilder signBuilder = buildSign(map);
        if (StringUtils.isNotEmpty(payToken)) {
            signBuilder.append("&token=").append(payToken);
        }
        return signBuilder.toString();
    }

    /**
     * 调用接口的签名
     * @param paramterMap
     * @return
     */
    public static String getApiSign(Map<String, String> paramterMap, String signKey) {
        List<Map.Entry<String, String>> paramsList = new ArrayList<>(paramterMap.entrySet());
        try {
            Collections.sort(paramsList, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });
            List<String> params = new ArrayList<>();
            for (Map.Entry<String, String> singleOne: paramsList) {
                if (!"sign".equals(singleOne.getKey())) {
                    params.add(String.format("%s=%s", singleOne.getKey(), encodedFix(URLEncoder.encode(singleOne.getValue(), "UTF-8"))));
                }
            }
            String orignalSign = org.apache.commons.lang.StringUtils.join(params, "&");
            return Md5Utils.GetMD5Code(String.format("%s%s", orignalSign, signKey));
        } catch (Exception e) {
            CommonLog.warn("parameter getApiSign failed. paramterMap = {}, {}", paramterMap, e.getMessage());
        }
        return null;
    }

    private static String encodedFix(String encoded) {
        encoded = encoded.replace("+", "%20");
        encoded = encoded.replace("*", "%2A");
        encoded = encoded.replace("%7E", "~");

        encoded = encoded.replace("!", "%21");
        encoded = encoded.replace("(", "%28");
        encoded = encoded.replace(")", "%29");
        encoded = encoded.replace("'", "%27");
        return encoded;
    }

    private static String signStr(Object obj) {
        if (obj == null)
            return null;

        if (obj instanceof Map) {
            return buildSign((Map<String, Object>) obj).insert(0, '{').append('}').toString();
        }

        if (obj instanceof List) {
            return buildSign((List<Object>) obj).insert(0, '[').append(']').toString();
        }

        return obj.toString();
    }

    private static StringBuilder buildSign(Map<String, Object> map) {
        if (!(map instanceof TreeMap)) {
            map = new TreeMap<>(map); // 按字母顺序排序
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!"sign".equals(key) && value != null) {
                builder.append(key).append("=").append(signStr(value)).append("&");
            }
        }
        return builder.deleteCharAt(builder.length() - 1);
    }

    private static StringBuilder buildSign(List<Object> list) {
        StringBuilder builder = new StringBuilder();

        if (list == null || list.isEmpty()) {
            return builder;
        }

        for (Object obj : list) {
            builder.append(signStr(obj)).append(",");
        }

        return builder.deleteCharAt(builder.length() - 1);
    }
}
