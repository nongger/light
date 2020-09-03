package com.darren.interview;

/**
 * 金额转换，阿拉伯数字的金额转换成中国传统的形式。
 * 如：105600123 => 壹亿零仟伍佰陆拾零万零仟壹佰贰拾叁圆整
 *
 * @author Darren
 * @date 2019/3/4 23:33
 */
public class MoneyConvert {
    private static final char[] units = new char[]{'圆', '拾', '佰', '仟', '万', '拾',
            '佰', '仟', '亿'};

    private static final char[] data = new char[]{'零', '壹', '贰', '叁', '肆', '伍',
            '陆', '柒', '捌', '玖'};

    public static void main(String[] args) {

        int money = 855666;

        String strMoney = convert(money);
        System.out.println(strMoney);
    }

    public static String convert(int money) {
        StringBuffer sb = new StringBuffer("整");
        int init = 0;
        while (money != 0) {
            sb.insert(0, units[init++]);
            int number = money % 10;
            sb.insert(0, data[number]);
            money /= 10;
        }

        return sb.toString();
    }
}
