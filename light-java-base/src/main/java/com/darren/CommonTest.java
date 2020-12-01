package com.darren;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.darren.model.User;
import com.darren.utils.*;
import com.github.wxpay.sdk.WXPayUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CommonTest {
    public static final String FORMAT_MICROSECOND = "HHmmss";
    public static final String FORMAT_DATE = "yyMMdd";

    public static void main(String[] args) {
        System.out.println(WXPayUtil.generateNonceStr());
        System.out.println(DateTools.getCurrentMsecTimestamp());
        System.out.println(Instant.now().toEpochMilli());
        System.out.println(DateTools.getCurrentDateyyyyMMddhhmmss());
        System.out.println(DateTools.getCurrentDateYYYYMMDDHHMMSSsss());
        System.out.println(DateTools.getCurrentFormatDate());
        System.out.println("时间：" + DateTools.stampToDate(String.valueOf(1579502059000L)));

        String timeStr = "2020-01-07 23:00:00";
        System.out.println("开始时间戳：" + DateTools.stringToDate(timeStr, DateTools.FORMAT_DATETIME).getTime());
        String timeStr2 = "2020-01-07 23:59:59";
        System.out.println("结束时间戳：" + DateTools.stringToDate(timeStr2, DateTools.FORMAT_DATETIME).getTime());

        String timeStradd = DateTools.addYYYYMMDDHHMMSSFromatDate(timeStr, 300);
        System.out.println(timeStradd);
        System.out.println("纳秒值：" + System.nanoTime());
        long now = System.currentTimeMillis();
        long end = now - now % 60000;
        System.out.println("当前分钟级时间戳： " + end);

        //for (int i = 0; i < 50; i++) {
        //    genOrderNo();
        //    System.out.println();
        //    try {
        //        Thread.sleep(1000L);
        //    } catch (InterruptedException e) {
        //
        //    }
        //}
    }

    public static String genOrderNo() {
        IdGenUtils idGenUtils = IdGenUtils.hasInstance();
        if (idGenUtils == null) {
            Long workId = null;
            workId = workId != null ? workId : System.nanoTime();
            System.out.println("工作域:" + workId);
            idGenUtils = IdGenUtils.getInstance(workId);
        }
        String id = idGenUtils.nextId();
        System.out.println("生成id值：" + id);
        System.out.println("生成主键长度：" + id.length());
        return id;
    }

    /**
     * 集合相等的判断测试
     */
    @Test
    public void collection() {
        System.out.println(System.currentTimeMillis());
        System.out.println(Instant.now().toEpochMilli());

        String time1 = "1579406132344";
        System.out.println("时间：" + DateTools.stampToDate(time1));

        ArrayList<String> listA = new ArrayList<String>() {{
            add("a");
            add("b");
            add("c");
        }};
        ArrayList<String> listB = new ArrayList<String>() {{
            add("b");
            add("c");
            add("a");
        }};
        System.out.println(listA.equals(listB));// false,缺点：需要先对集合进行排序

        System.out.println(listA.containsAll(listB) && listB.containsAll(listA));// true,交叉包含判断，缺点：无法判断集合包含相同元素的情况[a,b,c]和[a,a,b,c]
//        System.out.println(CollectionUtils.isEqualCollection(listA, listB));// true，推荐，使用简单入参非空即可，算法复杂度低，不用排序

        Collections.sort(listA);
        Collections.sort(listB);
        System.out.println(listA.equals(listB));// true，用于佐证集合排序后可以使用该API判断相等
    }


    /**
     * broker测试
     */
    @Test
    public void brokerMonitor() {
        try {
            String url = String.format("http://%s/monitor/record/list", "10");
            String response = doRequest(url, null, HttpMethod.GET, null);
            JSONArray array = JSON.parseObject(response).getJSONArray("data");
            List<Long> names = new ArrayList<>();
            if (array != null && array.size() > 0) {
                for (int j = 0; j < array.size(); j++) {
                    JSONObject object = array.getJSONObject(j).getJSONObject("value");

                    names.add(object.getLongValue("name"));
                }
            }
            JSONObject ret = new JSONObject();
            ret.put("total", names.size());
            ret.put("result", names);
            System.out.println(ret);
        } catch (Exception e) {
        }

    }

    public static String doRequest(String url, MultiValueMap jsonParams, HttpMethod method, HttpHeaders headers) {
        String result = null;

        try {
            HttpEntity<MultiValueMap> formEntity = new HttpEntity<>(jsonParams, headers);
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(800);
            requestFactory.setReadTimeout(800);

            RestTemplate template = new RestTemplate(requestFactory);
            ResponseEntity<String> responseEntity = template.exchange(url, method, formEntity, String.class);

            result = responseEntity.getBody();
        } catch (Exception e) {
        }

        return result;

    }

    @Test
    public void split() {
        String names = null;

        String[] split = StringUtils.split(names, ",");//names.split(",");
        System.out.println(StringUtils.join(split, ","));
    }

    @Test
    public void bigDecimalTest() {
        //商品的外币金额 单位元
        BigDecimal decimalAmount = new BigDecimal("0.00");
        BigDecimal bigDecimal1 = decimalAmount.movePointRight(2);
        System.out.println(bigDecimal1.toString());
        decimalAmount = decimalAmount.multiply(new BigDecimal(100));
        //单位分
        System.out.println(decimalAmount.intValue());

        System.out.println(org.springframework.util.StringUtils.isEmpty(" "));
        BigDecimal bigDecimal = new BigDecimal(18937838).movePointLeft(2);
        System.out.println("交易时间：" + DateTools.dateToString(new Date(), "yyyyMMddHHmmss") + "  交易金额：" + bigDecimal);

        // 金额去0，转string
        decimalAmount = new BigDecimal("123.00100");
        System.out.println(decimalAmount.stripTrailingZeros().toPlainString());

    }

    @Test
    public void secondBetweenNow() {
        String str = "2018-05-29 17:23:19";
        try {
            DateFormat dateFormat = new SimpleDateFormat(DateTools.FORMAT_DATETIME);
            Date date = dateFormat.parse(str);
            //Date date = stringToDate(str, DateTools.FORMAT_DATETIME);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            //加second s
            cal.add(Calendar.SECOND, 300);
            // cal.getTime();
            long between = DateTools.getSecond(cal.getTime(), new Date());
            System.out.println(between);
        } catch (Exception e) {

        }

    }

    @Test
    public void testString() {
        String prfix = "622252";
        String cardNo = "6222521234567895";
        System.out.println(cardNo.startsWith(prfix));


        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(FORMAT_MICROSECOND);
        String formatTime = LocalTime.now().format(pattern);
        System.out.println(formatTime);


        DateTimeFormatter patternDate = DateTimeFormatter.ofPattern(FORMAT_DATE);
        String formatDate = LocalDate.now().format(patternDate);
        System.out.println("格式化日期：" + formatDate);

        Random random = new Random();
        System.out.println(random.nextInt());

        int idt = new Random().nextInt(900000) + 100000;
        System.out.println(idt);

        //for (int j = 0; j < 100; j++) {
        //    List<Integer> idList = new LinkedList<>();
        //    for (int i = 0; i < 1000; i++) {
        //        int ids = new SecureRandom().nextInt(900000) + 100000;
        //        if (idList.contains(ids)) {
        //            System.out.println("重复了" + ids + "第" + i);
        //        } else {
        //            idList.add(ids);
        //        }
        //
        //    }
        //
        //}
        //System.out.println(ids);

        String addr = "2900-290000-290000";

        String[] split = addr.split("-");
        System.out.println(split.length);
        Arrays.stream(split).forEach(System.out::println);

        System.out.println(StringUtils.defaultIfBlank("hello", "Monkey"));
        String log = "%s新建场景:%s, 场景id为:%s";
        System.out.println(String.format(log, "hello", 333, 90));

        String expectValue = "11";
        String realValue = "123";
        System.out.println(Arrays.asList(expectValue.split(",")).contains(realValue));
        System.out.println(expectValue.compareTo(realValue) >= 0);
    }

    @Test
    public void fastJsonTest() {
//        JSONObject jsonObject = JSON.parseObject("");
//        jsonObject.getIntValue("code");
//        System.out.println(jsonObject);
        System.out.println(String.format("%018d", 1112727388849941123L));
        System.out.println("000000000000000111".length());

    }

    /**
     * xml生成器比较
     */
    @Test
    public void testXMLContains() {
        User darren = new User(11, "darren", 21);
        //darren.setTransCode("00");
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        String xml = xStream.toXML(darren);
        System.out.println(xml);
        System.out.println("======================");

        String toXML = xStream.toXML(getParam());
        System.out.println(toXML);
        System.out.println("======================");

        try {
            String reqBody = WXPayUtil.mapToXml(getParam());// 请求数据
            System.out.println(reqBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(XStreamUtil.bean2Xml(darren));

    }

    @Test
    public void localDate() {
        LocalDate localDate = LocalDate.now().minusDays(1);
        System.out.println(localDate);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        System.out.println(dateTimeFormatter.format(localDate));

    }


    private Map<String, String> getParam() {
        Map<String, String> appParam = new HashMap<>();
        appParam.put("service", "create_forex_trade");
        appParam.put("partner", "2088621900174893");
        appParam.put("_input_charset", "UTF-8");
        appParam.put("notify_url", "www.city.com");
        appParam.put("return_url", "www.baidu.com");
        appParam.put("out_trade_no", "20180626105557");
        appParam.put("subject", "ala");
        appParam.put("total_fee", "1");
        appParam.put("body", "Baby cloth");
        appParam.put("currency", "USD");
        appParam.put("product_code", "NEW_OVERSEAS_SELLER");
        return appParam;
    }

    /**
     * 加密测试
     */
    @Test
    public void testSign() {
        String token = "4c90ded0b4e0af4de377cecf43ca9828";
        System.out.println(SignUtils.sign(getParam(), token));

        String datast = "1540347829609";
        System.out.println("单号长度：" + datast.length());
        System.out.println(datast.substring(18, 20));

        System.out.println("sql:  " + AESUtils.encryptToBase64("UPDATE channel SET channel_id='301001000000741' WHERE id=2"));
        System.out.println("type: " + AESUtils.encryptToBase64("update"));

        System.out.println(AESUtils.decryptFromBase64("nV9qc/iIPUoAtuAu1oZUcsUu4j9tgNhekZF49DRVxxs="));

    }

    @Test
    public void dateTest() {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = new Date();
            String str = format.format(d);
            System.out.println(d);
            Date d2 = format.parse(str);

            System.out.println(format2.format(d2));

            int dayMis = 1000 * 60 * 60 * 24;// 一天的毫秒-1
            // 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            long curMillisecond = d2.getTime();// 当天的毫秒
            long resultMis = curMillisecond + (dayMis - 1); // 当天最后一秒
            // 得到我须要的时间 当天最后一秒
            Date resultDate = new Date(resultMis);

            System.out.println(format2.format(resultDate));
            String startTime = "2020-02-25 10:00:00";
            String endTime = "2020-02-25 18:00:00";
            Date startDate = format.parse(startTime);
            Date endDate = format.parse(endTime);

            // 日历使用
            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;//DateUtil.getMonth(sendTime);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);

            // 创建一个定时的cron表达式
            String cron = String.format("0 %s %s %s %s ? %s", min, hour, day, month, year);
            System.out.println(cron);

            // 获取mod两分钟的平均值
            long lastSecond = System.currentTimeMillis() - 60000;
            long second = lastSecond - lastSecond % 60000;
            System.out.printf("分钟级时间戳,\n\"start\":%d, \n\"end\": %d\n", second, lastSecond);


        } catch (ParseException e) {

        }
    }

    /**
     * 汉字编码方式测试
     */
    @Test
    public void stringEncode() {
        try {
            System.out.println("王禹玥".getBytes("GB2312").length);
            System.out.println("王禹玥".getBytes("GB18030").length);
            System.out.println("王禹玥".getBytes("GBK").length);
            System.out.println("王禹玥".getBytes("utf-8").length);
        } catch (UnsupportedEncodingException e) {

        }
        String name = "赵钱孙李11南哥";
        try {
            String realName = new String(name.getBytes("UTF-8"), "GB2312");
            System.out.println("编码前的字符串：" + name + "  编码前长度：" + name.length());
            System.out.println("编码后的字符串：" + realName + "  编码后长度：" + realName.length());
            //字符串所占字节数判断
            System.out.println("GB2312编码长度：" + name.getBytes("GB2312").length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRandom() {
        for (int i = 0; i < 1000; i++) {
            int index = new Random().nextInt(3);
            System.out.println(index);
        }
    }


}
