package com.designpattern.tentangfilm.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.designpattern.tentangfilm.data.MovieRepository;
import com.designpattern.tentangfilm.data.local.entity.Movie;
import com.designpattern.tentangfilm.vo.Resource;

public class DetailViewModel extends ViewModel {
    private final MutableLiveData<String> id = new MutableLiveData<>();
    private MovieRepository movieRepository;
    public LiveData<Resource<Movie>> movieDetail = Transformations.switchMap(id,
            mId -> movieRepository.getMovieDetail(mId));

    public DetailViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id.setValue(id);
    }

    void setMovieFavorite() {
        if (movieDetail.getValue() != null) {
            Movie movie = movieDetail.getValue().data;
            assert movie != null;
            final boolean newState = !movie.isBookmarked();
            movieRepository.setMovieFavorite(movie, newState);
        }
    }
}
