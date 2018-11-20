package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network.NewsRepository;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter.MainActivityContract;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter.MainActivityPresenter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityContract.PresenterPushedSomeAction {

    Button button;
    EditText editText;
    String searchQuery;
    RecyclerView recyclerView;
    MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.main_button);
        editText = findViewById(R.id.search_query_edit_text);
        recyclerView = findViewById(R.id.main_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        NewsRepository newsRepository = NewsRepository.getInstance();

       mainActivityPresenter = new MainActivityPresenter(newsRepository);
        mainActivityPresenter.attachView(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityPresenter.onButtonClick(getSearchPhrase());
            }
        });
    }


    public String getSearchPhrase() {
        searchQuery = editText.getText().toString();
        return searchQuery;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityPresenter.onDestroy();
    }

    @Override
    public void showNewsList(ArrayList<Article> news) {
        Log.d("my_tag", "inside MainActivity, news size is :" + news.size());
        NewsAdapter adapter = new NewsAdapter(news);
        recyclerView.setAdapter(adapter);
    }


}
