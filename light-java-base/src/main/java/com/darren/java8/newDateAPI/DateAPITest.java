package com.darren.java8.newDateAPI;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * public final class LocalDateTime 是不可变的对象
 *
 * @author Darren
 * @date 2018/4/18
 */
public class DateAPITest {

    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    // 1. LocalDate、LocalTime、LocalDateTime
    @Test
    public void localDateTimeTest() {

        // 根据当前时间创建对象 now
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        // 根据指定日期/时间创建对象of()
        LocalDateTime of = LocalDateTime.of(2018, 04, 18, 22, 19, 40);
        System.out.println(of);
        System.out.println("=======================");

        // 日期的运算 plus 加 minus 减
        LocalDateTime localDateTime1 = localDateTime.plusYears(2);
        System.out.println(localDateTime1);

        LocalDateTime localDateTime2 = localDateTime.minusDays(4);
        System.out.println(localDateTime2);

        System.out.println("3分钟前："+ localDateTime.minusMinutes(3));

        System.out.println("=======================");

        // 将月份天数、年份天数、月份、年份修改为指定的值,并返回新的LocalDate对象
        LocalDateTime localDateTime3 = localDateTime.withDayOfMonth(2);
        System.out.println(localDateTime3);
        LocalDateTime localDateTime4 = localDateTime.withYear(1990);
        System.out.println(localDateTime4);

        System.out.println("=======================");

        if (localDateTime.isBefore(localDateTime1)) {
            System.out.println(localDateTime + " 在 " + localDateTime1 + "之前");
        }
        if (localDateTime.isAfter(localDateTime2)) {
            System.out.println(localDateTime + " 在 " + localDateTime2 + "之后");
        }
        System.out.println("=======================");

        // 获取时间中的部分值
        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getDayOfYear());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getDayOfWeek());

        System.out.println("=======================");
        long timestamp = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0))).getTime();
        System.out.println(timestamp);

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FORMAT_DATETIME);

    }

    /**
     * 时间戳
     * Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
     */
    @Test
    public void instantTest() {
        Instant now = Instant.now();//默认使用 UTC(世界协调时间) 时区 并非天朝日期
        System.out.println(now.toEpochMilli());
        System.out.println(System.currentTimeMillis());
        System.out.println(now);
        //偏移量运算
        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);


        //相较与Unix元年获取时间戳
        Instant epochSecond = Instant.ofEpochSecond(60);
        System.out.println(epochSecond);
    }

    @Test
    public void betweenTest() {
        Instant now = Instant.now();//默认使用 UTC(世界协调时间) 时区 并非天朝日期
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        Instant after = Instant.now();
        //Duration:用于计算两个“时间”间隔
        Duration between = Duration.between(now, after);
        //毫秒时间戳
        System.out.println(between.toMillis());
        System.out.println(Duration.between(after, now).toMillis());

        //Duration:用于计算两个“时间”间隔
        LocalTime localTime = LocalTime.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        LocalTime localTime1 = LocalTime.now();

        System.out.println(Duration.between(localTime, localTime1).toMillis());

        //Period:用于计算两个“日期”间隔
        LocalDate localDate = LocalDate.now();
        LocalDate localDate1 = LocalDate.of(2016, 9, 30);
        Period between1 = Period.between(localDate1, localDate);
        System.out.println(between1);
        System.out.println("两个日期相差 ：" + between1.getYears() + "年 " + between1.getMonths() + "月 " + between1.getDays() + "天");

    }

    @Test
    public void dateFormat() {

        LocalDate localDate = LocalDate.now().minusDays(1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MMdd000000");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd000000");

        LocalDate end = localDate.plusDays(1);
        System.out.println(dateTimeFormatter.format(localDate));
        System.out.println(dateTimeFormatter.format(end));

        // 获取一天时间的最大最小值
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dayStart = dtf.format(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        String dayEnd = dtf.format(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        System.out.println(dayStart+"-"+dayEnd);

    }


}
