package com.company.qcy.Utils;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.company.qcy.R;

import java.io.File;

public class GlideUtils {


    // public static final int soWhite = R.color.white;


    public static void loadImageDefault(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.place_500x500) //占位图
                .error(R.drawable.place_500x500)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }

    /*
     *加载图片(默认)
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_500x500) //占位图
                .error(R.drawable.place_500x500)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }



    public static void loadImageFitCenter(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.place_500x500) //占位图
                .error(R.drawable.place_500x500)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }

    public static void loadImageCenter(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.place_500x500) //占位图
                .error(R.drawable.place_500x500)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }


    public static void loadImageWithStartPage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.qidongye) //占位图
                .error(R.drawable.qidongye)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }

    /*
     *加载图片(默认)
     */
    public static void loadImageRct(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_750x320) //占位图
                .error(R.drawable.place_750x320)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }

    /**
     * 指定图片大小;使用override()方法指定了一个图片的尺寸。
     * Glide现在只会将图片加载成width*height像素的尺寸，而不会管你的ImageView的大小是多少了。
     * 如果你想加载一张图片的原始尺寸的话，可以使用Target.SIZE_ORIGINAL关键字----override(Target.SIZE_ORIGINAL)
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public static void loadImageSize(Context context, String url, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(placeholderSoWhite) //占位图
                .error(R.color.baise)       //错误图
                .override(width, height)
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }


    /**
     * 禁用内存缓存功能
     * diskCacheStrategy()方法基本上就是Glide硬盘缓存功能的一切，它可以接收五种参数：
     * <p>
     * DiskCacheStrategy.NONE： 表示不缓存任何内容。
     * DiskCacheStrategy.DATA： 表示只缓存原始图片。
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
     */

    public static void loadImageSizekipMemoryCache(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
//                .placeholder(placeholderSoWhite) //占位图
                .error(R.color.baise)       //错误图S
                .skipMemoryCache(true)//禁用掉Glide的内存缓存功能
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }


    /**
     * 加载圆形图片
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .circleCrop()//设置圆形
                .placeholder(R.mipmap.morentouxiang)
                .error(R.mipmap.morentouxiang)

                //.priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).thumbnail(0.1f).apply(options).into(imageView);

    }

    /**
     * 预先加载图片
     * 在使用图片之前，预先把图片加载到缓存，调用了预加载之后，我们以后想再去加载这张图片就会非常快了，
     * 因为Glide会直接从缓存当中去读取图片并显示出来
     */
    public static void preloadImage(Context context, String url) {
        Glide.with(context)
                .load(url)
                .preload();
    }

    /**
     * 加载圆角图片
     */
//    public static void loadRoundCircleImage(Context context, String url, ImageView imageView) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .circleCrop()//设置圆形
//                .placeholder(placeholderSoWhite)
//                .error(errorSoWhite)
//                //.priority(Priority.HIGH)
//                .bitmapTransform(new RoundedCornersTransformation(45, 0, RoundedCornersTransformation.CornerType.ALL))
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//        Glide.with(context).load(url).apply(options).into(imageView);
//
//    }


    /**
     * 加载圆角图片-指定任意部分圆角（图片上、下、左、右四个角度任意定义）
     *
     * @param context
     * @param url
     * @param imageView
     * @param type
     */
//    public static void loadCustRoundCircleImage(Context context, String url, ImageView imageView, RoundedCornersTransformation.CornerType type) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(placeholderSoWhite)
//                .error(errorSoWhite)
//                //.priority(Priority.HIGH)
//                .bitmapTransform(new RoundedCornersTransformation(45, 0, type))
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//
//        Glide.with(context).load(url).apply(options).into(imageView);
//    }


    /**
     * 加载模糊图片（自定义透明度）
     *
     * @param context
     * @param url
     * @param imageView
     * @param blur      模糊度，一般1-100够了，越大越模糊
     */
//    public static void loadBlurImage(Context context, String url, ImageView imageView, int blur) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(placeholderSoWhite)
//                .error(errorSoWhite)
//                //.priority(Priority.HIGH)
//                .bitmapTransform(new BlurTransformation(blur))
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//        Glide.with(context).load(url).apply(options).into(imageView);
//    }

    /*
     *加载灰度(黑白)图片（自定义透明度）
     */
//    public static void loadBlackImage(Context context, String url, ImageView imageView) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(placeholderSoWhite)
//                .error(errorSoWhite)
//                //.priority(Priority.HIGH)
//                .bitmapTransform(new GrayscaleTransformation())
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//        Glide.with(context).load(url).apply(options).into(imageView);
//    }

    /**
     * Glide.with(this).asGif()    //强制指定加载动态图片
     * 如果加载的图片不是gif，则asGif()会报错， 当然，asGif()不写也是可以正常加载的。
     * 加入了一个asBitmap()方法，这个方法的意思就是说这里只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。
     * 如果你传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
     *
     * @param context
     * @param url       例如：https://image.niwoxuexi.com/blog/content/5c0d4b1972-loading.gif
     * @param imageView
     */
    private void loadGif(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions();
//                .placeholder(placeholderSoWhite)
//                .error(errorSoWhite);
        Glide.with(context)
                .load(url)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);

    }

    public static void loadYuanjiaoPicture(Context context,String url,ImageView imageView){
        CornerTransform transformation = new CornerTransform(context, dip2px(context, 10));
        //只是绘制左上角和右上角圆角
        transformation.setExceptCorner(false, false, false, false);
        RequestOptions options = new RequestOptions();
        options.transform(transformation);

        Glide.with(context).load(url).apply(options).into(imageView);



        //设置图片圆角角度  没试过，应该也可以

//        RoundedCorners roundedCorners= new RoundedCorners(6);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
//        Glide.with(context).load(url).apply(options).into(imageView);

    }


    public static int dip2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void downloadImage(final Context context, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //String url = "http://www.guolin.tech/book.png";
                    FutureTarget<File> target = Glide.with(context)
                            .asFile()
                            .load(url)
                            .submit();
                    final File imageFile = target.get();
                    LogUtils.d("logcat", "下载好的图片文件路径=" + imageFile.getPath());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
//                        }
//                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
