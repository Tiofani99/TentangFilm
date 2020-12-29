package com.designpattern.tentangfilm.ui.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.designpattern.tentangfilm.BuildConfig;
import com.designpattern.tentangfilm.R;
import com.designpattern.tentangfilm.data.local.entity.Movie;
import com.designpattern.tentangfilm.data.rest.api.ApiClient;
import com.designpattern.tentangfilm.data.rest.api.ApiInterface;
import com.designpattern.tentangfilm.data.rest.response.CreditsResponse;
import com.designpattern.tentangfilm.viewmodel.ViewModelFactory;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    @BindView(R.id.tv_title_detail)
    TextView tvTitle;
    @BindView(R.id.tv_desc_detail)
    TextView tvDesc;
    @BindView(R.id.tv_release_detail)
    TextView tvRelease;
    @BindView(R.id.tv_popularity_detail)
    TextView tvPopularity;
    @BindView(R.id.tv_title)
    TextView tvTitleCollapse;
    @BindView(R.id.rb_rating_detail)
    RatingBar rbRating;
    @BindView(R.id.img_photo_detail)
    ImageView imgMovie;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.shimmerFrameLayout)
    ShimmerFrameLayout shimmerFrameLayout;
    @BindView(R.id.constraint_detail)
    ConstraintLayout constraintLayout;
    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.cv_poster)
    CardView cvPoster;

    DetailViewModel detailViewModel;
    ArrayList<CreditsResponse> listCasts;
    @BindView(R.id.rv_casts)
    RecyclerView rvCasts;
    private Menu menu;
    private MovieCastsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        detailViewModel = new ViewModelProvider(this, factory).get(DetailViewModel.class);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            String id = getIntent().getStringExtra(EXTRA_MOVIE);
            showLoading(true);
            detailViewModel.setId(id);
            favorite();
            setFavorite();
            detailViewModel.movieDetail.observe(this, movieResource -> {
                if (movieResource != null) {
                    switch (movieResource.status) {
                        case LOADING:
                            showLoading(true);
                            break;

                        case SUCCESS:
                            if (movieResource.data != null) {
                                showLoading(false);
                                setDataMovie(movieResource.data);
                            }
                            break;

                        case ERROR:
                            showLoading(false);
                            Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }
    }

    private void setCollapsing(final String title) {
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");
        tvTitleCollapse.setText(" ");
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {

            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float percentage = (appBarLayout.getTotalScrollRange() - (float) Math.abs(i)) / appBarLayout.getTotalScrollRange();
                if (percentage < 0.16) {
                    ivPoster.setVisibility(View.GONE);
                    cvPoster.setVisibility(View.GONE);
                } else if (percentage > 0.2) {
                    ivPoster.setVisibility(View.VISIBLE);
                    cvPoster.setVisibility(View.VISIBLE);
                }

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + i == 0) {
                    collapsingToolbarLayout.setTitle(title);
                    tvTitleCollapse.setText(title);

                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    tvTitleCollapse.setText(" ");
                    isShow = false;
                }
            }
        });
    }

    private String changeFormatDate(String date) {
        String[] splitDate = date.split("-");
        String part1 = splitDate[0];
        int part2 = Integer.parseInt(splitDate[1]);
        String part3 = splitDate[2];
        String[] month = getResources().getStringArray(R.array.month);

        String monthConvert = "month";

        for (int i = 1; i <= 12; i++) {
            if (i == part2) {
                monthConvert = month[i - 1];
            }
        }

        return part3 + " " + monthConvert + " " + part1;
    }

    private void setDataMovie(Movie movies) {
        setCollapsing(movies.getTitle());
        String popularity = Double.toString(movies.getPopularity());
        Double rating = movies.getVote_avg() / 2;
        String rate = String.valueOf(rating);
        String date = movies.getRelease_date();
        if (!date.equals("")) {
            date = changeFormatDate(movies.getRelease_date());
        }
        tvRelease.setText(date);

        String img = "https://image.tmdb.org/t/p/w500/" + movies.getImg();
        String poster = "https://image.tmdb.org/t/p/original/" + movies.getBackdrop_path();

        tvTitle.setText(movies.getTitle());
        rbRating.setRating(Float.parseFloat(rate));

        Glide.with(this)
                .load(poster)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error).centerCrop())
                .into(imgMovie);


        Glide.with(this)
                .load(img)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error).centerCrop())
                .into(ivPoster);


        if (movies.getDesc().equals("")) {
            String desc = getResources().getString(R.string.no_desc);
            tvDesc.setText(desc);
        } else {
            tvDesc.setText(movies.getDesc());
        }

        tvPopularity.setText(popularity);
        showCredits(movies);

    }

    private void showLoading(Boolean state) {
        if (state) {
            constraintLayout.setVisibility(View.GONE);
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmer();
        } else {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.VISIBLE);
        }
    }

    private void favorite() {
        detailViewModel.movieDetail.observe(this, movieResource -> {
            if (movieResource != null) {
                switch (movieResource.status) {
                    case LOADING:
                        showLoading(true);
                        break;

                    case SUCCESS:
                        if (movieResource.data != null) {
                            showLoading(false);
                            boolean status = movieResource.data.isBookmarked();
                            setButtonFavorite(status);
                        }
                        break;

                    case ERROR:
                        showLoading(false);
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void setButtonFavorite(boolean status) {
        if (menu == null) return;
        MenuItem menuItem = menu.findItem(R.id.action_favorite);
        if (status) {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white));
        } else {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_n_favorite_white));
        }
    }

    private void setFavorite() {
        detailViewModel.setMovieFavorite();
    }

    private void showCredits(Movie movie) {
        listCasts = new ArrayList<>();
        Log.d("Coba", "List " + movie.getId());

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CreditsResponse> creditsResponseCall = apiService.getMovieCasts(movie.getId(), BuildConfig.API_KEY);
        creditsResponseCall.enqueue(new Callback<CreditsResponse>() {
            @Override
            public void onResponse(Call<CreditsResponse> call, Response<CreditsResponse> response) {
                if (response.body() != null) {
                    listCasts = response.body().getList();
                }

                Log.d("Coba", "List " + listCasts.size());

                adapter = new MovieCastsAdapter(listCasts);
                rvCasts.setLayoutManager(new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                rvCasts.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CreditsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_favorite) {
            setFavorite();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        this.menu = menu;
        favorite();
        return true;
    }
}