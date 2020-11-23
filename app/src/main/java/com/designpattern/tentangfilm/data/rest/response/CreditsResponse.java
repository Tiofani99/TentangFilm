package com.designpattern.tentangfilm.data.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CreditsResponse implements Parcelable {

    @SerializedName("id")
    private String id_movie;

    @SerializedName("original_name")
    private String original_name;

    @SerializedName("profile_path")
    private String profile_path;

    @SerializedName("cast")
    private ArrayList<CreditsResponse> list;


    protected CreditsResponse(Parcel in) {
        id_movie = in.readString();
        original_name = in.readString();
        profile_path = in.readString();
        list = in.createTypedArrayList(CreditsResponse.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_movie);
        dest.writeString(original_name);
        dest.writeString(profile_path);
        dest.writeTypedList(list);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreditsResponse> CREATOR = new Creator<CreditsResponse>() {
        @Override
        public CreditsResponse createFromParcel(Parcel in) {
            return new CreditsResponse(in);
        }

        @Override
        public CreditsResponse[] newArray(int size) {
            return new CreditsResponse[size];
        }
    };

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public ArrayList<CreditsResponse> getList() {
        return list;
    }

    public void setList(ArrayList<CreditsResponse> list) {
        this.list = list;
    }


}
