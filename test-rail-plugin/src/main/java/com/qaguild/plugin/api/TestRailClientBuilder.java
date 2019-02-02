package com.qaguild.plugin.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alpa on 2018-12-06
 */
public class TestRailClientBuilder {

    private String baseUrl;
    private String user;
    private String password;

    public TestRailClientBuilder(String baseUrl, String user, String password) {
        this.baseUrl = baseUrl;
        this.user = user;
        this.password = password;
    }

    public TestRailClient build() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new HttpLoggingInterceptor());
        httpClient.addInterceptor(new BasicAuthInterceptor(user, password));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(new TestRailCallAdapter())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(TestRailClient.class);
    }
}
