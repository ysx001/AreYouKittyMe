package com.example.android.areyoukittyme.User;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jingya on 5/2/17.
 */

public class InventoryList implements Parcelable {

    private HashMap<Integer, Integer> inventoryList;

    public InventoryList() {
        this.inventoryList = new HashMap<Integer, Integer>();
    }

    public InventoryList(HashMap<Integer, Integer> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public HashMap<Integer, Integer> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(HashMap<Integer, Integer> inventoryList) {
        this.inventoryList = inventoryList;
    }

    protected InventoryList(Parcel in) {
        this.inventoryList = new HashMap<Integer, Integer>();

        final int N = in.readInt();
        for (int i=0; i<N; i++) {
            int key = in.readInt();
            int dat = in.readInt();
            this.inventoryList.put(key, dat);
        }
    }

    public static final Creator<InventoryList> CREATOR = new Creator<InventoryList>() {
        @Override
        public InventoryList createFromParcel(Parcel in) {
            return new InventoryList(in);
        }

        @Override
        public InventoryList[] newArray(int size) {
            return new InventoryList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        final int N = this.inventoryList.size();
        dest.writeInt(N);

        if (N > 0) {
            for (Map.Entry<Integer, Integer> entry : this.inventoryList.entrySet()) {
                dest.writeInt(entry.getKey());
                dest.writeInt(entry.getValue());
            }
        }
    }
}
