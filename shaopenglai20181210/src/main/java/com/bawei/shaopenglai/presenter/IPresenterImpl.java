package com.bawei.shaopenglai.presenter;

import com.bawei.shaopenglai.bean.SignBean;
import com.bawei.shaopenglai.model.IModelImpl;
import com.bawei.shaopenglai.callback.MyCallBack;
import com.bawei.shaopenglai.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter{

    private IView mIview;
    private IModelImpl model;

    public IPresenterImpl(IView iview) {
        mIview = iview;
        model = new IModelImpl();
    }

    @Override
    public void startRequest(String url, String prames, Class clazz) {
       /* model.requestData(url, null, clazz, new MyCallBack() {
            @Override
            public void getData(Object data) {
                mIview.getRequest(data);
            }
        });*/
    }
    //okhttp post请求
    @Override
    public void startRequestPost(String url, Map<String, String> params, Class clazz) {
        model.requestDataPost(url, params, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
             mIview.getRequest(data);
            }
        });
    }
    public void onDetach(){
        if(model!=null){
            model = null;
        }
        if (mIview!=null){
            mIview = null;
        }
    }
}
