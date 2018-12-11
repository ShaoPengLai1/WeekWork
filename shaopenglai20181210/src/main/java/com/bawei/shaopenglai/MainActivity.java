package com.bawei.shaopenglai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.SignBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.utils.NonNullUtils;
import com.bawei.shaopenglai.view.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView,View.OnClickListener {

    private EditText edit_phone,edit_pass;
    private Button icon,register,Asynchronous;
    private Button subimit;
    private IPresenterImpl iPresenter;
    private String urlStr="http://www.zhaoapi.cn/user/login?mobile=%s&password=%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iPresenter=new IPresenterImpl(this);
        initView();
    }

    private void initView() {
        edit_phone=findViewById(R.id.phone_edit);
        edit_pass=findViewById(R.id.pass_edit);
        icon=findViewById(R.id.icon);

        subimit=findViewById(R.id.subimit);
        register=findViewById(R.id.register);
        Asynchronous=findViewById(R.id.Asynchronous);

        icon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    edit_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    icon.setBackgroundResource(R.drawable.ic_action_vie);
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    edit_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    icon.setBackgroundResource(R.drawable.ic_action_eye);
                }
                return false;
            }
        });
        subimit.setOnClickListener(this);
        register.setOnClickListener(this);
        Asynchronous.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.subimit:
                String names = edit_phone.getText().toString();
                String passwords = edit_pass.getText().toString();

                if (NonNullUtils.getInstence().isNotNull(names,passwords)){
                    Map<String,String> params = new HashMap<>();
                    params.put("mobile",names);
                    params.put("password",passwords);
                    iPresenter.startRequestPost(Apis.URL_LOGIN_POST,params,SignBean.class);

                }else {
                    Toast.makeText(MainActivity.this,"手机号或密码为空",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.register:
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.Asynchronous:
                //获得UMShareAPI实例
                UMShareAPI umShareAPI=UMShareAPI.get(MainActivity.this);
                //开始登录
                //第一个参数：上下文
                //第二个参数，登录哪种平台
                //第三个参数，添加回调
                umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    /**
                     * 开始登录回调
                     * @param share_media
                     */
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.i("TAG","UMAuthListener onStart");
                    }

                    /**
                     * 登录完成
                     * @param share_media
                     * @param i
                     * @param map
                     */
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        Log.i("TAG", "UMAuthListener onComplete");

                        //获取名字
                        String name  = map.get("screen_name");
                        //获取头像
                        String img  = map.get("profile_image_url");

                        Log.i("TAG", "name is "+ name);
                        Log.i("TAG", "img is "+ img);
                    }

                    /**
                     * 登录失败
                     * @param share_media
                     * @param i
                     * @param throwable
                     */
                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Log.i("TAG", "UMAuthListener onError" + throwable.getLocalizedMessage());
                    }

                    /**
                     * 登录取消
                     * @param share_media
                     * @param i
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Log.i("TAG", "UMAuthListener onCancel");
                    }
                });
                break;
                default:
                    break;
        }
    }


    @Override
    public void getRequest(Object o) {
        SignBean signBean= (SignBean) o;
        Toast.makeText(MainActivity.this,signBean.getMsg(),Toast.LENGTH_LONG).show();
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.i("TAG", "UMShareListener onStart");
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.i("TAG", "UMShareListener onResult");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.i("TAG", "UMShareListener onError");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.i("TAG", "UMShareListener onCancel");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}
