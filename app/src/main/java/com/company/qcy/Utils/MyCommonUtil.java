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
import android.support.constraint.ConstraintLayout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.ScreenUtils;
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


    public static void setImageSize(int dWidth, int dHeight, ImageView imageView) {

        imageView.setAdjustViewBounds(true);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        int width, height;//ImageView调整后的宽高

//        int sWidth  = ScreenUtils.getScreenWidth()/2 ;
//        int sHeight  = ScreenUtils.getScreenHeight()/2 ;
//
//        //屏幕宽高比,一定要先把其中一个转为float
//        float sScale = (float) sWidth /sHeight;
//        //图片宽高比
//        float dScale = (float) dWidth / dHeight;
//
//        float scale = 1.0f;
//        if (sScale > dScale) {
//            scale = (float) dHeight / sHeight;
//
//            height = sHeight;//图片高度就是屏幕高度
//            width = (int) (dWidth * scale);//按照缩放比算出图片缩放后的宽度
//        } else if (sScale < dScale) {
//            scale = (float) dWidth / sWidth;
//            width = sWidth;
//            height = (int) (dHeight / scale);//这里用除
//        } else {
//            width = sWidth;
//
//            height = sHeight;
//        }

        int maxW = ScreenUtils.getScreenWidth() / 2;

        int actualW = 0;
        int actualH = 0;
        float scale = ((float) dHeight) / ((float) dWidth);
        if (dWidth >= maxW) {
            actualW = maxW;
            actualH = (int) (actualW * scale);

        } else if (actualW < maxW/2) {
            actualW = maxW/2;
            actualH = (int) (actualW * scale);

        }else {
            actualW = dWidth;
            actualH = dHeight;

        }
        imageView.setLayoutParams(new ConstraintLayout.LayoutParams(actualW, actualH));
    }

    //判断是否是公司和打V认证
    public static void isDaVOrCompanyAndSetBossLevel(Context context, TextView secondNameTextView, String isCompany,
                                                     String companyName, String isDaV, String bigVName, ImageView bigV
            , String nickName, String bossLevel, TextView nicknameTextView) {
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
