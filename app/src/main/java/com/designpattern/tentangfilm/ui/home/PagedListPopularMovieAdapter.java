package com.designpattern.tentangfilm.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.designpattern.tentangfilm.BuildConfig;
import com.designpattern.tentangfilm.R;
import com.designpattern.tentangfilm.data.local.entity.Movie;
import com.designpattern.tentangfilm.ui.FragmentCallback;
import com.designpattern.tentangfilm.ui.detail.DetailActivity;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagedListPopularMovieAdapter extends PagedListAdapter<Movie, PagedListPopularMovieAdapter.MovieViewHolder> {

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.equals(newItem);
                }
            };
    private final FragmentCallback callback;


    public PagedListPopularMovieAdapter(FragmentCallback callback) {
        super(DIFF_CALLBACK);
        this.callback = callback;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_film, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        assert movie != null;
        holder.bind(movie);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_popular_film)
        ImageView ivImg;
        @BindView(R.id.tv_popular_title)
        TextView tvTitle;
        @BindView(R.id.tv_popular_rating)
        TextView tvRating;
        @BindView(R.id.tv_popular_count_rating)
        TextView tvCount;
        @BindView(R.id.btn_detail)
        MaterialButton btnDetail;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btnDetail.setOnClickListener(this);

        }

        void bind(Movie movies) {
            tvTitle.setText(movies.getTitle());
            tvRating.setText(movies.getVote_avg().toString());
            tvCount.setText("("+movies.getVote_count()+")");
            String poster = "https://image.tmdb.org/t/p/original/" + movies.getBackdrop_path();
            Log.d("Test",movies.getId());
            Glide.with(itemView.getContext())
                    .load(poster)
                    .into(ivImg);


        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = getItem(position);
            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
            assert movie != null;
            intent.putExtra(DetailActivity.EXTRA_MOVIE, movie.getId());
            itemView.getContext().startActivity(intent);
        }

    }

}

