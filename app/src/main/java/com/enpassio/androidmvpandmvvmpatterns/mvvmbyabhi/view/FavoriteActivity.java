package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.viewmodel.DetailsFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private DetailsFragmentViewModel detailsFragmentViewModel;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutmanager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.recycler_view_activity_favorite);
        mLayoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutmanager);
        detailsFragmentViewModel = ViewModelProviders.of(this).get(DetailsFragmentViewModel.class);
        detailsFragmentViewModel.getArticlesListLiveData().observe(this, new Observer<List<FavoriteArticle>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteArticle> favoriteArticles) {
                ArrayList<FavoriteArticle> articles = new ArrayList<>();
                articles.addAll(favoriteArticles);
                for (FavoriteArticle article : articles
                        ) {
                    Log.v("my_tag", "url of article is: " + article.getUrl());
                }
            }
        });
    }
}
