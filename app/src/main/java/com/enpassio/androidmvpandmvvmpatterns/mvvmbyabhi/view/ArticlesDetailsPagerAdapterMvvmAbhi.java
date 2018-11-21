package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.Article;

import java.util.ArrayList;

public class ArticlesDetailsPagerAdapterMvvmAbhi extends FragmentPagerAdapter {

    private ArrayList<Article> mArticleArrayList;

    ArticlesDetailsPagerAdapterMvvmAbhi(FragmentManager fmg, ArrayList<Article> articleArrayList) {
        super(fmg);
        mArticleArrayList = articleArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundleForCurrent = new Bundle();
        bundleForCurrent.putParcelable("key", mArticleArrayList.get(position));
        DetailsFragmentMvvmAbhi detailsFragment = new DetailsFragmentMvvmAbhi();
        detailsFragment.setArguments(bundleForCurrent);
        return detailsFragment;
    }

    @Override
    public int getCount() {
        return mArticleArrayList.size();
    }
}