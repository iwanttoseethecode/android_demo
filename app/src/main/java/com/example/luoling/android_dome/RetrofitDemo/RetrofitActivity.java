package com.example.luoling.android_dome.RetrofitDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class RetrofitActivity extends AppCompatActivity {

    @BindView(R.id.edit1)
    EditText edit1;
    @BindView(R.id.btn1)
    Button btn1;

    public interface PhoneService{
        @GET("/apistore/mobilenumber/mobilenumber")
        Call<PhoneBean> getResult(@Header("apikey") String apikey,@Query("phone")String phone);
    }

    private static final String BASE_URL = "http://apis.baidu.com";
    private static final String API_KEY = "8e13586b86e4b7f3758ba3bd6c9c9135";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn1)
    public void onClick() {
        query();
    }

    /**

     */
    private void query(){
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .baseUrl(BASE_URL)//主机地址
                .build();
        //2.创建访问API的请求
        PhoneService service=retrofit.create(PhoneService.class);
        Call<PhoneBean> call =service.getResult(API_KEY,edit1.getText().toString());
        call.enqueue(new Callback<PhoneBean>() {
            @Override
            public void onResponse(Call<PhoneBean> call, Response<PhoneBean> response) {
                if(response.isSuccessful()){
                    PhoneBean result = response.body();
                    if (result!=null){
                        PhoneBean.RetDataBean bean = result.getRetData();
                        Toast.makeText(RetrofitActivity.this,bean.getCity(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PhoneBean> call, Throwable t) {

            }
        });
    }
}
