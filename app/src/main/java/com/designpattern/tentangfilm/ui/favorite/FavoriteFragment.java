package com.designpattern.tentangfilm.ui.favorite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.designpattern.tentangfilm.R;
import com.designpattern.tentangfilm.ui.FragmentCallback;
import com.designpattern.tentangfilm.ui.home.PagedListNewMovieAdapter;
import com.designpattern.tentangfilm.viewmodel.ViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoriteFragment extends Fragment implements FragmentCallback {

    private PagedListNewMovieAdapter adapter;

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.tv_information)
    TextView tvInformation;
    @BindView(R.id.lav_favorite)
    LottieAnimationView lottieAnimationView;
    FavoriteViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (getActivity() != null) {
            initAdapter();
            getMovies();
        }
    }

    private void initAdapter() {
        rvMovies.setLayoutManager(new GridLayoutManager(this.requireActivity(),2,LinearLayoutManager.VERTICAL,false));
        adapter = new PagedListNewMovieAdapter(this);
        adapter.notifyDataSetChanged();
    }

    private void getMovies() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(FavoriteViewModel.class);
        viewModel.getMovieFavorite().observe(getViewLifecycleOwner(), movies -> {
            adapter.submitList(movies);
            if (!movies.isEmpty()) {
                tvInformation.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.GONE);
            } else {
                tvInformation.setVisibility(View.VISIBLE);
                lottieAnimationView.setVisibility(View.VISIBLE);
            }
        });

        rvMovies.setAdapter(adapter);


    }
}