package com.sjsu.cmpe295b.sahayata;

/**
 * Created by Keya on 3/27/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Restaurant.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE FAV (NAME TEXT PRIMARY KEY, RESULT TEXT)";
    private static final String  SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS FAV";

    public DBHelper(Context context){
        super(context,DB_NAME, null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public boolean addDatabase (String name, String result){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("RESULT", result);
        db.insert("FAV", null, values);
        return true;
    }

    public String getData (String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT RESULT FROM FAV WHERE NAME =" + name, null);
        cursor.moveToFirst();
        String ret = cursor.getString(1);
        cursor.close();
        return ret;
    }

    public Cursor getAllData () {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM FAV", null);
        cursor.moveToFirst();
        return cursor;
    }

    public int deleteData (String name){
        String[] ar = {name};
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("FAV", "NAME = ?", ar);
    }

    public int getCount () {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM FAV", null);
        while (cursor.moveToNext()) {
            count++;
        }
        cursor.close();
        Log.d("Count", "" + count);
        return count;
    }

}

