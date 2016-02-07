package udacity.popularmoviesapp.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by 1078943 on 12/31/2015.
 */
public abstract class DBHelperBase extends SQLiteOpenHelper {

    protected static final String dbName="ours.db";

    static DBHelperBase mInstance = null;
    public SQLiteDatabase sqLiteDatabase = null;
    public Context context;
    private static JsonUtil jsonUtil;

    protected DBHelperBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, null,version);
        jsonUtil=new JsonUtil();
    }

    private Stack<Long> openCloseStack = new Stack<Long>();

    protected synchronized SQLiteDatabase openDatabse() {

        openCloseStack.push(System.currentTimeMillis());
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            // LoggerGeneral.e("opening new db");
            sqLiteDatabase = getWritableDatabase();
        }
        return sqLiteDatabase;
    }

    protected synchronized void closeDatabse() {

        openCloseStack.pop();
        if (sqLiteDatabase != null && openCloseStack.empty()) {
            // LoggerGeneral.e("closing the db");
            sqLiteDatabase.close();
        }
    }

    public Object genericRead(Class aClass, String tableName, String whereClause, String[] whereArgs, String groupBy,
                              String having, String orderBy){

        openDatabse();
        Cursor c    =   sqLiteDatabase.query(tableName, null, whereClause, whereArgs, groupBy, having, orderBy);
        String []cols=c.getColumnNames();
        int noOfRows=c.getCount();

        if(noOfRows==0){
            c.close();
            closeDatabse();
            return null;
        }else if (noOfRows==1){

            c.moveToNext();
            JSONObject jsonObject=new JSONObject();
            for (int i=0;i<cols.length;i++){
                try {
                    jsonObject.put(cols[i], c.getString(i)) ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            c.close();
            closeDatabse();
            return jsonUtil.writeJsonToJavaObject(jsonObject.toString(), aClass);

        }else{

            ArrayList list=new ArrayList();
            while (c.moveToNext()){

                JSONObject jsonObject=new JSONObject();
                for (int i=0;i<cols.length;i++){
                    try {
                        jsonObject.put(cols[i],c.getString(i)) ;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Object object= jsonUtil.writeJsonToJavaObject(jsonObject.toString(), aClass);
                list.add(object);
            }
            c.close();
            closeDatabse();
            return list;
        }
    }

    public void insertGeneric(Object object,String tableName) {

        openDatabse();
        ContentValues contentValues = new ContentValues();
        try {

            JSONObject jsonObject=new JSONObject(jsonUtil.writeJavaObjectToString(object));
            for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
                String key = (String) iterator.next();
                contentValues.put(key,jsonObject.get(key)+"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sqLiteDatabase.insert(tableName, null, contentValues);
        closeDatabse();
    }

    public void updateGeneric(Object object,String tableName,String whereClause,String[] whereArgs) {

        openDatabse();
        ContentValues contentValues = new ContentValues();
        try {
            JSONObject jsonObject=new JSONObject(jsonUtil.writeJavaObjectToString(object));
            for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
                String key = (String) iterator.next();
                contentValues.put(key,jsonObject.get(key)+"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sqLiteDatabase.update(tableName, contentValues, whereClause, whereArgs);
        closeDatabse();
    }

    public void deleteGeneric(String tableName,String whereClause, String[] whereArgs){
        openDatabse();
        sqLiteDatabase.delete(tableName,whereClause,whereArgs);
        closeDatabse();
    }

}
