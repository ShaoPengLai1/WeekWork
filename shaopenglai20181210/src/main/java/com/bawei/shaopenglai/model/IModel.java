package com.bawei.shaopenglai.model;

import com.bawei.shaopenglai.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void requestData(String url,String prames,Class clazz,MyCallBack myCallBack);
    //kohttp post请求
    void requestDataPost(String url, Map<String,String> params, Class clazz, MyCallBack myCallBack);
}
