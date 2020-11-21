package com.designpattern.tentangfilm.data.local.room;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.designpattern.tentangfilm.data.local.entity.Movie;


import java.util.List;

public interface MovieDao {

    @Query("SELECT * from movie")
    DataSource.Factory<Integer, Movie> getAllMovie();

    @Query("SELECT * FROM movie WHERE bookmarked = 1 ")
    DataSource.Factory<Integer, Movie> getMovieFavorite();

    @Query("SELECT * from movie WHERE id = :id")
    LiveData<Movie> getMovieById(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovieFavorite(List<Movie> movies);

    @Query("UPDATE movie SET title = :title, poster_path = :img ,vote_count = :vote_count ," +
            "vote_average = :vote_avg, overview =:desc, release_date = :release_date, popularity = :popularity, backdrop_path = :backdrop_path " +
            "  WHERE id = :id")
    void updateDetailMovie(String id, String title, String img, String vote_count, Double vote_avg, String desc, String release_date, Double popularity,String backdrop_path);

    @Update
    void updateMovie(Movie movie);

}
