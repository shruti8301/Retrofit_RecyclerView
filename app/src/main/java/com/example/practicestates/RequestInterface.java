package com.example.practicestates;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("data.json")
    Call<SampleClass>GetStates();
}
