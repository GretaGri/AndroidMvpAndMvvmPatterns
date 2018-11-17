package mvvm_pattern.mvvmbyabhi.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enpassio.androidmvpandmvvmpatterns.R;

import mvvm_pattern.mvvmbyabhi.data.model.Article;

public class DetailsFragment extends Fragment {

    private Article article;
    
    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        Bundle bundle = getArguments();

        if (bundle != null)
            article = bundle.getParcelable("key");
            Log.d("my_tag", "Value is: " + article.getTitle());
        return rootView;
    }
}