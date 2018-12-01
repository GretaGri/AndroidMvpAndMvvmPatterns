package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favoritearticlestable")
public class FavoriteArticle implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FavoriteArticle> CREATOR = new Parcelable.Creator<FavoriteArticle>() {
        @Override
        public FavoriteArticle createFromParcel(Parcel in) {
            return new FavoriteArticle(in);
        }

        @Override
        public FavoriteArticle[] newArray(int size) {
            return new FavoriteArticle[size];
        }
    };
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    @ColumnInfo(name = "url")
    private String url;

    private FavoriteArticle(Parcel in) {
        id = in.readInt();
        url = in.readString();
    }

    public FavoriteArticle(String urlOfArticle) {
        this.url = urlOfArticle;
    }

    public FavoriteArticle() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String urlOfArticle) {
        this.url = urlOfArticle;
    }

    public int getId() {
        return id;
    }

    public void setId(int i) {
        this.id = i;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
    }
}