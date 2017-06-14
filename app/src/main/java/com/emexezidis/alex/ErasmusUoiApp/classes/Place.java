package com.emexezidis.alex.ErasmusUoiApp.classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Place implements Parcelable {

    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private String openTime = "Always open";

    public Place (String name, String description, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Place (String name, String description, double latitude, double longitude, String openTime) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openTime = openTime;
    }

    protected Place(Parcel in) {
        name = in.readString();
        description = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        openTime = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getName() {
        return name;
    }
    public String getDescription() { return description; }
    public LatLng getMapLatLng() { return new LatLng(latitude, longitude); }
    public String getOpenTime() { return openTime; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(openTime);
    }
}
