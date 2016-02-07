package udacity.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kartiks.utility.LoggerGeneral;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import udacity.popularmoviesapp.pojos.MovieWrapper;
import udacity.popularmoviesapp.utility.ApiCalls;
import udacity.popularmoviesapp.utility.Constants;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends ActivityBase {

    Context context=this;

    List<MovieWrapper.Movie> movieList;
    SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter;
    String sortBy;

    public static final String sortByPopularity="popularity.desc";
    public static final String sortByRating="vote_average.desc";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_toolvar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.Popularity){
            sortBy=sortByPopularity;
        }else{
            sortBy=sortByRating;
        }
        callService(sortBy);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        sortBy=sortByPopularity;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.setVisibility(View.INVISIBLE);

        movieList=new ArrayList<MovieWrapper.Movie>();
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.movie_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.setHasFixedSize(true);
        simpleItemRecyclerViewAdapter=new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(simpleItemRecyclerViewAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(movieList.size()==0)
            callService(sortBy);

    }

    private void callService(String sortBy){
        if(isConnected()){
            showLoader();
            ApiCalls calls=Constants.getRetrofitInstance();
            Call<MovieWrapper> call= calls.getMovieList(sortBy,Constants.apiKey);
            call.enqueue(new Callback<MovieWrapper>() {
                @Override
                public void onResponse(Response<MovieWrapper> response, Retrofit retrofit) {
                    hideLoader();
                    if (response.isSuccess()) {
                        MovieWrapper movieWrapper = response.body();
                        movieList=movieWrapper.getResults();
                        simpleItemRecyclerViewAdapter.notifyDataSetChanged();
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
            showCustomMessage(getResources().getString(R.string.app_name));
    }

    //    SimpleItemRecyclerViewAdapter starts
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

//        private final List<MovieWrapper.Movie> movieList;
//
//        public SimpleItemRecyclerViewAdapter(List<MovieWrapper.Movie> items) {
//            movieList = items;
//        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = movieList.get(position);
            holder.title.setText(holder.mItem.getTitle());
            //holder.mContentView.setText(movieList.get(position).content);
            Picasso.with(context).load(Constants.imageBaseUrl+holder.mItem.getPosterPath()).into(holder.image);
            //holder.mView.setTag(holder.mItem);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        //arguments.putLong(MovieDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
                        arguments.putParcelable(MovieDetailFragment.ARG_ITEM_ID,holder.mItem);
                        MovieDetailFragment fragment = new MovieDetailFragment();
                        fragment.setArguments(arguments);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.movie_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MovieDetailActivity.class);
                        //intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
                        Bundle mBundle = new Bundle();
                        mBundle.putParcelable(MovieDetailFragment.ARG_ITEM_ID, holder.mItem);

                        intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, holder.mItem);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView title;
            public final ImageView image;
            public MovieWrapper.Movie mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                title = (TextView) view.findViewById(R.id.Title);
                image = (ImageView) view.findViewById(R.id.Image);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + title.getText() + "'";
            }
        }
    }
//    SimpleItemRecyclerViewAdapter ends

}
