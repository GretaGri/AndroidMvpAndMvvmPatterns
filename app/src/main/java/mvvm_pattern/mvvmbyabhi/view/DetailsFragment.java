package mvvm_pattern.mvvmbyabhi.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.enpassio.androidmvpandmvvmpatterns.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import mvvm_pattern.mvvmbyabhi.data.model.Article;

public class DetailsFragment extends Fragment {

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        Article article = bundle.getParcelable("key");
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        TextView articleTitleTextView = view.findViewById(R.id.article_title_text_view);
        TextView articleContentTextView = view.findViewById(R.id.article_content_text_view);
        TextView articleAuthorTextView = view.findViewById(R.id.article_author_text_view);
        TextView articleSourceTextView = view.findViewById(R.id.article_source_text_view);
        ImageView articleBannerImageView = view.findViewById(R.id.article_banner_image);
        ShimmerFrameLayout shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();
        articleTitleTextView.setText(article.getTitle());
        articleTitleTextView.setSelected(true);
        articleContentTextView.setText(article.getContent());
        Log.v("my_taga", "author is: " + article.getAuthor());
        Log.v("my_taga", "source is: " + article.getSource());
        if (article.getAuthor() != null)
            articleAuthorTextView.setText(String.format("Author: %s", article.getAuthor().toString()));
        if (article.getUrl() != null)
            articleSourceTextView.setText(String.format("Source: %s", article.getUrl()));
        GlideApp
                .with(getActivity())
                .load(article.getUrlToImage())
                .centerCrop()
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model,
                                                Target<Drawable> target,
                                                boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource,
                                                   Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource,
                                                   boolean isFirstResource) {
                        shimmerFrameLayout.stopShimmer();
                        return false;
                    }
                })
                .into(articleBannerImageView);
        return view;
    }
}