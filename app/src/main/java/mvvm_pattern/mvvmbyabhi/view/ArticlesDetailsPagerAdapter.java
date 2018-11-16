package mvvm_pattern.mvvmbyabhi.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enpassio.androidmvpandmvvmpatterns.R;

class ArticlesDetailsPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int mSizeOfArticleList;
    private FragmentManager mFragmentManager;
    private ViewGroup mLayout;

    ArticlesDetailsPagerAdapter(Context context, FragmentManager fragmentManager) {
        mContext = context;
        mFragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        Toast.makeText(mContext, "In the dialog fragment now", Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (mLayout == null)
            mLayout = (ViewGroup) inflater.inflate(R.layout.contents_view_pager, collection, false);
        collection.addView(mLayout);

        Bundle bundle = new Bundle();
        bundle.putString("key", "value");
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        mFragmentManager.beginTransaction()
                .add(R.id.frame_layout, detailsFragment)
                .commit();

        return mLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "pager";
    }

}