package com.overtech.djtechlibrary.widget.bitmap;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.overtech.djtechlibrary.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {
    public static final int STUB_NULL = -1;// 不设置默认图
    private static final int STUB_ID = R.mipmap.icon_common_default_stub; // 默认图片（再不设置默认图的情况下用这个）
    private static final Config DEFAULT_CONFIG = Config.RGB_565;
    private static ImageLoader imageLoader = null;
    private Context context;

    private ImageLoader() {

    }

    public void initContext(Context context) {
        this.context = context;
    }

    public static synchronized ImageLoader getInstance() {

        if (imageLoader == null) {
            imageLoader = new ImageLoader();
        }
        return imageLoader;
    }

    public void displayAssetImage(String url, ImageView imageView) {
        AssetManager assets = context.getAssets();
        try {
            InputStream open = assets.open(url);
            Bitmap bitmap = BitmapFactory.decodeStream(open);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认图片加载
     *
     * @param imageUrl
     * @param imageView
     */
    public void displayImage(String imageUrl, ImageView imageView) {
        displayImage(imageUrl, imageView, STUB_ID, STUB_ID, DEFAULT_CONFIG);
    }

    /**
     * 带默认图片的图片加载（加载成功前和加载失败后的图片一样）
     *
     * @param imageUrl
     * @param imageView
     * @param stub_id
     */
    public void displayImage(String imageUrl, ImageView imageView, int stub_id) {
        if (stub_id == STUB_NULL) {
            Picasso.with(context).load(imageUrl).config(DEFAULT_CONFIG)
                    .into(imageView);
        } else {
            displayImage(imageUrl, imageView, stub_id, stub_id, DEFAULT_CONFIG);
        }
    }

    /**
     * 带有设定图片品质的接口
     *
     * @param imageUrl
     * @param imageView
     * @param config
     */
    public void displayImage(String imageUrl, ImageView imageView, Config config) {
        displayImage(imageUrl, imageView, STUB_ID, STUB_ID, config);
    }

    public void displayImage(String imageUrl, ImageView imageView, int stub_id,
                             Config config) {
        displayImage(imageUrl, imageView, stub_id, stub_id, config);
    }

    /**
     * 定制，没有动画的图片加载。（默认情况下在图片设置时会有动画）
     *
     * @param imageUrl
     * @param imageView
     * @param stub_id
     * @param isNoFade
     */
    public void displayImage(String imageUrl, ImageView imageView, int stub_id,
                             boolean isNoFade) {
        if (imageUrl == null || "".equals(imageUrl)) {
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setImageResource(stub_id);
            return;
        }
        if (stub_id == STUB_NULL) {
            Picasso.with(context).load(imageUrl).config(DEFAULT_CONFIG)
                    .noFade().into(imageView);
            return;
        }
        if (isNoFade) {
            Picasso.with(context).load(imageUrl).placeholder(stub_id)
                    .error(stub_id).config(DEFAULT_CONFIG).noFade()
                    .into(imageView);
            return;
        }
        Picasso.with(context).load(imageUrl).placeholder(stub_id)
                .error(stub_id).config(DEFAULT_CONFIG).into(imageView);
    }

    /**
     * 带默认图片的图片加载（加载成功前和加载失败后的图片显示）
     *
     * @param imageUrl
     * @param imageView
     * @param stub_id
     * @param stub_id_no_img
     */
    public void displayImage(String imageUrl, ImageView imageView, int stub_id,
                             int stub_id_no_img, Config config) {
        if (imageUrl == null || "".equals(imageUrl)) {
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setImageResource(stub_id);
            return;
        }
        Picasso.with(context).load(imageUrl).placeholder(stub_id)
                .error(stub_id_no_img).config(config).into(imageView);
    }

    /**
     * 带默认的图片加载，对加载成功后的图片的处理
     *
     * @param imageUrl
     * @param imageView
     * @param stub_id
     * @param stub_id_no_img
     * @param transformation
     * @param config
     */
    public void displayImage(String imageUrl, ImageView imageView, int stub_id,
                             int stub_id_no_img, Transformation transformation, Config config) {
        if (imageUrl == null || "".equals(imageUrl)) {
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setImageResource(stub_id);
            return;
        }
        Picasso.with(context).load(imageUrl).placeholder(stub_id)
                .error(stub_id_no_img).transform(transformation).config(config)
                .into(imageView);
    }

    /**
     * 带回调的图片加载（加载成功、失败、进度的回调）
     *
     * @param imageUrl
     * @param activity
     * @param imageView
     * @param pb_img_loading
     * @param handler
     */
    public void displayImage(String imageUrl, ImageView imageView,
                             Callback callback) {
        displayImage(imageUrl, imageView, STUB_ID, STUB_ID, callback,
                DEFAULT_CONFIG);
    }

    public void displayImage(String imageUrl, ImageView imageView,
                             Callback callback, int stub_id) {
        displayImage(imageUrl, imageView, stub_id, stub_id, callback,
                DEFAULT_CONFIG);
    }

    public void displayImage(String imageUrl, ImageView imageView,
                             Callback callback, Config config) {
        displayImage(imageUrl, imageView, STUB_ID, STUB_ID, callback, config);
    }

    public void displayImage(String imageUrl, ImageView imageView,
                             Callback callback, int stub_id, Config config) {
        displayImage(imageUrl, imageView, stub_id, stub_id, callback, config);
    }

    /**
     * 带默认图片和回调函数的加载
     *
     * @param imageUrl
     * @param imageView
     * @param stub_id
     * @param stub_id_no_img
     * @param callback
     */
    public void displayImage(String imageUrl, ImageView imageView, int stub_id,
                             int stub_id_no_img, Callback callback, Config config) {
        if (imageUrl == null || "".equals(imageUrl)) {
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setImageResource(stub_id);
            return;
        }
        Picasso.with(context).load(imageUrl).placeholder(stub_id)
                .error(stub_id_no_img).config(config).into(imageView, callback);

    }

    public void displayImageFileFitView(File file, ImageView target) {
        if (file == null) {
            return;
        }
        Picasso.with(context).load(file).fit().centerInside().into(target);
    }

    public void fetch(String imageUrl) {
        if (imageUrl == null || "".equals(imageUrl))
            return;
        Picasso.with(context).load(imageUrl).fit();
    }

    public void fetchToTarget(String imageUrl, Target target) {
        if (imageUrl == null || "".equals(imageUrl))
            return;
        Picasso.with(context).load(imageUrl).into(target);
    }

    public void fetchToTarget(String imageUrl, Target target, int res) {
        if (imageUrl == null || "".equals(imageUrl))
            return;
        Picasso.with(context).load(imageUrl).placeholder(res).into(target);
    }

    /**
     * 取消请求（用于释放资源）
     *
     * @param view
     */
    public void cancelRequest(ImageView view) {
        Picasso.with(context).cancelRequest(view);
    }

}
