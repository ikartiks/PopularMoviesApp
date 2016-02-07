package udacity.popularmoviesapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import udacity.popularmoviesapp.pojos.MovieWrapper;
import udacity.popularmoviesapp.utility.Constants;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends FragmentBase {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private MovieWrapper.Movie mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = getArguments().getParcelable(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            //Toolbar toolbar=(Toolbar)activity.findViewById(R.id.detail_toolbar);
            //toolbar.setPadding(0, statusBarHeight, 0, 0);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getTitle());
                ImageView image = (ImageView) activity.findViewById(R.id.DetailPoster);
                Picasso
                    .with(activity)
                    .load(Constants.imageBaseUrl + mItem.getPosterPath())
                    .fit()
                    .centerInside()// to avoid a stretched image
                    .into(image);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.movie_detail)).setText(mItem.getOverview());
            ((TextView) rootView.findViewById(R.id.Average)).setText(mItem.getVoteAverage()+" / 10");
            ((TextView) rootView.findViewById(R.id.ReleaseDate)).setText(mItem.getReleaseDate());
        }

        return rootView;
    }
}
