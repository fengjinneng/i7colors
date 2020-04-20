package com.company.qcy;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.githang.statusbar.StatusBarCompat;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {


    private ConvenientBanner activityShopBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guide);

        if (Build.VERSION.SDK_INT > 19) {
            StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.baise), true);
        }

        activityShopBanner = (ConvenientBanner) findViewById(R.id.activity_guide_banner);

        initView();

    }


    private void initView() {
        ArrayList<Integer> localImages = new ArrayList<>();

        localImages.add(getResId("daohang1", R.drawable.class));
        localImages.add(getResId("daohang2", R.drawable.class));
        localImages.add(getResId("daohang3", R.drawable.class));
        localImages.add(getResId("daohang4", R.drawable.class));
        localImages.add(getResId("daohang5", R.drawable.class));

        activityShopBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages);


        activityShopBanner.setCanLoop(false);

//        activityShopBanner.setPageIndicator(new int[]{R.mipmap.express_checked, R.mipmap.express_uncheck});
//        activityShopBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //设置如果只有一组数据时不能滑动
//        convenientBanner.setPointViewVisible(bannerData.size() == 1 ? false : true); // 指示器
//        activityShopBanner.setManualPageable(localImages.size() == 1 ? false : true);//设置false,手动影响（设置了该项无法手动切换）
//        activityShopBanner.setCanLoop(localImages.size() == 1 ? false : true); // 是否循环

        activityShopBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == localImages.size() - 1) {
                    ActivityUtils.startActivity(MainActivity.class);
                    finish();
                }
            }
        });
    }


    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }


}