package com.emexezidis.alex.ErasmusUoiApp.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {

    private String group;
    private String name;
    private String description;
    private String url = null;
    private float longitude = 0f;
    private float latitude = 0f;
    private boolean state = false;
    private boolean descriptionVisibility = false;
    private boolean generalVisibility = true;

    public Step (String name) {
        this.name = name;
    }

    public Step (String group, String name) {

        this.group = group;
        this.name = name;
        this.description = "No description added";
        this.url = "No url available";
    }

    public Step (String group, String name, String description) {

        this.group = group;
        this.name = name;
        this.description = description;
        this.url = "Not available";
    }

    protected Step (Parcel in) {
        group = in.readString();
        name = in.readString();
        description = in.readString();
        url = in.readString();
        longitude = in.readFloat();
        latitude = in.readFloat();
        state = in.readByte() != 0;
        descriptionVisibility = in.readByte() != 0;
        generalVisibility = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(group);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeFloat(longitude);
        dest.writeFloat(latitude);
        dest.writeByte((byte) (state ? 1 : 0));
        dest.writeByte((byte) (descriptionVisibility ? 1 : 0));
        dest.writeByte((byte) (generalVisibility ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGeolocationAvailable() {
        return longitude != 0 && latitude != 0;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void changeState() {
        this.state = !this.state;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public String getGroup() { return group; }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}