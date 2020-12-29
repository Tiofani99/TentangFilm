package com.designpattern.tentangfilm.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.designpattern.tentangfilm.data.MovieRepository;
import com.designpattern.tentangfilm.data.local.entity.Movie;

public class FavoriteViewModel extends ViewModel {
    private final MovieRepository movieRepository;

    public FavoriteViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<PagedList<Movie>> getMovieFavorite() {
        return movieRepository.getMovieFavorite();
    }
}
