package com.testvagrant.ekamTemplate.api.clients.services;

import com.google.gson.JsonObject;
import com.testvagrant.ekamTemplate.api.models.response.Posts;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PostsService {

    @GET("/posts")
    Call<List<Posts>> getPosts();


    @GET("/posts")
    Call<List<Posts>> getPost(@Query("id") String id);


    @POST("/posts")
    Call<Posts> createPost(@Body Posts post);

    @DELETE("/posts/{id}")
    Call<JsonObject> deletePost(@Path("id") String postId);

}
