package mvvm_pattern.mvvmbyabhi.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.enpassio.androidmvpandmvvmpatterns.R;

import java.util.List;

import mvvm_pattern.mvvmbyabhi.data.model.Article;
import mvvm_pattern.mvvmbyabhi.viewmodel.ArticlesViewModel;

public class MainActivity extends AppCompatActivity {

    Button button;
    private ArticlesViewModel articlesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.main_button);
        articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articlesViewModel
                        .getNewsForQueriedParameter("India")
                        .observe(MainActivity.this, new Observer<List<Article>>() {
                            @Override
                            public void onChanged(@Nullable List<Article> articles) {
                                Log.d("my_tag", "size of articles received is: " + articles.size());
                            }
                        });
            }
        });
    }
}
