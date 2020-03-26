package com.danmo.hotel.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.danmo.hotel.R;
import com.danmo.hotel.base.CommentItem;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkhttpActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://49.235.173.135";
    private static final String TAG = "OkhttpActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
    }

    public void getRequest(View view){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();
        //创建请求内容
        Request request = new Request.Builder()
                .get()
                .url(BASE_URL+"/get/text")
                .build();
        //用client去创建请求任务
        Call task = okHttpClient.newCall(request);
        //异步请求
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"onFailure->" + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                Log.d(TAG,"code->"+code);
                if(code== HttpURLConnection.HTTP_OK){
                    ResponseBody body = response.body();
                    Log.d(TAG,"body->"+body.string());
                }
            }
        });
    }


    public void postComment(String username , String password){
        OkHttpClient Client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();

        CommentItem commentItem = new CommentItem(username,password);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(commentItem);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonStr,mediaType);

        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL + "/post/comment")
                .build();
        Call task = Client.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"onFailure -- >" + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                Log.d(TAG,"code -- >" + code);
                if(code == HttpURLConnection.HTTP_OK){
                    ResponseBody body = response.body();
                    if(body != null){
                        Log.d(TAG,"result == >" + body.string());
                    }
                }
            }
        });
    }

    public void postFile(){
        OkHttpClient Client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();

        File file = new File("");
        MediaType fileType = MediaType.parse("image/png");
        RequestBody fileBody = RequestBody.create(file,fileType);

        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("file",file.getName(),fileBody)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/file/upload")
                .post(requestBody)
                .build();
        Call task = Client.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"onFailure -- >" + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                Log.d(TAG,"code -- >" + code);
                if(code == HttpURLConnection.HTTP_OK){
                    ResponseBody body = response.body();
                    if(body != null){
                        Log.d(TAG,"result == >" + body.string());
                    }
                }
            }
        });
    }
}
