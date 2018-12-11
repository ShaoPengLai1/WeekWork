package com.bawei.shaopenglai;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.NewBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.HashMap;
import java.util.Map;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class LoginActivity extends AppCompatActivity implements IView, QRCodeView.Delegate{

    private Banner banner;
    private IPresenterImpl iPresenter;
    private Button but;
    private ZXingView zXingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iPresenter=new IPresenterImpl(this);
        initView();
    }

    private void initView() {
        banner=findViewById(R.id.banner);
        but=findViewById(R.id.but);
        zXingView=findViewById(R.id.zxing);
        initData();
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zXingView.setVisibility(View.VISIBLE);
                zXingView.showScanRect();
                zXingView.startCamera();
                zXingView.startSpot();
                zXingView.startSpotAndShowRect();
                zXingView.openFlashlight();
            }
        });

    }

    private void initData() {

        Map<String,String> params = new HashMap<>();
        iPresenter.startRequestPost(Apis.URL_CONTENT_POST,params,NewBean.class);
    }

    @Override
    public void getRequest(Object o) {
        if (o instanceof NewBean){
            NewBean bean= (NewBean) o;
            banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
            banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    NewBean.DataBean.BannerBean bannerBean= (NewBean.DataBean.BannerBean) path;
                    com.nostra13.universalimageloader.core.ImageLoader.getInstance()
                            .displayImage(bannerBean.hasImage(),imageView);
                }

                @Override
                public ImageView createImageView(Context context) {
                    ImageView imageView=new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    return imageView;
                }
            });
            banner.setImages(bean.getData().getBanner());
            banner.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        zXingView.startCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zXingView.onDestroy();
    }
    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(LoginActivity.this,result,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }
}
