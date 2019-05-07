package com.example.raovat.api;

import com.example.raovat.Models.Address;
import com.example.raovat.Models.Categorychild1;
import com.example.raovat.Models.Categoryparen;
import com.example.raovat.Models.Categoryparen2;
import com.example.raovat.Models.InfoUser;
import com.example.raovat.Models.MultiSearch;
import com.example.raovat.Models.Post;
import com.example.raovat.Models.PostNew;

import com.example.raovat.Models.SearchKey;
import com.example.raovat.Models.User1;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Observable;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by admin on 8/24/18.
 */

public interface APIService {


    @POST("user/api/auth/login")
    Observable<InfoUser> login(@Body JsonObject object);

    @POST("user/api/create")
    Observable<User1> singup(@Body JsonObject object);

    @GET("categoryparent/api/query/{id}")
    Observable<Categoryparen> ListPost(@Path("id") String id);


    @GET("categorychild/api/{id}")
    Observable<Categorychild1> getIdCategoryParents(@Path("id") String id);

    @GET("categoryparent/api")
    Observable<Categoryparen2> getListNameCategoryParents();

    @GET("area/api")
    Observable<Address> getListAddress();


    @POST("post/api/create")
    Observable<PostNew> createPost(@Body JsonObject object);

    @GET("post/api/user/{id}")
    Observable<List<Post>> ListPostUser(@Path("id") String id);

    @Multipart
    @POST("image/upload/{id}")
    Observable<JsonObject> uploadImage(@Path("id") String id, @Part List<MultipartBody.Part> images);

    @DELETE("post/api/delete/{id}")
    Observable<JsonObject> delPostUser(@Path("id") String id);

    @PUT("post/api/update/{id}")
    Observable<Post> updatePost(@Path("id") String id, @Body JsonObject object);

    @GET("search/api/{postname}")
    Observable<SearchKey> listSearch(@Path("postname") String key);

    @GET("search/query?&&")
    Observable<MultiSearch> searchMulti(@Query("area") String idkv, @Query("categoryParent") String iddm, @Query("categoryChild") String iddmc);


}
