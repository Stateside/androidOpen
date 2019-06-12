package com.stateside.stateside.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JSONClient {

    private static StatesideService service;

    public static StatesideService getRetrofit() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-88-21-95.compute-1.amazonaws.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(StatesideService.class);
        }
        return service;
    }

}
