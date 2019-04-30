package com.company.qcy.Utils;

import com.blankj.utilcode.util.TimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {


    /**
     * 显示几天钱几周前几月前几年前
     * @param d
     * @return
     */
    public static String getFriendlytime(Date d){
        long delta = (new Date().getTime()-d.getTime())/1000;
        if(delta<=0)return d.toLocaleString();
        if(delta/(60*60*24*365) > 0)return TimeUtils.date2String(d).substring(0,16);
        if(delta/(60*60*24*30) > 0)return TimeUtils.date2String(d).substring(0,16);
        if(delta/(60*60*24*7) > 0)return TimeUtils.date2String(d).substring(0,16);
        if(delta/(60*60*24) > 0) return TimeUtils.date2String(d).substring(0,16);
        if(delta/(60*60) > 0)return delta/(60*60) +"小时前";
        if(delta/(60) > 0) return delta/(60) +"分钟前";
        return "刚刚";
    }
//    public static String getFriendlytime(Date d){
//        long delta = (new Date().getTime()-d.getTime())/1000;
//        if(delta<=0)return d.toLocaleString();
//        if(delta/(60*60*24*365) > 0)return delta/(60*60*24*365) +"年前";
//        if(delta/(60*60*24*30) > 0)return delta/(60*60*24*30) +"个月前";
//        if(delta/(60*60*24*7) > 0)return delta/(60*60*24*7) +"周前";
//        if(delta/(60*60*24) > 0) return delta/(60*60*24) +"天前";
//        if(delta/(60*60) > 0)return delta/(60*60) +"小时前";
//        if(delta/(60) > 0) return delta/(60) +"分钟前";
//        return "刚刚";
//    }

     //判断是否为今天(效率比较高)
    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }


    //判断是否为昨天(效率比较高)
    public static boolean IsYesterday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }




    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

}
