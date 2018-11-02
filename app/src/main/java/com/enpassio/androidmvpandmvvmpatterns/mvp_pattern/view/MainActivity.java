package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network.NewsRepository;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter.MainActivityContract;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter.MainActivityPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.MainView {

    Button button;
    EditText editText;
    String searchQuery;
    RecyclerView recyclerView;
    MainActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.main_button);
        editText = findViewById(R.id.search_query_edit_text);
        recyclerView = findViewById(R.id.main_recycler_view);

        NewsRepository newsRepository = new NewsRepository();

        final MainActivityPresenter mainActivityPresenter = new MainActivityPresenter(newsRepository, recyclerView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getSearchPhrase();
               mainActivityPresenter.onButtonClick();
            }
        });
    }

    @Override
    public String getSearchPhrase() {
        searchQuery = editText.getText().toString();
        return searchQuery;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
