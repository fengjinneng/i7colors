package com.company.qcy.Utils;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;

public class UserUtil {


    public static boolean isLogin() {

        if(StringUtils.equals("false", SPUtils.getInstance().getString("isLogin"))
                || StringUtils.isEmpty(SPUtils.getInstance().getString("isLogin"))){
            return false;
        }
        return  true;
    }

}
