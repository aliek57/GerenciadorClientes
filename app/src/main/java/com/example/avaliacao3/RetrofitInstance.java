package com.example.avaliacao3;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL = "https://viacep.com.br/ws/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Log.d("Retrofit", "Instanciando Retrofit pela primeira vez");
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            Log.d("Retrofit", "Instância do Retrofit já existente");
        }
        return retrofit;
    }
}
