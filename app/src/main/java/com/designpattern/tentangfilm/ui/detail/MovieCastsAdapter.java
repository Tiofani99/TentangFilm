package com.designpattern.tentangfilm.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.designpattern.tentangfilm.R;
import com.designpattern.tentangfilm.data.rest.response.CreditsResponse;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieCastsAdapter extends RecyclerView.Adapter<MovieCastsAdapter.CastViewHolder> {

    private final ArrayList<CreditsResponse> list;

    public MovieCastsAdapter(ArrayList<CreditsResponse> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credits, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        final CreditsResponse creditsResponse = list.get(position);
        holder.bind(creditsResponse);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class CastViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item_photo)
        ImageView img;
        @BindView(R.id.tv_item_name)
        TextView tvName;

        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(CreditsResponse creditsResponse) {
            String profile = "https://image.tmdb.org/t/p/w185/" + creditsResponse.getProfile_path();
            Glide.with(itemView.getContext())
                    .load(profile)
                    .into(img);
            tvName.setText(creditsResponse.getOriginal_name());
        }
    }
}
