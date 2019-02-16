package udacity.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import udacity.popularmoviesapp.adapters.AdapterTrailerReview;
import udacity.popularmoviesapp.pojos.MovieWrapper;

public class ActivityMovieDetailUsingRecyler extends ActivityBase {

    AdapterTrailerReview adapterTrailerReview;
    ArrayList<Object> item;
    RecyclerView movieTrailerReviews;

    private MovieWrapper.Movie mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_using_recyler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mItem = getIntent().getParcelableExtra(MovieDetailFragmentUsingRecyler.ARG_ITEM_ID);
//            MovieDetailFragmentUsingRecyler fragment = new MovieDetailFragmentUsingRecyler();
//            fragment.setArguments(arguments);
//            getFragmentManager().beginTransaction()
//                    .add(R.id.movie_detail_container, fragment)
//                    .commit();

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

            arguments.putParcelable(MovieDetailFragmentUsingRecyler.ARG_ITEM_ID,
                    getIntent().getParcelableExtra(MovieDetailFragmentUsingRecyler.ARG_ITEM_ID));
            MovieDetailFragmentUsingRecyler fragment = new MovieDetailFragmentUsingRecyler();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back

            //below starts new activity
            NavUtils.navigateUpTo(this, new Intent(this, MovieListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
