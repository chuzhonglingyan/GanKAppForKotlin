package com.yuntian.baselibs.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.yuntian.baselibs.R;

import java.io.File;
import java.util.concurrent.ExecutionException;


public class ImageLoaderUtil {

    private static Context mContext;

    private static final String TAG = ImageLoaderUtil.class.getSimpleName();

    private static int PLACEHOLDER_DEFAULT = R.drawable.default_image_loading;


    public static void initImageLoader(Context context) {
        mContext = context.getApplicationContext();
    }

    public static void initImageLoader(Context context,int defaultImage) {
        mContext = context.getApplicationContext();
        PLACEHOLDER_DEFAULT=defaultImage;
    }



    /**
     * 删除缓存
     */
    public static void clearImageView(ImageView imageView) {
        Glide.clear(imageView);
    }


    /**
     * 加载通用图片
     *
     * @param imageView
     */
    public static <T> void displayImage(T model, ImageView imageView) {
        displayImage(model, imageView, -1);
    }

    /**
     * 加载通用图片
     *
     * @param imageView
     */
    public static <T> void displayImage(T model, ImageView imageView, @DrawableRes int placeholder) {
        displayImage(model, imageView, placeholder, true);
    }

    /**
     * 加载通用图片
     *
     * @param imageView
     */
    public static <T> void displayImage(T model, ImageView imageView, boolean isCached) {
        displayImage(model, imageView, -1, isCached);
    }

    /**
     * 加载通用图片
     *
     * @param imageView
     */
    public static <T> void displayImage(T model, ImageView imageView, @DrawableRes int placeholder, boolean isCached) {
        if (imageView == null) {
            return;
        }
        if (isCached) {
            getDrawableRequestBuilder(model, imageView, getPlaceholder(placeholder)).into(imageView);
        } else {
            getDrawableRequestBuilder(model, imageView, getPlaceholder(placeholder))
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        }
    }


    /**
     * 无图下载监听
     *
     * @param model
     * @param baseTarget
     */
    public static <T> void displayWithListener(T model, SimpleTarget baseTarget) {
        displayWithListener(model, baseTarget, -1);
    }


