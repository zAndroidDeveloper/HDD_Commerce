package com.overtech.hdd.widget.cyclerviewpager;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.overtech.djtechlibrary.widget.bitmap.ImageLoader;
import com.overtech.hdd.R;

/**
 * ImageView 创建工厂
 *
 * @author Overtech Will
 */
public class ViewFactory {
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = new ImageView(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ScaleType.CENTER_CROP);
//		imageView.setAdjustViewBounds(true);
        ImageLoader.getInstance().displayImage(url, imageView,
                R.mipmap.icon_common_default_stub, R.mipmap.icon_common_default_error, Config.RGB_565);
        return imageView;
    }
}
