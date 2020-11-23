package com.designpattern.tentangfilm.data.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String img;

    @SerializedName("vote_count")
    private String vote_count;

    @SerializedName("vote_average")
    private Double vote_avg;

    @SerializedName("overview")
    private String desc;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("backdrop_path")
    private String backdrop;


    @SerializedName("results")
    private ArrayList<MovieResponse> list;


    public MovieResponse(String id, String title, String img, String vote_count, Double vote_avg, String desc, String release_date, Double popularity, String backdrop) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.vote_count = vote_count;
        this.vote_avg = vote_avg;
        this.desc = desc;
        this.release_date = release_date;
        this.popularity = popularity;
        this.backdrop = backdrop;
    }

    public MovieResponse() {
    }

    protected MovieResponse(Parcel in) {
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
        backdrop = in.readString();
        list = in.createTypedArrayList(MovieResponse.CREATOR);
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    public ArrayList<MovieResponse> getList() {
        return list;
    }

    public void setList(ArrayList<MovieResponse> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
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
        parcel.writeTypedList(list);
        parcel.writeString(backdrop);
    }
}
