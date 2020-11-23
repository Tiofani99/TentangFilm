package com.designpattern.tentangfilm.data.rest.api;

import com.designpattern.tentangfilm.data.rest.response.CreditsResponse;
import com.designpattern.tentangfilm.data.rest.response.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<MovieResponse> getMovies(
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}")
    Call<MovieResponse> getMovieById(
            @Path("movie_id") String id,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/credits")
    Call<CreditsResponse> getMovieCasts(
            @Path("movie_id") String id,
            @Query("api_key") String apiKey
    );

    @GET("discover/movie/popular")
    Call<MovieResponse> getMoviesPopular(
            @Query("api_key") String apiKey
    );
}
