package com.designpattern.tentangfilm.data;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;


import com.designpattern.tentangfilm.data.local.entity.Movie;
import com.designpattern.tentangfilm.data.rest.api.ApiResponse;
import com.designpattern.tentangfilm.data.rest.response.CreditsResponse;
import com.designpattern.tentangfilm.vo.Resource;

import java.util.List;

public interface MovieDataSource {

    LiveData<Resource<PagedList<Movie>>> getMovies();

    LiveData<Resource<Movie>> getMovieDetail(String  id);

    LiveData<Resource<PagedList<Movie>>> getMoviesPopular();

    LiveData<PagedList<Movie>> getMovieFavorite();

    LiveData<ApiResponse<List<CreditsResponse>>> getMovieCredits(String  id);

    void setMovieFavorite(Movie movie, Boolean state);

}
