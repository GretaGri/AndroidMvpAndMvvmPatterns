package mvvm_pattern.mvvmbyabhi.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import mvvm_pattern.mvvmbyabhi.data.model.Article;

public class ArticlesDetailsPagerAdapterMvvmAbhi extends FragmentPagerAdapter {

    private ArrayList<Article> mArticleArrayList;
    private int mCurrentPosition;
    private FragmentManager mfm;

    ArticlesDetailsPagerAdapterMvvmAbhi(FragmentManager fmg, int currentPosition, ArrayList<Article> articleArrayList) {
        super(fmg);
        mArticleArrayList = articleArrayList;
        mCurrentPosition = currentPosition;
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