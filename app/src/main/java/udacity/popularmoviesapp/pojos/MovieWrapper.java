package udacity.popularmoviesapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieWrapper {

    /**
     * An array of sample (dummy) items.
     */
    public List<Movie> results = new ArrayList<Movie>();

    int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }


    /**
     * A map of sample (dummy) items, by ID.

    public static final Map<String, Movie> ITEM_MAP = new HashMap<String, Movie>();
     */
//    private static void addItem(Movie item) {
//        results.add(item);
//        //ITEM_MAP.put(item.id, item);
//    }



    /**
     * A dummy item representing a piece of content.
     */
    public static class Movie implements Parcelable {

        public Movie() {
            this.favurite=false;
        }

        protected Long id;
        protected String title;
        protected String overview;
        @SerializedName("poster_path")
        protected String posterPath;

        @SerializedName("release_date")
        String releaseDate;

        @SerializedName("vote_average")
        String voteAverage;


        boolean favurite;

        public boolean isFavurite() {
            return favurite;
        }

        public void setFavurite(boolean favurite) {
            this.favurite = favurite;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }


        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getVoteAverage() {
            return voteAverage;
        }

        public void setVoteAverage(String voteAverage) {
            this.voteAverage = voteAverage;
        }

        /**
         * Describe the kinds of special objects contained in this Parcelable's
         * marshalled representation.
         *
         * @return a bitmask indicating the set of special object types marshalled
         * by the Parcelable.
         */
        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * Flatten this object in to a Parcel.
         *
         * @param dest  The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written.
         *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(id);
            dest.writeString(title);
            dest.writeString(overview);
            dest.writeString(posterPath);
            dest.writeString(voteAverage);
            dest.writeString(releaseDate);

        }

        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
            public Movie createFromParcel(Parcel in) {
                return new Movie(in);
            }
            public Movie[] newArray(int size) {
                return new Movie[size];
            }
        };
        private Movie(Parcel in) {

            setId(in.readLong());
            setTitle(in.readString());
            setOverview(in.readString());
            setPosterPath(in.readString());
            setVoteAverage(in.readString());
            setReleaseDate(in.readString());
        }
    }
}
