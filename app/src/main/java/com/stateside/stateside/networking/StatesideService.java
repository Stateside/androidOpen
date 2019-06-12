package com.stateside.stateside.networking;

import com.stateside.stateside.information.GanadorResponse;
import com.stateside.stateside.information.NewUserResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface StatesideService {

    @FormUrlEncoded
    @POST("register")
    Call<NewUserResponse> newUser (@Field("fullName") String fullName,
                                   @Field("jobTitle") String jobTitle,
                                   @Field("level") String level,
                                   @Field("email") String email,
                                   @Field("phone") String phone
    );

    @FormUrlEncoded
    @GET("api/v1/currentAgendaItem")
    Call<GanadorResponse> getCurrentAgenda();

    @FormUrlEncoded
    @GET("api/v1/raffleWinners")
    Call<GanadorResponse> getWinner();

    @FormUrlEncoded
    @POST("api/v1/checkIn")
    Call<ResponseBody> checkIn (@Field("id") Long id);

}
