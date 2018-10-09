package com.company.qcy.Utils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtil {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    // private final static SimpleDateFormat dateFormater = new
    // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // private final static SimpleDateFormat dateFormater2 = new
    // SimpleDateFormat("yyyy-MM-dd");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0,7])|(18[0-9])|(14[0-9])|(16[0-9])|(19[0-9])|(12[0-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        System.out.println(m.matches() + "---");

        return m.matches();

    }

    /**
     * 将字符串转为日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * MD5加密字符串
     *
     * @param str
     * @return
     */
    public static String toMd5(String str) {
        byte[] hash = null;
        String hex = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            byte[] messageDigest = digest.digest();
            hex = toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
        }
        return hex;
    }

    public static String toMd5Low(String sSecret) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = sSecret.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    private static String toHexString(byte[] b) {
        //String to  byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }


    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 返回long类型的今天的日期
     *
     * @return
     */
    public static long getToday() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater2.get().format(cal.getTime());
        curDate = curDate.replace("-", "");
        return Long.parseLong(curDate);
    }

    /**
     * 判断字符串是否不是空
     *
     * @param input
     * @return
     */
    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equalsIgnoreCase(input) || TextUtils.isEmpty(input.trim()))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line);
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    static final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");

    /**
     * Unicode解码
     *
     * @param s
     * @return
     */
    public static String unicodeDecode(String s) {
        if (!s.contains("\\u")) {
            s = s.replace("u", "\\u");
        }
        Matcher m = reUnicode.matcher(s);
        StringBuffer sb = new StringBuffer(s.length());
        while (m.find()) {
            m.appendReplacement(sb, Character.toString((char) Integer.parseInt(m.group(1), 16)));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String formatNum(String num, int d) {
        int dotIndex = num.indexOf(".");
        if (num.length() - 1 < dotIndex + d + 1) {
            return num;
        }
        return num.substring(0, dotIndex + d + 1);
    }

    /**
     * 判断对象是否为空<br>
     * 1,字符串(null或者"")都返回true<br>
     * 2,数字类型(null或者0)都返回true<br>
     * 3,集合类型(null或者不包含元素都返回true)<br>
     * 4,数组类型不包含元素返回true(包含null元素返回false)<br>
     * 5,其他对象仅null返回true
     *
     * @param obj
     * @return
     */
    public static boolean arrayIsEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof Number) {
            Number num = (Number) obj;
            if (num.intValue() == 0) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof String) {
            String str = (String) obj;
            if ((str == null) || str.equals("")) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof Collection<?>) {
            Collection<?> c = (Collection<?>) obj;
            return c.isEmpty();
        } else if (obj instanceof Map<?, ?>) {
            Map<?, ?> m = (Map<?, ?>) obj;
            return m.isEmpty();
        } else if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            return length == 0 ? true : false;
        } else {
            return false;
        }
    }


    /**
     * 替换空格
     *
     * @param str
     * @return
     */
    public static String replaceStringSpace(String str) {
        String s = str.replace(" ", "");
        return s;
    }

    // 现在用的
    public static String HTMLPath(String deatil) {
        if (TextUtils.isEmpty(deatil)) {
            return "";
        } else {
            StringBuilder url = new StringBuilder();
//        String s = "<style type=\"text/css\">* {padding: 0px;margin: 0px;}img {width: 100%;}</style>";
            String newDetail = deatil.replace("<img", "<img style='display: block; max-width: 100%;'");
//        String loadUrl = newDetail.replace("<span","<span style='font-size:20px;'");
//            String body = "<html><head><meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=utf-8\\\"/>" +
//                    "<meta content=\\\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;\\\" name=\\\"viewport\\\" />" +
//                    "<meta name=\\\"apple-mobile-web-app-capable\\\" content=\\\"yes\\\">" +
//                    "<meta name=\\\"apple-mobile-web-app-status-bar-style\\\" content=\\\"black\\\">" +
//                    "<link rel=\\\"stylesheet\\\" type=\\\"text/css\\\" />" +
//                    "<style type=\\\"text/css\\\"> .color{color:#576b95;}</style>" +
//                    "</head><body style='margin:0;padding:0;'><div id=\\\"content\\\"><div style=\"text-align:center;\">"
//                    + newDetail + "</div></div></body></html>";

            String body = "<html><body>"
                    + newDetail + "</body></html>";
            url.append(body);
            LogUtil.e("WebImageUrl", url.toString());
            return url.toString();
        }
    }


    public static boolean isNumeric(String str) {

        Pattern pattern = Pattern.compile("[0-9]*");

        Matcher isNum = pattern.matcher(str);

        if (!isNum.matches()) {
            return false;
        }
        return true;

    }

    public static String isStringEmpty(String str) {

        return TextUtils.isEmpty(str) ? "" : str;

    }

    public static String isStringToZero(String str) {

        return TextUtils.isEmpty(str) ? "0.00" : str;

    }


}
