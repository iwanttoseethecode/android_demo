package com.example.luoling.android_dome.okhttpdome;

/*
* 本实例调用第三方新闻接口，使用说明地址http://apistore.baidu.com/apiworks/servicedetail/2612.html
*
* 本教程借鉴 http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html
* */

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.luoling.android_dome.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class OkhttpStudyActivity extends Activity {

    @BindView(R.id.contentTxtView)
    TextView contentTxtView;
    @BindView(R.id.getBtn)
    Button getBtn;
    @BindView(R.id.postBtn)
    Button postBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_study);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.getBtn, R.id.postBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getBtn:
                new MethodAsyncTask().execute();
                break;
            case R.id.postBtn:
                break;
        }
    }

    class MethodAsyncTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            String string = null;
            try {
                string=Okhttp_get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return string;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            contentTxtView.setText(s);
        }
    }

    public String Okhttp_get() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url("http://apis.baidu.com/3023/news/channel?id=popular&page=1")
                .addHeader("apikey","7c9011a96332779b7d02db13705f273a").build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else{
            throw new IOException("Unexpected code " + response);

        }
    }

    //--------------------------------------一下内容为示例内容，由于没找到相应的网络加在，只能看看,网络加在都需要在独立的线程里面进行，省略新开线程代码--------------------------------------

    //POST提交Json数据
    public String postJson() throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON,"json");
        Request request = new Request.Builder().url("url").post(body).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else{
            throw new IOException("Unexpected code "+response);
        }
    }

    //POST提交键值对
    public String postKeyValue() throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody fromBody = new FormBody.Builder().add("platform", "android").add("name", "bug").add("subject", "XXXXXXXXXXXXXXX").build();

        Request request = new Request.Builder().url("url").post(fromBody).build();

        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().toString();
        }else{
            throw new IOException("Unexpected code "+response);
        }
    }

    /**
     * 下载一个文件，打印他的响应头，以string形式打印响应体。
     响应体的 string() 方法对于小文档来说十分方便、高效。但是如果响应体太大（超过1MB），应避免适应 string()方法 ，因为他会将把整个文档加载到内存中。
     对于超过1MB的响应body，应使用流的方式来处理body。
     */
    public void getlittlefile() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("url").build();
        Response response = client.newCall(request).execute();
        if(!response.isSuccessful()) throw new IOException("Unexpected code "+response);

        Headers responseHeaders = response.headers();
        for (int i=0;i<responseHeaders.size();i++){
            System.out.println(responseHeaders.name(i)+":"+responseHeaders.value(i));
        }

        System.out.println(response.body().string());
    }

    /*
    * 在一个工作线程中下载文件，当响应可读时回调Callback接口。读取响应时会阻塞当前线程。OkHttp现阶段不提供异步api来接收响应体。
    * */
    public void getlittlefile1(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("url").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0 ;i<responseHeaders.size();i++){
                    System.out.println(responseHeaders.name(i)+":"+responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            }
        });
    }

    //提取响应头
    public void gethttpheader() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("url").
                header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json").build();

        Response response = client.newCall(request).execute();
        if(!response.isSuccessful()) throw new IOException("Unexpected code "+response);

        System.out.println("Server: " + response.header("Server"));
        System.out.println("Date: " + response.header("Date"));
        System.out.println("Vary: " + response.headers("Vary"));
    }

    /*Post方式提交Stirng
    使用HTTP POST提交请求到服务。这个例子提交了一个markdown文档到web服务，以HTML方式渲染markdown。因为整个请求体都在内存中，因此避免使用此api提交大文档（大于1MB）。
    * */
    public void postString(){
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String postBody = ""
                + "Releases\n"
                + "--------\n"
                + "\n"
                + " * _1.0_ May 6, 2013\n"
                + " * _1.1_ June 15, 2013\n"
                + " * _1.2_ August 11, 2013\n";

        Request request = new Request.Builder().url("url").post(RequestBody.create(mediaType,postBody)).build();
    }

    /*
    * Post方式提交流
以流的方式POST提交请求体。请求体的内容由流写入产生。这个例子是流直接写入Okio的BufferedSink。你的程序可能会使用OutputStream，你可以使用BufferedSink.outputStream()来获取。
    * */
    public void postStream() throws IOException {
        final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("-------\n");
                for (int i = 2; i <= 997; i++) {
                    sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                }
            }

            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n) return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }
        };

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }

    //post方式提交文件
    public void postFile() throws IOException {
        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        File file = new File("url");
        Request request = new Request.Builder().url("url").post(RequestBody.create(MEDIA_TYPE_MARKDOWN,file)).build();
        Response response = client.newCall(request).execute();
        if(!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        System.out.println(response.body().string());
    }

    /*
    * 使用Gson来解析JSON响应
    Gson是一个在JSON和Java对象之间转换非常方便的api。这里我们用Gson来解析Github API的JSON响应。
    * */
    public void GsonParseResponse() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        Request request = new Request.Builder()
                .url("https://api.github.com/gists/c2a7c39532239ff261be")
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Gist gist = gson.fromJson(response.body().charStream(), Gist.class);
        for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().content);
        }
    }
    static class Gist {
        Map<String, GistFile> files;
    }

    static class GistFile {
        String content;
    }
}
