package com.designpattern.tentangfilm.data.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.designpattern.tentangfilm.data.local.entity.Movie;
import com.designpattern.tentangfilm.data.local.room.MovieDao;

import java.util.List;

public class LocalDataSource {
    private static LocalDataSource INSTANCE;
    private final MovieDao movieDao;

    public LocalDataSource(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public static LocalDataSource getInstance(MovieDao mMovieDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(mMovieDao);
        }

        return INSTANCE;
    }

    public DataSource.Factory<Integer, Movie> getAllMovie(){
        return movieDao.getAllMovie();
    }

    public DataSource.Factory<Integer, Movie> getMovieFavorite(){
        return movieDao.getMovieFavorite();
    }

    public LiveData<Movie> getMovieDetail(final String id){
        return movieDao.getMovieById(id);
    }

    public void setMovieFavorite(Movie movieFavorite, Boolean newState) {
        movieFavorite.setBookmarked(newState);
        movieDao.updateMovie(movieFavorite);
    }

    public void updateMovieDetail(String id, String title, String img, String vote_count, Double vote_avg, String desc, String release_date, Double popularity, String backdrop_path){
        movieDao.updateDetailMovie(id,title,img,vote_count,vote_avg,desc,release_date,popularity,backdrop_path);
    }

    public void insertMovie(List<Movie> movies) {
        movieDao.insertMovieFavorite(movies);
    }
}
