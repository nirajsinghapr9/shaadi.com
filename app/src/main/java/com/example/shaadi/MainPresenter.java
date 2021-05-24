package com.example.shaadi;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.example.shaadi.model.ResponseData;
import com.example.shaadi.service.ApiClient;
import com.example.shaadi.service.DetailsService;

import java.io.Serializable;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainPresenter  {

    private ResponseData responseData;
    private Context context;
    DetailsService detailsService;
    MainActivity mainActivity;

    public MainPresenter(Context context) {
        this.context = context;
        detailsService = ApiClient.getClient(context).create(DetailsService.class);
        mainActivity=new MainActivity();

    }
    public void getData(){
        detailsService.getData("https://randomuser.me/api/?results=10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<ResponseData>>() {
                    @Override
                    public void onNext(Response<ResponseData> responseBody) {

                            if (responseBody.code()==200) {
                                Intent intent = new Intent("message_subject_intent");
                                intent.putExtra("name" , (Serializable) responseBody.body().getResults());
                                context.sendBroadcast(intent);
                            } else if (responseBody.code() == 401) {

                            }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
