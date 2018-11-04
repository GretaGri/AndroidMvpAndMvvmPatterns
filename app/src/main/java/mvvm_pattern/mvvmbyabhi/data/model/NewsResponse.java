package mvvm_pattern.mvvmbyabhi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse implements Parcelable {

    public final static Parcelable.Creator<NewsResponse> CREATOR = new Creator<NewsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NewsResponse createFromParcel(Parcel in) {
            return new NewsResponse(in);
        }

        public NewsResponse[] newArray(int size) {
            return (new NewsResponse[size]);
        }

    };
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("articles")
    @Expose
    private List<com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.MvpByAbhi.data.model.Article> articles = null;

    private NewsResponse(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.articles, (com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.MvpByAbhi.data.model.Article.class.getClassLoader()));
    }

    public NewsResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.MvpByAbhi.data.model.Article> getArticles() {
        return articles;
    }

    public void setArticles(List<com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.MvpByAbhi.data.model.Article> articles) {
        this.articles = articles;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(totalResults);
        dest.writeList(articles);
    }

    public int describeContents() {
        return 0;
    }

}