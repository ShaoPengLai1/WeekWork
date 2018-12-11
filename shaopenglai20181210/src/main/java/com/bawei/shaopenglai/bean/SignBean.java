package com.bawei.shaopenglai.bean;

public class SignBean {


    /**
     * msg : 登录成功
     * code : 0
     * data : {"age":null,"appkey":"5dd64458c70c72fc","appsecret":"E1B631D25AFF438312EA3FC6A2EAE642","createtime":"2018-12-10T08:54:52","email":null,"fans":null,"follow":null,"gender":null,"icon":null,"latitude":null,"longitude":null,"mobile":"13713657944","money":null,"nickname":null,"password":"1DBFECCBF6D0A6C873DA416BF8C42A01","praiseNum":null,"token":"739831DD20482AAC976AC5944E7D6A23","uid":23438,"userId":null,"username":"13713657944"}
     */

    private String msg;
    private String code;
    private final String SUCCESS_CODE="0";
    public boolean isSuccess(){
        return code.equals(SUCCESS_CODE);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
