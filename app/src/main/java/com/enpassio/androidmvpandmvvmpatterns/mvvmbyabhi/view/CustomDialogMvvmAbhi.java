package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.Article;

import java.util.ArrayList;

public class CustomDialogMvvmAbhi extends DialogFragment {

    private ArrayList<Article> mArticleArrayList;
    private int mCurrentPosition;

    public CustomDialogMvvmAbhi() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && !getArguments().isEmpty()) {
            Bundle argumentsBundle = getArguments();
            mArticleArrayList = argumentsBundle.getParcelableArrayList("articlesArrayList");
            mCurrentPosition = argumentsBundle.getInt("position");
            Log.d("my_tag", "articlesArrayList received size is: " + mArticleArrayList.size());
            Log.d("my_tag", "position received size is: " + mCurrentPosition);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_details_mvvm_abhi, container);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        ArticleStatePagerAdapter adapterViewPager;
        adapterViewPager = new ArticleStatePagerAdapter(getChildFragmentManager(), mArticleArrayList);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(mCurrentPosition);
        viewPager.setOffscreenPageLimit(0);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    public void onResume() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels - 80;
        int width = displaymetrics.widthPixels - 20;
        getDialog().getWindow().setLayout(width, height);
        super.onResume();
    }
}
