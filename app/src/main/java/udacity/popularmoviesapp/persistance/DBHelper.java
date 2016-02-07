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
    public static final String reasonsTable = "reasonsTable";
    public static final String reasonName = "name";

    public static final String createreasons = "create table " + reasonsTable + " ( "+ reasonName + " text);";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createreasons);
        
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Colour')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Comparison With Compititors')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Confused')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Demand For More Products')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Durability')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Just Inquiry')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Need Time To Think')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Price')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Quality')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Size')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Urgency')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Want To Meet Owner')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Weight')");
        db.execSQL("insert into "+reasonsTable+" ("+reasonName+") VALUES ('Will Come Back')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
