package com.bawei.shaopenglai.model;

import com.bawei.okhttputil.ICallBack;
import com.bawei.okhttputil.OkHttpUtil;
import com.bawei.shaopenglai.callback.MyCallBack;

import com.bawei.shaopenglai.utils.NetUtils;

import java.util.Map;

import okhttp3.OkHttpClient;

public class IModelImpl implements IModel {

    private MyCallBack myCallBack;
    @Override
    public void requestData(String url, String params, Class clazz, final MyCallBack myCallBack) {

    }

    @Override
    public void requestDataPost(String url, Map<String, String> params, Class clazz, final MyCallBack myCallBack) {
        OkHttpUtil.getInstance().postAsynchronization(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object o) {
                myCallBack.setData(o);
            }

            @Override
            public void failed(Exception e) {
                myCallBack.setData(e.getMessage());
            }
        });
    }
}
