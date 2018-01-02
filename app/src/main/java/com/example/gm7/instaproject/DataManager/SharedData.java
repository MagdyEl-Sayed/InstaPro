package com.example.gm7.instaproject.DataManager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by emad on 1/2/18.
 */

public class SharedData implements Parcelable {
    private String user_email;
    private String mainProjectUrl;
    private String image2Url;
    private String image3Url;
    private String image4Url;
    private String project_bio;

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setMainProjectUrl(String mainProjectUrl) {
        this.mainProjectUrl = mainProjectUrl;
    }

    public void setImage2Url(String image2Url) {
        this.image2Url = image2Url;
    }

    public void setImage3Url(String image3Url) {
        this.image3Url = image3Url;
    }

    public void setImage4Url(String image4Url) {
        this.image4Url = image4Url;
    }

    public void setProject_bio(String project_bio) {
        this.project_bio = project_bio;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getMainProjectUrl() {
        return mainProjectUrl;
    }

    public String getImage2Url() {
        return image2Url;
    }

    public String getImage3Url() {
        return image3Url;
    }

    public String getImage4Url() {
        return image4Url;
    }

    public String getProject_bio() {
        return project_bio;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_email);
        dest.writeString(this.mainProjectUrl);
        dest.writeString(this.image2Url);
        dest.writeString(this.image3Url);
        dest.writeString(this.image4Url);
        dest.writeString(this.project_bio);
    }

    public SharedData() {
    }

    protected SharedData(Parcel in) {
        this.user_email = in.readString();
        this.mainProjectUrl = in.readString();
        this.image2Url = in.readString();
        this.image3Url = in.readString();
        this.image4Url = in.readString();
        this.project_bio = in.readString();
    }

    public static final Parcelable.Creator<SharedData> CREATOR = new Parcelable.Creator<SharedData>() {
        @Override
        public SharedData createFromParcel(Parcel source) {
            return new SharedData(source);
        }

        @Override
        public SharedData[] newArray(int size) {
            return new SharedData[size];
        }
    };
}
