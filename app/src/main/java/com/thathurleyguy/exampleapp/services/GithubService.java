package com.thathurleyguy.exampleapp.services;

import com.thathurleyguy.exampleapp.models.Repo;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface GithubService {
    @GET("/users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);
}
