package com.example.practicestates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.covid19india.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface=retrofit.create(RequestInterface.class);
        Call<SampleClass> call=requestInterface.GetStates();
        call.enqueue(new Callback<SampleClass>() {
            @Override
            public void onResponse(Call<SampleClass> call, Response<SampleClass> response) {
               List<statewise> statewises=response.body().getStatewiseList();
                for(statewise statewise:statewises)
                {
                    String content="";
                    content+= "ACTIVE:"+statewise.getActive()+"\n";
                    content+= "CONFIRMED:"+statewise.getConfirmed()+"\n";
                    content+= "DEATHS:"+statewise.getDeaths()+"\n";
                    content+= "STATE:"+statewise.getState()+"\n";
                    content+= "RECOVERED:"+statewise.getRecovered()+"\n";

                }
                recyclerView.setAdapter(new DataAdapter(MainActivity.this,statewises));
            }
            @Override
            public void onFailure(Call<SampleClass> call, Throwable t) {
                Log.i("Failure",t.toString());
            }
        });
}}
