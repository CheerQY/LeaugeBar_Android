package com.sicnu.cheer.generalmodule.util;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 网络请求工具类
 * Created by chheer on 2016/11/3.
 */

public class HttpAccessUtils {

    private static OkHttpClient httpClient;//网络请求主体


    /**
     * post请求
     *
     * @param url
     * @param param    参数
     * @param callBack 回调接口
     */
    public static void callHttpAccess(String url, Map<String, Object> param, HttpAccessCallBack callBack) {
        HttpAccess httpAccess = new HttpAccess(callBack);
        httpAccess.callHttpAccess(url, param);
    }


    /**
     * 回调接口
     */
    public interface HttpAccessCallBack {
        public void callback(String result);
    }

    /**
     * 网络请求类
     */
    private static class HttpAccess {
        private HttpAccessCallBack callBack;//回调接口

        /**
         * 构造网络请求
         *
         * @param callBack 回调接口
         */
        public HttpAccess(HttpAccessCallBack callBack) {
            this.callBack = callBack;
            if (httpClient == null) {
                httpClient = new OkHttpClient();
            }
        }


        /**
         * post请求
         *
         * @param url
         * @param param 请求参数
         */
        private void callHttpAccess(String url, Map<String, Object> param) {

            Request.Builder builder = new Request.Builder().url(url);


            if (param != null && param.size() > 0) {
                MultipartBuilder multipartBuilder = new MultipartBuilder();
                for (Map.Entry<String, Object> entry : param.entrySet()) {
                    String key = entry.getKey();
                    if (key == null) {
                        continue;
                    }
                    Object value = entry.getValue();
                    if (value == null) {
                        value = "";
                    }
                    if (value instanceof List){
                        List list = (List) value;
                        for (int i = 0;i < list.size();i ++){
                            Object item = list.get(i);
                            if (item instanceof File){
                                File file = (File) item;
                                multipartBuilder.addFormDataPart(key, file.getAbsolutePath(), RequestBody.create(null, file));
                            }else {
                                multipartBuilder.addFormDataPart(key, String.valueOf(value));
                            }
                        }
                    }else if (value instanceof File) {
                        File file = (File) value;
                        multipartBuilder.addFormDataPart(key, file.getAbsolutePath(), RequestBody.create(null, file));
                    } else {
                        multipartBuilder.addFormDataPart(key, String.valueOf(value));
                    }
                }
                RequestBody body = multipartBuilder.build();
                builder.post(body);
            }
            builder.tag("http");
            Request request = builder.build();

            new AsyncTask<Request, String, String>() {
                @Override
                protected String doInBackground(Request... params) {
                    if (params.length > 0) {
                        Request request = params[0];
                        httpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                try {
                                    JSONObject result = new JSONObject();
                                    result.put("ok", false);
                                    result.put("error", "网络请求失败");
                                    publishProgress(result.toString());
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                String cookie = response.header("Set-Cookie");
                                Log.e("tag", "cookie:" + cookie);
                                String result = response.body().string();
                                publishProgress(result);
                            }
                        });
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(String... values) {
                    if (values.length > 0) {
                        String value = values[0];
                        if (callBack != null) {
                            callBack.callback(value);
                        }
                    }
                }
            }.execute(request);
        }

    }
}
