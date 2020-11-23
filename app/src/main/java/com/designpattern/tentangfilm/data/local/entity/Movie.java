package com.designpattern.tentangfilm.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movie implements Parcelable {

    @ColumnInfo(name = "id")
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "poster_path")
    private String img;

    @ColumnInfo(name = "vote_count")
    private String vote_count;

    @ColumnInfo(name = "vote_average")
    private Double vote_avg;

    @ColumnInfo(name = "overview")
    private String desc;

    @ColumnInfo(name = "release_date")
    private String release_date;

    @ColumnInfo(name = "popularity")
    private Double popularity;

    @ColumnInfo(name = "backdrop_path")
    private String backdrop_path;

    @ColumnInfo(name = "bookmarked")
    private boolean bookmarked = false;

    @ColumnInfo(name = "state_popular")
    private boolean state_popular = false;


    public Movie(@NonNull String id, String title, String img, String vote_count, Double vote_avg, String desc, String release_date, Double popularity, String backdrop_path, boolean bookmarked, boolean state_popular) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.vote_count = vote_count;
        this.vote_avg = vote_avg;
        this.desc = desc;
        this.release_date = release_date;
        this.popularity = popularity;
        this.backdrop_path = backdrop_path;
        this.bookmarked = bookmarked;
        this.state_popular = state_popular;
    }


    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        img = in.readString();
        vote_count = in.readString();
        if (in.readByte() == 0) {
            vote_avg = null;
        } else {
            vote_avg = in.readDouble();
        }
        desc = in.readString();
        release_date = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        backdrop_path = in.readString();
        bookmarked = in.readByte() != 0;
        state_popular = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public String getVote_count() {
        return vote_count;
    }

    public Double getVote_avg() {
        return vote_avg;
    }

    public String getDesc() {
        return desc;
    }

    public String getRelease_date() {
        return release_date;
    }


    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public boolean isState_popular() {
        return state_popular;
    }

    public void setState_popular(boolean state_popular) {
        this.state_popular = state_popular;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(img);
        parcel.writeString(vote_count);
        if (vote_avg == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(vote_avg);
        }
        parcel.writeString(desc);
        parcel.writeString(release_date);
        if (popularity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(popularity);
        }
        parcel.writeString(backdrop_path);
        parcel.writeByte((byte) (bookmarked ? 1 : 0));
        parcel.writeByte((byte) (state_popular ? 1 : 0));
    }


}