    /**
     * 无图下载监听
     *
     * @param model
     * @param baseTarget
     */
    public static <T> void displayWithListener(T model, SimpleTarget baseTarget, @DrawableRes int placeholder) {
        Glide.with(mContext)
                .load(model)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder) //占位图
                .error(placeholder)       //失败图
                .fallback(placeholder)
                .into(baseTarget);
    }


    /**
     * 下载监听
     *
     * @param model
     * @param imageView
     * @param baseTarget
     */
    public static <T> void displayWithListener(T model, ImageView imageView, BaseTarget<Bitmap> baseTarget) {
        displayWithListener(model, imageView, baseTarget, -1);
    }

    /**
     * 下载监听
     *
     * @param model
     * @param imageView
     * @param baseTarget
     */
    public static <T> void displayWithListener(T model, ImageView imageView, BaseTarget<Bitmap> baseTarget, boolean isCached) {
        displayWithListener(model, imageView, baseTarget, -1, isCached);
    }

    /**
     * 下载监听
     *
     * @param model
     * @param imageView
     * @param baseTarget
     */
    public static <T> void displayWithListener(T model, ImageView imageView, BaseTarget<Bitmap> baseTarget, @DrawableRes int placeholder) {
        displayWithListener(model, imageView, baseTarget, placeholder, true);
    }


    /**
     * 下载监听
     *
     * @param model
     * @param imageView
     * @param baseTarget
     * @param placeholder
     * @param isCached
     * @param <T>
     */
    public static <T> void displayWithListener(T model, ImageView imageView, BaseTarget<Bitmap> baseTarget, @DrawableRes int placeholder, boolean isCached) {
        if (imageView == null) {
            return;
        }
        if (isCached) {
            getBitmapNoRequestBuilder(model, imageView, getPlaceholder(placeholder)).into(baseTarget);
        } else {
            getBitmapNoRequestBuilder(model, imageView, getPlaceholder(placeholder))
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        }
    }


    /**
     * 加载gif图片
     *
     * @param model
     * @param imageView
     * @param <T>
     */
    public static <T> void displayGifImage(T model, ImageView imageView) {
        displayGifImage(model, imageView, -1);
    }

    public static <T> void displayGifImage(T model, ImageView imageView, @DrawableRes int placeholder) {
        if (imageView == null) {
            return;
        }
        getGifRequestBuilder(model, imageView, getPlaceholder(placeholder)).into(imageView);
    }


    /**
     * 加载圆形图片
     *
     * @param model
     * @param imageView
     */
    public static <T> void displayCicleImage(T model, ImageView imageView) {
        displayCicleImage(model, imageView, -1);
    }

    public static <T> void displayCicleImage(T model, ImageView imageView, @DrawableRes int placeholder) {
        if (imageView == null) {
            return;
        }
        getDrawableRequestBuilder(model, imageView, getPlaceholder(placeholder))
                .transform(new GlideCircleTransfrom(getContext(imageView)))
                .into(imageView);
    }

    public static <T> void displayCicleImageNoCache(T model, ImageView imageView) {
        displayCicleImageNoCache(model, imageView, -1);
    }


    public static <T> void displayCicleImageNoCache(T model, ImageView imageView, @DrawableRes int placeholder) {
        if (imageView == null) {
            return;
        }
        getDrawableRequestBuilder(model, imageView, getPlaceholder(placeholder))
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new GlideCircleTransfrom(getContext(imageView)))
                .into(imageView);
    }


    public static <T> void displayCicleWithListener(T model, ImageView imageView, BaseTarget<Bitmap> simpleTarget, @DrawableRes int placeholder) {
        if (imageView == null) {
            return;
        }
        getBitmapNoRequestBuilder(model, imageView, getPlaceholder(placeholder)).transform(new GlideCircleTransfrom(getContext(imageView))).into(simpleTarget);
    }


    /**
     * 加载圆角图片
     *
     * @param model
     * @param imageView
     */
    public static <T> void displayRound(T model, ImageView imageView) {
        displayRound(model, imageView, 4);
    }

    /**
     * 加载圆角图片
     *
     * @param model
     * @param imageView
     * @param raduis
     */
    public static <T> void displayRound(T model, ImageView imageView, int raduis) {
        displayRound(model, imageView, raduis, -1);
    }

    public static <T> void displayRound(T model, ImageView imageView, int raduis, @DrawableRes int placeholder) {
        if (imageView == null) {
            return;
        }
        getDrawableRequestBuilder(model, imageView, getPlaceholder(placeholder))
                .transform(new GlideRoundTransform(getContext(imageView), raduis))
                .into(imageView);
    }


    /**
     * 加载小图
     *
     * @param imageView
     * @param model
     */
    public static <T> void displaySmallPhoto(ImageView imageView, T model) {
        if (imageView == null) {
            return;
        }
        getDrawableRequestBuilder(model, imageView, PLACEHOLDER_DEFAULT).thumbnail(0.5f).into(imageView);
    }


    public static <T> GifRequestBuilder<T> getGifRequestBuilder(T model, ImageView imageView, @DrawableRes int placeholder) {
        return Glide.with(getContext(imageView))
                .load(model)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder) //占位图
                .error(placeholder);     //失败图
        // .centerCrop();           //等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。
    }


    public static <T> BitmapRequestBuilder<T, Bitmap> getBitmapRequestBuilder(T model, ImageView imageView, @DrawableRes int placeholder) {
        return Glide.with(getContext(imageView))
                .load(model)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder) //占位图
                .error(placeholder)       //失败图
                .fallback(placeholder)
                .centerCrop();           //等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。
    }

    public static <T> BitmapRequestBuilder<T, Bitmap> getBitmapNoRequestBuilder(T model, ImageView imageView, @DrawableRes int placeholder) {
        return Glide.with(getContext(imageView))
                .load(model)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder) //占位图
                .error(placeholder)       //失败图
                .fallback(placeholder);
    }


    public static <T> DrawableRequestBuilder<T> getDrawableRequestBuilder(T model, ImageView imageView, @DrawableRes int placeholder) {
        return Glide.with(getContext(imageView))
                .load(model)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(getPlaceholder(placeholder)) //占位图
                .error(getPlaceholder(placeholder))       //失败图
                .fallback(getPlaceholder(placeholder))    //回调失败图
                .crossFade();            //淡入淡出动画，默认300ms
    }


    @SuppressLint("ResourceType")
    private static int getPlaceholder(@DrawableRes int placeholder) {
        if (placeholder <= 0) {
            placeholder = PLACEHOLDER_DEFAULT;
        }
        return placeholder;
    }


    public static Context getContext(ImageView imageView) {
        if (imageView.getContext() != null) {
            return imageView.getContext();
        }
        return mContext;
    }

    /**
     * 计算图片分辨率
     *
     * @param url
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String calePhotoSize( String url) throws ExecutionException, InterruptedException {
        File file = Glide.with(Utils.getApp()).load(url)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return options.outWidth + "*" + options.outHeight;
    }

}
