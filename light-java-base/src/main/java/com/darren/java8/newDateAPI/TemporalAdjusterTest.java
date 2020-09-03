package com.darren.java8.newDateAPI;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * @author Darren
 * @date 2018/4/23 22:02
 */
public class TemporalAdjusterTest {
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_MICROSECOND = "yyyyMMddHHmmss";

    //时间校正器
    @Test
    public void adjust() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        //指定的日期
        LocalDateTime withDayOfMonth = localDateTime.withDayOfMonth(10);
        System.out.println(withDayOfMonth);

        //获取下个周一
        LocalDateTime adjust = localDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println(adjust);

        //lambda表达式 自定义时间算法
        LocalDateTime with = localDateTime.with((ldt) -> {
            LocalDateTime thisDay = (LocalDateTime) ldt;
            DayOfWeek dayOfWeek = thisDay.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return thisDay.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return thisDay.plusDays(2);
            } else {
                return thisDay.plusDays(1);
            }
        });
        System.out.println("下一个工作日是：" + with);
    }

    /**
     * 时间日期格式化
     */
    @Test
    public void dateFormatTest() {

        //内置多个标准时间日期格式
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.format(timeFormatter));

        //自定义格式化费事
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATETIME);
        System.out.println(formatter.format(now));

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(FORMAT_MICROSECOND);
        String formatTime = now.format(pattern);
        System.out.println(formatTime);

        //已格式化的时间字符串转时间
        LocalDateTime parse = LocalDateTime.parse(formatTime, pattern);
        System.out.println(parse);
    }

    @Test
    public void zoneIdTest() {
        //获取所有的时区列表
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        availableZoneIds.forEach(System.out::println);

    }

    @Test
    public void zoneTest() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Athens"));
        System.out.println(now);

        //按时区创建时间
        LocalDateTime shanghai = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(shanghai);

        //带时区的时间格式
        ZonedDateTime zonedDateTime = shanghai.atZone(ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime);

    }
}
