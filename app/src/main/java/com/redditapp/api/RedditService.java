package com.redditapp.api;

import com.redditapp.models.AccessTokenResponse;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RedditService {

    String GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client";

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("access_token")
    Single<AccessTokenResponse> getNoUserAccessToken(@Field("grant_type") String grantType, @Field("device_id") String deviceId);

    @GET("hot")
    Single<String> getFrontPageListing(@Header("Authorization") String token);

    // use body example
//    Observable<AccessTokenResponse> getNoUserAccessToken(@Body AccessTokenRequest user);
}