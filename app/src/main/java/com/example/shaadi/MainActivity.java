package com.example.shaadi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.shaadi.db.AppDatabase;
import com.example.shaadi.model.ResponseData;
import com.example.shaadi.model.ResultData;
import com.example.shaadi.service.ApiClient;
import com.example.shaadi.service.DetailsService;

import java.io.Serializable;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MyAdapter.ClickListener{
    RecyclerView rv;
    MainPresenter mainPresenter;
    List<ResultData> resultData;
    MyAdapter myAdapter;
    DetailsService detailsService;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rView);
        detailsService = ApiClient.getClient(this).create(DetailsService.class);
        myAdapter= new MyAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        myAdapter.setOnClickListener(this);
        rv.setAdapter(myAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv);
        getData();
    }


    public void setData(List<ResultData> results) {
        this.resultData = results;
        myAdapter.setList(resultData);
    }

    @Override
    public void onClickViewOrder() {

    }
    public void getData(){
        detailsService.getData("https://randomuser.me/api/?results=10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<ResponseData>>() {
                    @Override
                    public void onNext(Response<ResponseData> responseBody) {

                        if (responseBody.code()==200) {
                           setData(responseBody.body().getResults());
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