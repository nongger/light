package com.darren.java8.newDateAPI;

import com.darren.utils.DateTools;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/12/4 17:02
 * Desc   :  *
 */
public class CalendarTest {

    @Test
    public void calendar() {
        // 获取日历实例--取本地时间
        Calendar calendar = Calendar.getInstance();

        System.out.println(calendar.getTime());// 获取时间，Date类型
        System.out.println(DateTools.dateToString(calendar.getTime()));
        calendar.add(Calendar.DATE, -5);
        Date endDate = calendar.getTime();
        System.out.println(DateTools.dateToString(endDate));
        calendar.add(Calendar.DATE, -5);
        Date startDate = calendar.getTime();
        System.out.println(DateTools.dateToString(startDate));

        if (endDate.after(startDate)) {
            System.out.println("结束时间在开始时间之后");
        }
        if (startDate.after(endDate)) {
            System.out.println("开始时间在结束时间之后");
        }


    }
}
