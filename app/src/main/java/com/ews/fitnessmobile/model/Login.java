package com.ews.fitnessmobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wallace on 10/07/17.
 */
public class Login implements Parcelable {

    @SerializedName("usuario")
    private String username;

    @SerializedName("senha")
    private String password;

    @Expose(serialize = false, deserialize = false)
    private Role role;

    public Login() {}

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected Login(Parcel in) {
        username = in.readString();
        password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Login> CREATOR = new Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel in) {
            return new Login(in);
        }

        @Override
        public Login[] newArray(int size) {
            return new Login[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return (username.equalsIgnoreCase("android")) ? Role.ADMIN : Role.USER;
    }

    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + getRole() +
                '}';
    }


}
