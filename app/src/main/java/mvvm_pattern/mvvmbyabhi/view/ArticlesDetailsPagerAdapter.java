package mvvm_pattern.mvvmbyabhi.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ArticlesDetailsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"City Information", "Reach Here By", "Hotels", "Must Visit"};
    private DetailsFragment detailsFragment;
    private FragmentManager mfm;

    ArticlesDetailsPagerAdapter(FragmentManager fmg) {
        super(fmg);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Bundle bundle = new Bundle();
            bundle.putString("key", "value1");
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);
            return detailsFragment;
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putString("key", "value2");
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);
            return detailsFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


/*
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
                .replace(R.id.frame_layout, detailsFragment)
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
*/

}
