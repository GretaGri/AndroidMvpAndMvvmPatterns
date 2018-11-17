package mvvm_pattern.mvvmbyabhi.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enpassio.androidmvpandmvvmpatterns.R;

public class CustomDialog extends DialogFragment {

    public CustomDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static CustomDialog newInstance() {
        return new CustomDialog();
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
