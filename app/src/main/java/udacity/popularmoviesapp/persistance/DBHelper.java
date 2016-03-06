package udacity.popularmoviesapp.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends DBHelperBase {

    public static final int DATABASE_VERSION = 1;

    protected DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBHelper getInstance(Context context) {

        /**
         * use the application context as suggested by CommonsWare. this will
         * ensure that you dont accidentally leak an Activitys context (see this
         * article for more information:
         * http://developer.android.com/resources/articles
         * /avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DBHelper(context,dbName,null,DATABASE_VERSION);
        }
        return (DBHelper)mInstance;
    }

    //reasons table
    public static final String moviesTable = "movie";
    public static final String title = "title";
    public static final String  id ="id";
    public static final String overview="overview";
    public static final String posterPath="posterPath";
    public static final String releaseDate="releaseDate";
    public static final String voteAverage="voteAverage";
    public static final String isFavurite="favurite";

    public static final String createMovies = "create table " + moviesTable + " ( "+ id + " int, "+title+" text, "+overview+" text," +
              posterPath+" text, "+releaseDate+" text, "+voteAverage+" text, "+isFavurite+" text);";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createMovies);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
