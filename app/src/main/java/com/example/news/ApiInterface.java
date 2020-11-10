package com.example.news;

import com.example.news.Model.MetaData;

import java.net.URL;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("convert")
    Call<MetaData> getMetaData(
            @Query("url") String url
    );
}
