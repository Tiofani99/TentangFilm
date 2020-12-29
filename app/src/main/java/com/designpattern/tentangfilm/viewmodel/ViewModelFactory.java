package com.designpattern.tentangfilm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.designpattern.tentangfilm.data.MovieRepository;
import com.designpattern.tentangfilm.di.Injection;
import com.designpattern.tentangfilm.ui.detail.DetailViewModel;
import com.designpattern.tentangfilm.ui.favorite.FavoriteViewModel;
import com.designpattern.tentangfilm.ui.home.HomeViewModel;

public class ViewModelFactory  extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final MovieRepository movieRepository;

    public ViewModelFactory(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public static ViewModelFactory getInstance(Application context){
        if(INSTANCE == null){
            synchronized (ViewModelFactory.class){
                if(INSTANCE == null){
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(context));
                }
            }
        }

        return INSTANCE;
    }

    @NonNull
    @Override
    public<T extends ViewModel> T create(Class<T>modelClass){
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(movieRepository);
        }else if(modelClass.isAssignableFrom(DetailViewModel.class)){
            return (T) new DetailViewModel(movieRepository);
        }else if(modelClass.isAssignableFrom(FavoriteViewModel.class)){
            return (T) new FavoriteViewModel(movieRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
