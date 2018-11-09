package mvvm_pattern.mvvmbyabhi.data;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import androidx.paging.DataSource;

public class ArticleDataFactory extends DataSource.Factory {

    private MutableLiveData<ArticleDataSource> mutableLiveData;

    ArticleDataFactory() {
        this.mutableLiveData = new MutableLiveData<ArticleDataSource>();
    }

    @NonNull
    @Override
    public DataSource create() {
        ArticleDataSource articleDataSource = new ArticleDataSource();
        mutableLiveData.postValue(articleDataSource);
        return articleDataSource;
    }

    public MutableLiveData<ArticleDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}