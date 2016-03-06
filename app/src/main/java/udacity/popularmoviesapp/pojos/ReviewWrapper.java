package udacity.popularmoviesapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by kartikshah on 21/02/16.
 */
public class ReviewWrapper {

    int page;
    List<Review> results ;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public static class Review implements Parcelable{

        String  id;
        String author;
        String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
            dest.writeString(getId());
            dest.writeString(getAuthor());
            dest.writeString(getContent());
        }

        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
            public Review createFromParcel(Parcel in) {
                return new Review(in);
            }
            public Review[] newArray(int size) {
                return new Review[size];
            }
        };
        private Review(Parcel in) {

            setId(in.readString());
            setAuthor(in.readString());
            setContent(in.readString());
        }
    }
}
