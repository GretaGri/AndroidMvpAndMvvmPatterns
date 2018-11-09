package mvvm_pattern.mvvmbyabhi.data;

import androidx.paging.PageKeyedDataSource;
import mvvm_pattern.mvvmbyabhi.data.model.Article;

public class ArticleDataSource extends PageKeyedDataSource<Long, Article> {

    @Override
    public void loadInitial(@androidx.annotation.NonNull LoadInitialParams<Long> params,
                            @androidx.annotation.NonNull LoadInitialCallback<Long, Article> callback) {

    }

    @Override
    public void loadBefore(@androidx.annotation.NonNull LoadParams<Long> params,
                           @androidx.annotation.NonNull LoadCallback<Long, Article> callback) {

    }

    @Override
    public void loadAfter(@androidx.annotation.NonNull LoadParams<Long> params,
                          @androidx.annotation.NonNull LoadCallback<Long, Article> callback) {

    }
}