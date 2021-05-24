package com.example.shaadi.service;


import com.example.shaadi.model.ResponseData;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DetailsService {

    String BASE_URL = "https://randomuser.me/api/?results=10";

    @GET
    Observable<Response<ResponseData>> getData(@Url String url);
}
