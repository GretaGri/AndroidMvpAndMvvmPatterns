package mvvm_pattern.mvvmbyabhi.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enpassio.androidmvpandmvvmpatterns.R;

import mvvm_pattern.mvvmbyabhi.data.model.Article;

public class DetailsFragment extends Fragment {

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        Article article = bundle.getParcelable("key");
        Log.d("my_tag", "Value is: " + article.getTitle());
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        TextView textView = view.findViewById(R.id.article_title_text_view);
        textView.setText("" + article.getTitle());
        ImageView imageView = view.findViewById(R.id.news_banner);
        GlideApp
                .with(getActivity())
                .load(article.getUrlToImage())
                .centerCrop()
                .into(imageView);
        return view;
    }
}