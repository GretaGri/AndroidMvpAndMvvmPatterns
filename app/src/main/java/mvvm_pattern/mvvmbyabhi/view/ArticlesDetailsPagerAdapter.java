package mvvm_pattern.mvvmbyabhi.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import mvvm_pattern.mvvmbyabhi.data.model.Article;

public class ArticlesDetailsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Article> mArticleArrayList;
    private int mCurrentPosition;
    private FragmentManager mfm;

    ArticlesDetailsPagerAdapter(FragmentManager fmg, int currentPosition, ArrayList<Article> articleArrayList) {
        super(fmg);
        mArticleArrayList = articleArrayList;
        mCurrentPosition = currentPosition;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundleForCurrent = new Bundle();
        bundleForCurrent.putParcelable("key", mArticleArrayList.get(position));
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundleForCurrent);
        return detailsFragment;
    }

    @Override
    public int getCount() {
        return mArticleArrayList.size();
    }
}