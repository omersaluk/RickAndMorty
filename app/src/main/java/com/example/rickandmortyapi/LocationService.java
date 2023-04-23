package com.example.rickandmortyapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationService {

    @GET("location")
    Call<LocationResponse> getLocations(@Query("page") int page);


}
