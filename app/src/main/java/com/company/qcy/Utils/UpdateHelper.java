package com.company.qcy.Utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
public class UpdateHelper {
	/**
	 * 获取软件版本号
	 * @param context
	 * @return
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			LogUtil.e("NameNotFoundException",e.getMessage());
		}
		return verCode;
	}
	/**
	 * 获取版本名称
	 * @param context
	 * @return
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			LogUtil.e("NameNotFoundException",e.getMessage());
		}
		return verName;
	}

}
