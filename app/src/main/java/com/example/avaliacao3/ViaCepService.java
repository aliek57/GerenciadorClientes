package com.example.avaliacao3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {
    @GET("{cep}/json")
    Call<CepResponse> getEnderecoPorCep(@Path("cep") String cep);
}
