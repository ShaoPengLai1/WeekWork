package com.bawei.shaopenglai.utils;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetUtils {
    private static NetUtils instance;
    private final Gson gson;

    public NetUtils(){
        gson = new Gson();
    }

    public static NetUtils getInstance(){
        if (instance==null){
            instance=new NetUtils();
        }
        return instance;
    }

    public void getResult(String urlStr,final Class clazz,final CallBack callBack){
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... strings) {
                return getResult(strings[0],clazz);
            }

            @Override
            protected void onPostExecute(Object o) {
                callBack.onSuccess(o);
            }
        }.execute(urlStr);
    }
    public interface CallBack<T>{
        void onSuccess(T t);
    }
    public <T> T getResult(String urlStr,Class clazz){
        return (T)gson.fromJson(getResult(urlStr),clazz);
    }


    public String getResult(String urlStr){
        String result="";
        try {
            URL url=new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            int responseCode = urlConnection.getResponseCode();
            if (responseCode==200){
                result=stream2String(urlConnection.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String stream2String(InputStream inputStream) throws IOException {
        StringBuilder sb=new StringBuilder();
        BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
        for (String tmp=br.readLine();tmp!=null;tmp=br.readLine()){
            sb.append(tmp);
        }
        return sb.toString();
    }
}
