package udacity.popularmoviesapp.pojos;

import java.util.List;

/**
 * Created by kartikshah on 21/02/16.
 */
public class TrailerWrapper {

    long id ;
    List<Trailer> results;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

    public static class Trailer /* implements Parcelable */ {
        String key;
        String name;
        String site;
        String type;
        long size;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getThumbnailUrl(){
            return "http://img.youtube.com/vi/"+this.key+"/sddefault.jpg";
        }

        public String getVideoUrl(){
            return "http://www.youtube.com/watch?v="+this.key;
        }

    }
}
