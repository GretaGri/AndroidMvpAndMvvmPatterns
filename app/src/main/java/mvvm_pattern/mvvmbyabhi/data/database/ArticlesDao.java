package mvvm_pattern.mvvmbyabhi.data.database;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import mvvm_pattern.mvvmbyabhi.data.model.Article;

@Dao
public interface ArticlesDao {

    @Query("SELECT * from articlestable")
    List<Article> getAllArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(Article article);

    @Query("DELETE FROM articlestable")
    void deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArticle(Article article);

    @Query("SELECT * from articlestable")
    DataSource.Factory<Integer, Article> getAllArticlesPage();
}