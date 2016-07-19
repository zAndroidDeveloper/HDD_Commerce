package com.overtech.hdd.activity;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.overtech.djtechlibrary.widget.bitmap.ImageLoader;
import com.overtech.hdd.debug.Logger;

/**
 * Created by Overtech on 16/7/13.
 */
public class HDDApplication extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.getInstance().initContext(getApplicationContext());
        Logger.e("application==执行了");
    }
}
