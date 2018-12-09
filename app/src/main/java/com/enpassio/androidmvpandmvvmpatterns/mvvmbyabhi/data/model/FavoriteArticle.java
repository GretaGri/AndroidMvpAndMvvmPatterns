package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favoritearticlestable")
public class FavoriteArticle implements Parcelable, Comparable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    public final static Parcelable.Creator<FavoriteArticle> CREATOR = new Creator<FavoriteArticle>() {
        @SuppressWarnings({
                "unchecked"
        })
        public FavoriteArticle createFromParcel(Parcel in) {
            return new FavoriteArticle(in);
        }

        public FavoriteArticle[] newArray(int size) {
            return (new FavoriteArticle[size]);
        }

    };
    public static DiffUtil.ItemCallback<FavoriteArticle> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavoriteArticle>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavoriteArticle oldItem, @NonNull FavoriteArticle newItem) {
            return oldItem.url.equals(newItem.url);
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavoriteArticle oldItem, @NonNull FavoriteArticle newItem) {
            return oldItem.equals(newItem);
        }
    };
    @SerializedName("author")
    @Expose
    private String author;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title = "title";
    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;
    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose
    private String url;
    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;

    public FavoriteArticle(String titleFav, String descriptionFav, String publishedAtFav, String authorFav, String urlFav) {
        this.title = titleFav;
        this.description = descriptionFav;
        this.publishedAt = publishedAtFav;
        this.author = authorFav;
        this.url = urlFav;
    }

    public FavoriteArticle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int i) {
        this.id = i;
    }

    private FavoriteArticle(Parcel in) {
        this.author = ((String) in.readValue((Object.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.publishedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishedAt() {
        return publishedAt;
    }


    public int describeContents() {
        return 0;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        FavoriteArticle compare = (FavoriteArticle) o;
        if (compare.title.equals(this.title)
                && compare.url.equals(this.url)) {
            return 0;
        }
        return 1;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(author);
        dest.writeValue(title);
        dest.writeValue(description);
        dest.writeValue(url);
        dest.writeValue(publishedAt);
    }
}