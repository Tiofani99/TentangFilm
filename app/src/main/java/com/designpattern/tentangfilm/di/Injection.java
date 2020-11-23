package com.designpattern.tentangfilm.di;

import android.content.Context;

import com.designpattern.tentangfilm.data.MovieRepository;
import com.designpattern.tentangfilm.data.local.LocalDataSource;
import com.designpattern.tentangfilm.data.local.room.MovieDatabase;
import com.designpattern.tentangfilm.data.rest.api.ApiCall;
import com.designpattern.tentangfilm.utils.AppExecutors;

public class Injection {
    public static MovieRepository provideRepository(Context context){
        ApiCall networkCall = ApiCall.getInstance(context);
        MovieDatabase database = MovieDatabase.getInstance(context);
        LocalDataSource localDataSource = LocalDataSource.getInstance(database.movieDao());
        AppExecutors appExecutors = new AppExecutors();

        return MovieRepository.getInstance(networkCall,localDataSource,appExecutors);
    }
}
