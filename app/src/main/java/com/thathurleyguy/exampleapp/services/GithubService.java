package com.thathurleyguy.exampleapp.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.thathurleyguy.exampleapp.models.Repo;

import java.util.Date;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public class GithubService {
    public interface GithubAPI {
        @GET("/users/{user}/repos")
        Observable<List<Repo>> listRepos(@Path("user") String user);
    }

    private static final String API_URL = "https://api.github.com";

    private static final Gson gsonHandler = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .create();

    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint(API_URL)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setConverter(new GsonConverter(gsonHandler))
            .build();

    private static final GithubAPI GIT_HUB_SERVICE = REST_ADAPTER.create(GithubAPI.class);

    public static GithubAPI getService() {
        return GIT_HUB_SERVICE;
    }
}
