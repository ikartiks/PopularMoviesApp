package udacity.popularmoviesapp.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class Constants {

    static ApiCalls apiCalls;

    public static final String baseUrl="http://api.themoviedb.org/3/";
    public static final String apiKey="8b5b7707072ff65312e97b55ca01cbdf";
    public static final String imageBaseUrl="http://image.tmdb.org/t/p/w185/";


    public static ApiCalls getRetrofitInstance(){

        if(apiCalls!=null)
            return apiCalls;

        Gson gson = new GsonBuilder()
                //.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                // Register an adapter to manage the date types as long values
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                })

                .create();




        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiCalls=retrofit.create(ApiCalls.class);
        return apiCalls;
    }
}
