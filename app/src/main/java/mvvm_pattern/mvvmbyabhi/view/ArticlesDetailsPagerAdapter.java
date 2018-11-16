package mvvm_pattern.mvvmbyabhi.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enpassio.androidmvpandmvvmpatterns.R;

class ArticlesDetailsPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int mSizeOfArticleList;

    ArticlesDetailsPagerAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        Toast.makeText(mContext, "In the dialog fragment now", Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_details, collection, false);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "pager";
    }

}