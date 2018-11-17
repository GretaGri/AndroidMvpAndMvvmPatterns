package mvvm_pattern.mvvmbyabhi.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enpassio.androidmvpandmvvmpatterns.R;

import java.util.ArrayList;

import mvvm_pattern.mvvmbyabhi.data.model.Article;

public class CustomDialog extends DialogFragment {

    private static CustomDialog customDialog;
    private ArrayList<Article> mArticleArrayList;
    private int mCurrentPosition;

    public CustomDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static CustomDialog newInstance() {
        if (customDialog == null) {
            customDialog = new CustomDialog();
        }
        return customDialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        customDialog = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if (getArguments() !=null && !getArguments().isEmpty()){
           Bundle argumentsBundle= getArguments();
           mArticleArrayList = argumentsBundle.getParcelableArrayList("articlesArrayList");
           mCurrentPosition = argumentsBundle.getInt("position");
           Log.d("my_tag", "articlesArrayList received size is: "+mArticleArrayList.size());
           Log.d("my_tag", "position received size is: "+mCurrentPosition);
       }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_details_mvvm, container);
        ViewPager vpPager = (ViewPager) view.findViewById(R.id.view_pager);
        FragmentPagerAdapter adapterViewPager;
        adapterViewPager = new ArticlesDetailsPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        return view;
    }
}
