package com.designpattern.tentangfilm.data;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;


import com.designpattern.tentangfilm.data.local.LocalDataSource;
import com.designpattern.tentangfilm.data.local.entity.Movie;
import com.designpattern.tentangfilm.data.rest.NetworkBoundResource;
import com.designpattern.tentangfilm.data.rest.api.ApiCall;
import com.designpattern.tentangfilm.data.rest.api.ApiResponse;
import com.designpattern.tentangfilm.data.rest.response.CreditsResponse;
import com.designpattern.tentangfilm.data.rest.response.MovieResponse;
import com.designpattern.tentangfilm.utils.AppExecutors;
import com.designpattern.tentangfilm.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository implements MovieDataSource{

    private volatile static MovieRepository INSTANCE = null;
    private final LocalDataSource localDataSource;
    private final AppExecutors appExecutors;
    private final ApiCall apiCall;

    public MovieRepository(LocalDataSource localDataSource, AppExecutors appExecutors, ApiCall apiCall) {
        this.localDataSource = localDataSource;
        this.appExecutors = appExecutors;
        this.apiCall = apiCall;
    }

    public static MovieRepository getInstance(ApiCall apiCall, LocalDataSource localDataSource, AppExecutors appExecutors){
        if(INSTANCE == null){
            synchronized (MovieRepository.class){
                if(INSTANCE == null){
                    INSTANCE = new MovieRepository(localDataSource,appExecutors,apiCall);
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public LiveData<Resource<PagedList<Movie>>> getMovies() {
        return new NetworkBoundResource<PagedList<Movie>, List<MovieResponse>>(appExecutors) {
            @Override
            protected LiveData<PagedList<Movie>> loadFromDB() {
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build();
                return new LivePagedListBuilder<>(localDataSource.getAllMovie(),config).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<Movie> data) {
                return (data == null || (data.size() == 0));
            }

            @Override
            protected LiveData<ApiResponse<List<MovieResponse>>> createCall() {
                return apiCall.getMovies();
            }

            @Override
            protected void saveCallResult(List<MovieResponse> data) {
                ArrayList<Movie> listMovies = new ArrayList<>();
                for (MovieResponse response : data) {
                    Movie movie = new Movie(response.getId(),
                            response.getTitle(),
                            response.getImg(),
                            response.getVote_count(),
                            response.getVote_avg(),
                            response.getDesc(),
                            response.getRelease_date(),
                            response.getPopularity(),
                            response.getBackdrop(),
                            false,
                            false
                    );

                    listMovies.add(movie);
                }

                localDataSource.insertMovie(listMovies);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<Movie>> getMovieDetail(String id) {
        return new NetworkBoundResource<Movie, MovieResponse>(appExecutors) {


            @Override
            protected LiveData<Movie> loadFromDB() {
                return localDataSource.getMovieDetail(id);
            }

            @Override
            protected Boolean shouldFetch(Movie data) {
                return data.getTitle() == null;
            }

            @Override
            protected LiveData<ApiResponse<MovieResponse>> createCall() {
                return apiCall.getMovieDetail(id);
            }

            @Override
            protected void saveCallResult(MovieResponse data) {
                localDataSource.updateMovieDetail(data.getId(),
                        data.getTitle(),
                        data.getImg(),
                        data.getVote_count(),
                        data.getVote_avg(),
                        data.getDesc(),
                        data.getRelease_date(),
                        data.getPopularity(),
                        data.getBackdrop());
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<Movie>>> getMoviesPopular() {
        return new NetworkBoundResource<PagedList<Movie>, List<MovieResponse>>(appExecutors) {
            @Override
            protected LiveData<PagedList<Movie>> loadFromDB() {
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build();
                return new LivePagedListBuilder<>(localDataSource.getMoviePopular(),config).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<Movie> data) {
                return (data == null || (data.size() == 0));
            }

            @Override
            protected LiveData<ApiResponse<List<MovieResponse>>> createCall() {
                return apiCall.getMoviesPopular();
            }

            @Override
            protected void saveCallResult(List<MovieResponse> data) {
                ArrayList<Movie> listMovies = new ArrayList<>();
                for (MovieResponse response : data) {
                    Movie movie = new Movie(response.getId(),
                            response.getTitle(),
                            response.getImg(),
                            response.getVote_count(),
                            response.getVote_avg(),
                            response.getDesc(),
                            response.getRelease_date(),
                            response.getPopularity(),
                            response.getBackdrop(),
                            false,
                            true
                    );

                    listMovies.add(movie);
                }

                localDataSource.insertMovie(listMovies);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<PagedList<Movie>> getMovieFavorite() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build();

        return new LivePagedListBuilder<>(localDataSource.getMovieFavorite(), config).build();
    }

    @Override
    public LiveData<ApiResponse<List<CreditsResponse>>> getMovieCredits(String id) {
        return apiCall.getMovieCast(id);
    }

    @Override
    public void setMovieFavorite(Movie movie, Boolean state) {
        appExecutors.diskIO().execute(() -> localDataSource.setMovieFavorite(movie, state));
    }
}
