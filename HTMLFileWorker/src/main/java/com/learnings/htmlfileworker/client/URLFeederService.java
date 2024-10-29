package com.learnings.htmlfileworker.client;

import com.learnings.htmlfileworker.model.URL;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.Set;

public interface URLFeederService {

    @POST("/batch")
    public Call<Void> batchPublish(@Body Set<URL> urls);
}