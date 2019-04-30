package com.company.qcy.Utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.ui.activity.pengyouquan.ImagePagerActivity;

import java.io.File;

public class MyCommonUtil {


    //加载朋友圈头像
    public static void jiazaitouxiang(Context mContext, String imgUrl, ImageView target) {

        if (StringUtils.isEmpty(imgUrl)) {
            target.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.morentouxiang));
        } else {
            if (imgUrl.startsWith("http")) {
                GlideUtils.loadCircleImage(mContext, imgUrl, target);
            } else {
                GlideUtils.loadCircleImage(mContext, ServerInfo.IMAGE + imgUrl, target);
            }
        }
    }

    //判断是否是公司和打V认证
    public static void isDaVOrCompanyAndSetBossLevel(Context context, TextView secondNameTextView, String isCompany,
                                                     String companyName, String isDaV, String bigVName, ImageView bigV
                                                     ,String nickName, String bossLevel, TextView nicknameTextView) {
        if (StringUtils.equals("1", isCompany)) {
            secondNameTextView.setText(companyName);
            bigV.setVisibility(View.VISIBLE);
            secondNameTextView.setVisibility(View.VISIBLE);
//            name.setTextColor(mContext.getResources().getColor(R.color.chunhongse));
        } else {
            if (StringUtils.equals("1", isDaV)) {
                secondNameTextView.setVisibility(View.VISIBLE);
                secondNameTextView.setText(bigVName);
                bigV.setVisibility(View.VISIBLE);
//                name.setTextColor(mContext.getResources().getColor(R.color.chunhongse));
            } else {
                secondNameTextView.setVisibility(View.GONE);
                secondNameTextView.setText("");
                bigV.setVisibility(View.GONE);
//                name.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
            }
        }

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(nickName + " .");

        if (StringUtils.equals("1", bossLevel)) {
            Drawable level1 = context.getResources().getDrawable(R.mipmap.level_one);
            level1.setBounds(0, 0, level1.getIntrinsicWidth(), level1.getIntrinsicHeight());
            ImageSpan span1 = new ImageSpan(level1, ImageSpan.ALIGN_BASELINE);
            spannableStringBuilder.setSpan(span1,
                    spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            nicknameTextView.setText(spannableStringBuilder);
        }
        if (StringUtils.equals("2", bossLevel)) {
            Drawable level1 = context.getResources().getDrawable(R.mipmap.level_two);
            level1.setBounds(0, 0, level1.getIntrinsicWidth(), level1.getIntrinsicHeight());
            ImageSpan span1 = new ImageSpan(level1, ImageSpan.ALIGN_BASELINE);
            spannableStringBuilder.setSpan(span1,
                    spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            nicknameTextView.setText(spannableStringBuilder);
        }

        if (StringUtils.equals("3", bossLevel)) {
            Drawable level1 = context.getResources().getDrawable(R.mipmap.level_three);
            level1.setBounds(0, 0, level1.getIntrinsicWidth(), level1.getIntrinsicHeight());
            ImageSpan span1 = new ImageSpan(level1, ImageSpan.ALIGN_BASELINE);
            spannableStringBuilder.setSpan(span1,
                    spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            nicknameTextView.setText(spannableStringBuilder);
        }

    }



    //保存图片到本地
    public static void saveImageToSysAlbum(Activity activity, ImageView imageView) {
        if (SDCardUtils.isSDCardEnable()) {
            BitmapDrawable bmpDrawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bmp = bmpDrawable.getBitmap();
            if (bmp != null) {
                try {
                    ContentResolver cr = activity.getContentResolver();
                    String url = MediaStore.Images.Media.insertImage(cr, bmp,
                            String.valueOf(System.currentTimeMillis()), "");
                    ToastUtils.showShort("图片保存成功");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtils.showShort("图片保存失败");
            }
        } else {
            ToastUtils.showShort("图片保存失败");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(new File("com.company.qcy.image"));
            mediaScanIntent.setData(contentUri);
            activity.sendBroadcast(mediaScanIntent);
        } else {
            activity.sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://"
                            + Environment.getExternalStorageDirectory())));
        }
    }


}
