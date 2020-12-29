package com.designpattern.tentangfilm.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.designpattern.tentangfilm.R;
import com.designpattern.tentangfilm.ui.FragmentCallback;
import com.designpattern.tentangfilm.viewmodel.ViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements FragmentCallback {

    @BindView(R.id.rv_new_film)
    RecyclerView rvNewMovie;
    @BindView(R.id.rv_popular_film)
    RecyclerView rvPopularMovie;
    @BindView(R.id.tv_see_more)
    TextView tvSeeMore;
    private PagedListPopularMovieAdapter popularMovieAdapter;
    private PagedListNewMovieAdapter newMovieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (getActivity() != null) {
            initAdapterNewMovie();
            initAdapterPopularMovie();
            tvSeeMore.setOnClickListener(view1 -> {
                startActivity(new Intent(requireActivity(),SeeMoreActivity.class));
            });
//            showLoading(true);
//            getMovies();
        }
    }

    private void initAdapterNewMovie() {
        rvNewMovie.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        newMovieAdapter = new PagedListNewMovieAdapter(this);
        newMovieAdapter.notifyDataSetChanged();
        getMovies();
    }

    private void initAdapterPopularMovie() {
        rvPopularMovie.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        popularMovieAdapter = new PagedListPopularMovieAdapter(this);
        popularMovieAdapter.notifyDataSetChanged();
        getMoviesPopular();
    }

//    private void showLoading(Boolean state) {
//        if (state) {
//            shimmerFrameLayout.setVisibility(View.VISIBLE);
//            shimmerFrameLayout.startShimmer();
//        } else {
//            shimmerFrameLayout.stopShimmer();
//            shimmerFrameLayout.setVisibility(View.GONE);
//        }
//    }


    private void getMovies() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        HomeViewModel viewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        viewModel.getMovies().observe(getViewLifecycleOwner(), listMovies -> {
            if (listMovies != null) {
                switch (listMovies.status) {
                    case LOADING:
//                        showLoading(true);
                        Log.d("Coba", "Loading");
                        break;

                    case SUCCESS:
//                        showLoading(false);
                        newMovieAdapter.submitList(listMovies.data);
                        assert listMovies.data != null;
                        Log.d("Coba", "Jalan " + listMovies.data.size());
                        newMovieAdapter.notifyDataSetChanged();
                        break;

                    case ERROR:
//                        showLoading(false);
                        Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        rvNewMovie.setAdapter(newMovieAdapter);

    }

    private void getMoviesPopular() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        HomeViewModel viewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        viewModel.getMoviesPopular().observe(getViewLifecycleOwner(), listMovies -> {
            if (listMovies != null) {
                switch (listMovies.status) {
                    case LOADING:
//                        showLoading(true);
                        Log.d("Coba", "Loading Popular");
                        break;

                    case SUCCESS:
//                        showLoading(false);
                        popularMovieAdapter.submitList(listMovies.data);
                        assert listMovies.data != null;
                        Log.d("Coba", "Jalan Popular" + listMovies.data.size());
                        popularMovieAdapter.notifyDataSetChanged();
                        break;

                    case ERROR:
//                        showLoading(false);
                        Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        });

        rvPopularMovie.setAdapter(popularMovieAdapter);

    }
}