package com.bawei.shaopenglai.utils;

import android.text.TextUtils;

public class NonNullUtils {
    private static NonNullUtils instence;
    public NonNullUtils(){

    }
    public static NonNullUtils getInstence(){
        if (instence==null){
            instence=new NonNullUtils();
        }
        return instence;
    }
    public boolean isNotNull(String name,String password){
        return !TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password);
    }
}
