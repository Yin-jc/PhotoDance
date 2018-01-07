package com.yjc.photodance.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/1/7/007.
 */

public class Details extends DataSupport implements Parcelable{

    private int position;
//    private String userId;
    private String username;
    private String location;
//    private String profileImage;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

//    public String getuserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(userId);
        parcel.writeString(username);
        parcel.writeString(location);
    }

    public static final Parcelable.Creator<Details> CREATOR = new Creator<Details>() {
        @Override
        public Details createFromParcel(Parcel parcel) {
            Details details = new Details();
//            details.setUserId(parcel.readString());
            details.setUsername(parcel.readString());
            details.setLocation(parcel.readString());
            return details;
        }

        @Override
        public Details[] newArray(int i) {
            return new Details[i];
        }
    };

    @Override
    public String toString() {
        return
//                "ID : "
//                + getuserId()
//                + "\r\n"
                "UserName : "
                + getUsername()
                + "\r\n\r\n"
                +"Location : "
                + getLocation();
    }

    //    public String getProfileImage() {
//        return profileImage;
//    }
//
//    public void setProfileImage(String profileImage) {
//        this.profileImage = profileImage;
//    }
}
