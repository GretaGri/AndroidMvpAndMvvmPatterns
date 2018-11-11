package mvvm_pattern.mvvmbyabhi.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.enpassio.androidmvpandmvvmpatterns.R;

import mvvm_pattern.mvvmbyabhi.data.model.Article;
import mvvm_pattern.mvvmbyabhi.viewmodel.ArticlesViewModel;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ArticlesViewModel articlesViewModel;
    /* Adapters for inflating different recyclerview */
    ArticlePagedListAdapter mNewsAdapter;
    /* Set layout managers on those recycler views */
    LinearLayoutManager newslayoutmanager;
    RecyclerView mNewsrecyclerView;
    EditText searchQueryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mNewsrecyclerView = findViewById(R.id.main_recycler_view);
        searchQueryEditText = findViewById(R.id.search_query_edit_text);
        /*
         * Setup layout manager for items with orientation
         * Also supports `LinearLayoutManager.HORIZONTAL`
         */
        newslayoutmanager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        /* Attach layout manager to the RecyclerView */
        mNewsrecyclerView.setLayoutManager(newslayoutmanager);
        mNewsAdapter = new ArticlePagedListAdapter(this);
        mNewsrecyclerView.setAdapter(mNewsAdapter);

        button = findViewById(R.id.main_button);
        articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articlesViewModel.getArticleLiveData(searchQueryEditText.getText().toString())
                        .observe(MainActivity.this, new Observer<PagedList<Article>>() {
                            @Override
                            public void onChanged(@Nullable PagedList<Article> pagedLists) {
                                mNewsAdapter.submitList(pagedLists);
                            }
                        });
            }
        });
    }
}
