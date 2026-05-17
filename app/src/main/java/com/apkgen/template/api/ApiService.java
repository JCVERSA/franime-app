package com.apkgen.template.api;

import com.apkgen.template.model.Anime;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("animes")
    Call<List<Anime>> getAllAnimes();

    @GET("animes")
    Call<List<Anime>> searchAnimes(@Query("search") String query);

    @GET("animes/{id}")
    Call<Anime> getAnimeById(@retrofit2.http.Path("id") int id);
}