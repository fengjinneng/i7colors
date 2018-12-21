package com.company.qcy.Utils;

public class ClickUtil {



        // 两次点击按钮之间的点击间隔不能少于1000毫秒

        private static final int MIN_CLICK_DELAY_TIME = 1000;

        private static long lastTime;



         private static long delay;


        public static boolean isFastClick() {

            boolean flag = false;
            long time = System.currentTimeMillis() - lastTime;
            if (time < delay) {
                flag = true;
            }
            lastTime  = System.currentTimeMillis();
            return flag;

        }


}
