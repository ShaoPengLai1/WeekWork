package com.bawei.shaopenglai.application;

import android.app.Application;
import android.graphics.Bitmap;

import com.bawei.shaopenglai.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.commonsdk.UMConfigure;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this,"5a12384aa40fa3551f0001d1","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSizePercentage(10)
                .diskCacheSize(50*1024*1024)
                .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .cacheOnDisk(true)
                        .cacheInMemory(true)
                        .showImageForEmptyUri(R.mipmap.ic_launcher)
                        .showImageOnFail(R.mipmap.ic_launcher)
                        .showImageOnLoading(R.mipmap.ic_launcher)
                        .build())
                .build());
    }
}
