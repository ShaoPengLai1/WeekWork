package com.bawei.shaopenglai.presenter;

import java.util.Map;

public interface IPresenter {
    void startRequest(String url,String prames,Class clazz);
    void startRequestPost(String url, Map<String,String> params, Class clazz);
}
