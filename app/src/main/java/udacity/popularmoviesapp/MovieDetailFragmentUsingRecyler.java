package udacity.popularmoviesapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kartiks.utility.LoggerGeneral;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import udacity.popularmoviesapp.adapters.AdapterTrailerReview;
import udacity.popularmoviesapp.persistance.DBHelper;
import udacity.popularmoviesapp.pojos.MovieWrapper;
import udacity.popularmoviesapp.pojos.ReviewWrapper;
import udacity.popularmoviesapp.pojos.TrailerWrapper;
import udacity.popularmoviesapp.utility.ApiCalls;
import udacity.popularmoviesapp.utility.Constants;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragmentUsingRecyler extends FragmentBase {
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
    public MovieDetailFragmentUsingRecyler() {
    }

    AdapterTrailerReview adapterTrailerReview;
    ArrayList<Object> item;
    RecyclerView movieTrailerReviews;

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
        View rootView = inflater.inflate(R.layout.movie_detail_recyler, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {

            item=new ArrayList<Object>();
            item.add("Rating :: " + mItem.getVoteAverage() + " /10    \nRelease date :: " + mItem.getReleaseDate());
            item.add(mItem.getOverview());

            DBHelper dbHelper=DBHelper.getInstance(getActivity());
            Object movie=dbHelper.genericRead(MovieWrapper.Movie.class,DBHelper.moviesTable,
                    DBHelper.id+"=?",new String[]{mItem.getId()+""},null,null,null);
            if(movie==null)
                mItem.setFavurite(false);
            else
                mItem.setFavurite(true);

            item.add(mItem);

            adapterTrailerReview=new AdapterTrailerReview(this.getActivity(),item);
            movieTrailerReviews=(RecyclerView)rootView.findViewById(R.id.MovieTrailerReviews);
            movieTrailerReviews.setHasFixedSize(false);
            movieTrailerReviews.setAdapter(adapterTrailerReview);

            if(isConnected()){
                showLoader();
                ApiCalls calls=Constants.getRetrofitInstance();
                Call<TrailerWrapper> call= calls.getTrailers(mItem.getId(), Constants.apiKey);
                call.enqueue(new Callback<TrailerWrapper>() {
                    @Override
                    public void onResponse(Response<TrailerWrapper> response, Retrofit retrofit) {
                        hideLoader();
                        if (response.isSuccess()) {

                            item.add("Trailers");
                            TrailerWrapper trailerWrapper = response.body();
                            item.addAll(trailerWrapper.getResults());
                            adapterTrailerReview.notifyDataSetChanged();

                        } else {
                            handleFailure(response);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        hideLoader();
                        LoggerGeneral.e(t.toString());
                    }
                });

                Call<ReviewWrapper> callR= calls.getReviews(mItem.getId(), Constants.apiKey);
                callR.enqueue(new Callback<ReviewWrapper>() {
                    @Override
                    public void onResponse(Response<ReviewWrapper> response, Retrofit retrofit) {
                        hideLoader();
                        if (response.isSuccess()) {
                            item.add("Reviews");
                            ReviewWrapper reviewWrapper = response.body();
                            item.addAll(reviewWrapper.getResults());
                            adapterTrailerReview.notifyDataSetChanged();
                        } else {
                            handleFailure(response);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        hideLoader();
                        LoggerGeneral.e(t.toString());
                    }
                });

            }else
                showCustomMessage(getResources().getString(R.string.noNet));
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
