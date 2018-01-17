package com.stateside.stateside.networking;

import com.stateside.stateside.information.GanadorResponse;
import com.stateside.stateside.information.NewUserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface StatesideService {

    @FormUrlEncoded
    @POST("user")
    Call<NewUserResponse> newUser (@Header("Authorization") String token,
                                   @Field("name") String name, @Field("last_name") String lastname,
                                   @Field("phone")String phone);

    @FormUrlEncoded
    @POST("searchwinner")
    Call<GanadorResponse> getGanador (@Header("Authorization") String token,
                                      @Field("phone") String phone);

}
