package com.designpattern.tentangfilm.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.designpattern.tentangfilm.data.MovieRepository;
import com.designpattern.tentangfilm.data.local.entity.Movie;
import com.designpattern.tentangfilm.vo.Resource;

public class HomeViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public HomeViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<Resource<PagedList<Movie>>> getMovies() {
        return movieRepository.getMovies();
    }

    public LiveData<Resource<PagedList<Movie>>> getMoviesPopular(){
        return movieRepository.getMoviesPopular();
    }
}
