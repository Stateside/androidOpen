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
    @POST("register")
    Call<NewUserResponse> newUser (@Header("Authorization") String token,
                                   @Field("fullName") String fullName,
                                   @Field("jobTitle") String jobTitle,
                                   @Field("level") String level,
                                   @Field("email") String email,
                                   @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("api/v1/currentAgendaItem")
    Call<GanadorResponse> getCurrentAgenda (@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/v1/raffleWinners")
    Call<GanadorResponse> getWinner (@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/v1/checkIn")
    Call<GanadorResponse> checkIn (@Header("Authorization") String token,
                                   @Field("id") Long id);

}
