package com.designpattern.tentangfilm.data.rest.api;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.designpattern.tentangfilm.BuildConfig;
import com.designpattern.tentangfilm.data.rest.response.CreditsResponse;
import com.designpattern.tentangfilm.data.rest.response.MovieResponse;
import com.designpattern.tentangfilm.utils.EspressoIdlingResource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {
    private static ApiCall INSTANCE;
    private static final ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
    private final Context context;

    private static final String TAG = ApiCall.class.getSimpleName();

    public ApiCall(Context context) {
        this.context = context;
    }

    public static ApiCall getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ApiCall(context);
        }

        return INSTANCE;
    }

    public LiveData<ApiResponse<List<MovieResponse>>> getMovies(){
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<MovieResponse>>> listMovies = new MutableLiveData<>();
        Call<MovieResponse> movieResponseCall = apiClient.getMovies(BuildConfig.API_KEY);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.body()!= null){
                    listMovies.setValue(ApiResponse.success(response.body().getList()));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG,"Empty Data");
            }
        });

        EspressoIdlingResource.decrement();
        return listMovies;
    }

    public LiveData<ApiResponse<MovieResponse>> getMovieDetail(String id){
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<MovieResponse>> movieDetail = new MutableLiveData<>();
        Call<MovieResponse> movieResponseCall = apiClient.getMovieById(id,BuildConfig.API_KEY);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.body()!= null){
                    movieDetail.setValue(ApiResponse.success(response.body()));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG,"Empty Data");
            }
        });

        EspressoIdlingResource.decrement();
        return movieDetail;
    }

    public LiveData<ApiResponse<List<MovieResponse>>> getMoviesPopular(){
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<MovieResponse>>> listMovies = new MutableLiveData<>();
        Call<MovieResponse> movieResponseCall = apiClient.getMoviesPopular(BuildConfig.API_KEY);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.body()!= null){
                    listMovies.setValue(ApiResponse.success(response.body().getList()));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG,"Empty Data");
            }
        });

        EspressoIdlingResource.decrement();
        return listMovies;
    }

    public LiveData<ApiResponse<List<CreditsResponse>>> getMovieCast(String id){
        EspressoIdlingResource.increment();;
        MutableLiveData<ApiResponse<List<CreditsResponse>>> listCredits = new MutableLiveData<>();
        Call<CreditsResponse> creditsResponseCall = apiClient.getMovieCasts(id,BuildConfig.API_KEY);
        creditsResponseCall.enqueue(new Callback<CreditsResponse>() {
            @Override
            public void onResponse(Call<CreditsResponse> call, Response<CreditsResponse> response) {
                if(response.body()!= null){
                    listCredits.setValue(ApiResponse.success(response.body().getList()));
                }
            }

            @Override
            public void onFailure(Call<CreditsResponse> call, Throwable t) {
                Log.d(TAG,"Empty Data");
            }
        });

        EspressoIdlingResource.decrement();
        return listCredits;
    }




}
