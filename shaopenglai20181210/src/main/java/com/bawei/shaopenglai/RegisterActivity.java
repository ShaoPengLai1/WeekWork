package com.bawei.shaopenglai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements IView {

    private EditText phone_edit,pass_edit;
    private Button icon,submit;
    private IPresenterImpl iPresenter;
    private String url="http://www.zhaoapi.cn/user/reg?mobile=%s&password=%s";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        iPresenter=new IPresenterImpl(this);
        initView();
    }

    private void initView() {
        phone_edit=findViewById(R.id.phone_edit);
        pass_edit=findViewById(R.id.pass_edit);
        submit=findViewById(R.id.subimit);
        icon=findViewById(R.id.icon);
        icon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    pass_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    icon.setBackgroundResource(R.drawable.ic_action_vie);
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    pass_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    icon.setBackgroundResource(R.drawable.ic_action_eye);
                }
                return false;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = phone_edit.getText().toString();
                String passs = pass_edit.getText().toString();
                if (NonNullUtils.getInstence().isNotNull(names,passs)){
                    Map<String,String> params = new HashMap<>();
                    params.put("mobile",names);
                    params.put("password",passs);
                    iPresenter.startRequestPost(Apis.URL_LOGIN_POST,params,SignBean.class);
                }else {
                    Toast.makeText(RegisterActivity.this,"账号密码为空",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void getRequest(Object o) {
        if (o instanceof SignBean){
            SignBean bean= (SignBean) o;
            if (bean==null||!bean.isSuccess()) {
                Toast.makeText(RegisterActivity.this, bean.getMsg(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}
