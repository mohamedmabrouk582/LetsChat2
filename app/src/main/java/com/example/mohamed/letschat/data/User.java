package com.example.mohamed.letschat.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :19:33
 */

public class User implements Parcelable {
    private String name;
    private String email;
    private String imageUrl;
    private String status;
    private String device_token;
    private boolean online;
    private Long lastSeen;


    public Long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Long lastSeen) {
        this.lastSeen = lastSeen;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }


    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public User() {
    }

    public User(String name, String email, String imageUrl, String status, String device_token) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.status = status;
        this.device_token = device_token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.imageUrl);
        dest.writeString(this.status);
        dest.writeString(this.device_token);
        dest.writeByte(this.online ? (byte) 1 : (byte) 0);
        dest.writeValue(this.lastSeen);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.imageUrl = in.readString();
        this.status = in.readString();
        this.device_token = in.readString();
        this.online = in.readByte() != 0;
        this.lastSeen = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
