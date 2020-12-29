package com.designpattern.tentangfilm.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.designpattern.tentangfilm.R;
import com.designpattern.tentangfilm.ui.FragmentCallback;
import com.designpattern.tentangfilm.viewmodel.ViewModelFactory;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeeMoreActivity extends AppCompatActivity implements FragmentCallback {

    HomeViewModel viewModel;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    private PagedListNewMovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);
        ButterKnife.bind(this);
        initAdapter();
        getMoviesPopular();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Film Populer");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    private void initAdapter() {
        rvMovies.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        adapter = new PagedListNewMovieAdapter(this);
        adapter.notifyDataSetChanged();
    }

    private void getMoviesPopular() {
        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        viewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        viewModel.getMoviesPopular().observe(this, listMovies -> {
            if (listMovies != null) {
                switch (listMovies.status) {
                    case LOADING:
//                        showLoading(true);
                        Log.d("Coba", "Loading Popular");
                        break;

                    case SUCCESS:
//                        showLoading(false);
                        adapter.submitList(listMovies.data);
                        assert listMovies.data != null;
                        Log.d("Coba", "Jalan Popular" + listMovies.data.size());
                        adapter.notifyDataSetChanged();
                        break;

                    case ERROR:
//                        showLoading(false);
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        });

        rvMovies.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}