package udacity.popularmoviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import udacity.popularmoviesapp.R;
import udacity.popularmoviesapp.persistance.DBHelper;
import udacity.popularmoviesapp.pojos.MovieWrapper;
import udacity.popularmoviesapp.pojos.ReviewWrapper;
import udacity.popularmoviesapp.pojos.TrailerWrapper;

/**
 * Created by kartikshah on 21/02/16.
 */
public class AdapterTrailerReview extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    // The items to display in your RecyclerView
    private List<Object> items;
    private final int TRAILER = 1, REVIEW = 2,SYNOPSIS=3,FAVOURITE=4;

    public AdapterTrailerReview(Context context,List<Object> items) {

        this.context=context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {

        Object object=items.get(position);
        if (object instanceof String) {
            return SYNOPSIS;
        } else if (object instanceof ReviewWrapper.Review) {
            return REVIEW;
        }else if (object instanceof TrailerWrapper.Trailer) {
            return TRAILER;
        }
        else if (object instanceof MovieWrapper.Movie) {
            return FAVOURITE;
        }
        return -1;
    }

    /**
     * Called when RecyclerView needs a new { ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * {#onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     *  #getItemViewType(int)
     *  #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder=null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case SYNOPSIS:
                View v1 = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder = new SynopsisHolder(v1);
                break;
            case TRAILER:
                v1 = inflater.inflate(R.layout.inflate_trailer, parent, false);
                viewHolder = new TrailerHolder(v1);
                break;
            case REVIEW:
                v1 = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
                viewHolder = new ReviewHolder(v1);
                break;
            case FAVOURITE:
                v1 = inflater.inflate(R.layout.infalte_favourite, parent, false);
                viewHolder = new FavouriteHolder(v1);
                break;
//            default:
//                View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
//                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
//                break;
        }
        return viewHolder;
    }



    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the { ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use { ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override { #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle effcient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case SYNOPSIS:
                SynopsisHolder synopsisHolder=(SynopsisHolder)holder;
                synopsisHolder.getTextView()
                        .setText((String)items.get(position));

                break;
            case TRAILER:
                TrailerHolder trailerHolder = (TrailerHolder)holder;
                TrailerWrapper.Trailer trailer =(TrailerWrapper.Trailer)items.get(position);
                Picasso
                        .with(context)
                        .load(trailer.getThumbnailUrl())
                        .fit()
                        .centerCrop()// to avoid a stretched image
                        .into(trailerHolder.getTrailerImage());
                break;
            case REVIEW:
                ReviewHolder reviewHolder=(ReviewHolder)holder;
                ReviewWrapper.Review review=(ReviewWrapper.Review)items.get(position);
                reviewHolder.getName().setText(review.getAuthor());
                reviewHolder.getReview().setText(review.getContent());
                break;

            case FAVOURITE:
                FavouriteHolder favouriteHolder=(FavouriteHolder)holder;
                MovieWrapper.Movie movie=(MovieWrapper.Movie)items.get(position);
                if(movie.isFavurite())
                    favouriteHolder.getTrailerImage().setBackgroundResource(R.drawable.ic_action_important_light);
                else
                    favouriteHolder.getTrailerImage().setBackgroundResource(R.drawable.ic_action_not_important_light);

                break;
        }
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class SynopsisHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public SynopsisHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView;
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public class ReviewHolder extends RecyclerView.ViewHolder{

        TextView name,review;


        public ReviewHolder(View itemView) {
            super(itemView);
            name    =   (TextView)itemView.findViewById(android.R.id.text1);
            review  =   (TextView)itemView.findViewById(android.R.id.text2);
        }

        public TextView getName() {
            return name;
        }

        public TextView getReview() {
            return review;
        }
    }

    public class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView trailerImage;

        public TrailerHolder(View itemView) {
            super(itemView);
            trailerImage    =   (ImageView)itemView.findViewById(R.id.TrailerImage);
            trailerImage.setOnClickListener(this);
        }

        public ImageView getTrailerImage() {
            return trailerImage;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            TrailerWrapper.Trailer trailer=(TrailerWrapper.Trailer)items.get(getPosition());
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getVideoUrl())));
        }
    }

    public class FavouriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView trailerImage;

        public FavouriteHolder(View itemView) {
            super(itemView);
            trailerImage    =   (ImageView)itemView.findViewById(R.id.Favourite);
            trailerImage.setOnClickListener(this);
        }

        public ImageView getTrailerImage() {
            return trailerImage;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            MovieWrapper.Movie movie=(MovieWrapper.Movie)items.get(getPosition());
            DBHelper dbHelper=DBHelper.getInstance(context);
            if(movie.isFavurite()){
                movie.setFavurite(false);
                dbHelper.deleteGeneric(DBHelper.moviesTable, DBHelper.id + "=?", new String[]{movie.getId() + ""});
            }else{
                movie.setFavurite(true);
                dbHelper.insertGeneric(movie, DBHelper.moviesTable);
            }
            notifyDataSetChanged();
        }
    }
}
