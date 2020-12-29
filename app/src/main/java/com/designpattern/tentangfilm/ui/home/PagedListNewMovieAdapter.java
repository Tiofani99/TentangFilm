package com.designpattern.tentangfilm.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagedListNewMovieAdapter extends PagedListAdapter<Movie, PagedListNewMovieAdapter.MovieViewHolder> {
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


    public PagedListNewMovieAdapter(FragmentCallback callback) {
        super(DIFF_CALLBACK);
        this.callback = callback;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_film, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        assert movie != null;
        holder.bind(movie);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_film)
        ImageView ivImg;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        void bind(Movie movies) {

            String img = BuildConfig.LINK_IMAGE + movies.getImg();
            Glide.with(itemView.getContext())
                    .load(img)
                    .centerCrop()
                    .into(ivImg);
            Log.d("Test", movies.getId());


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

        String changeFormatDate(String date) {
            String[] splitDate = date.split("-");
            String part1 = splitDate[0];
            int part2 = Integer.parseInt(splitDate[1]);
            String part3 = splitDate[2];
            String[] month = itemView.getResources().getStringArray(R.array.month);

            String monthConvert = "month";

            for (int i = 1; i <= 12; i++) {
                if (i == part2) {
                    monthConvert = month[i - 1];
                }
            }

            return part3 + " " + monthConvert + " " + part1;
        }
    }
}
