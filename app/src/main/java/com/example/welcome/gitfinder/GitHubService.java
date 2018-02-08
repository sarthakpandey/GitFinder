package com.example.welcome.gitfinder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by WELCOME on 07-10-2017.
 */

public interface GitHubService {

    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("users/{username}/repos")
    Call<ArrayList<REPOSITORY>> getRepoository(@Path("username") String username);



}
