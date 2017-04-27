package com.example.android.areyoukittyme.User;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sarah on 4/26/17.
 */

public class UserData implements Parcelable {
    private ArrayList<Double> data;

    public UserData() {
    }

    public UserData(ArrayList<Double> data){
        this.data = data;
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public void setData(ArrayList<Double> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    protected UserData(Parcel in) {
        this.data = new ArrayList<Double>();
        in.readList(this.data, Double.class.getClassLoader());
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel source) {
            return new UserData(source);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}
