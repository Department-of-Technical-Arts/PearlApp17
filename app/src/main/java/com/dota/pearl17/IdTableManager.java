package com.dota.pearl17;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 7.3.17.
 */

public class IdTableManager {

    public static final String EVENT_ID = "EVENT_ID";
    public static final String EVENT_NAME = "EVENT_NAME";

    private static final String DATABASE_TABLE = "ID_Manager";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Id_Manager_Database";
    private Context context;
    private DBHelper ourHelper;
    private SQLiteDatabase ourDatabase;

    public IdTableManager(Context c) {
        context = c;
    }

    public IdTableManager open() {
        ourHelper = new DBHelper(context);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
        ourDatabase.close();
    }

    public long addEntry(int eventId, String eventName){

        long success = -1;

        ContentValues cv = new ContentValues();

        cv.put(EVENT_ID, eventId);
        cv.put(EVENT_NAME, eventName);

        open();
        try {
            success = ourDatabase.insertOrThrow(DATABASE_TABLE, null, cv);
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        }
        close();
        Log.v("TAG",success + "");
        open();
        Cursor c;

        c = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE , null);

        c.moveToFirst();
        ArrayList<String> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                String event = c.getString(1);
                list.add(event);
            } while (c.moveToNext());
        }
        c.close();
        close();

        Log.v("TAG",list.toString());
        return success;
    }



    public ArrayList<Integer> getList(){

        ArrayList<Integer> eventList = new ArrayList<Integer>();
        open();
        Cursor c;

        c = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE , null);

        c.moveToFirst();
        ArrayList<Integer> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Integer event = new Integer(c.getInt(0));
                list.add(event);
            } while (c.moveToNext());
        }
        c.close();
        close();

        return list;
    }

    public ArrayList<String> getEventNames(){

        ArrayList<String> eventList = new ArrayList<>();
        open();
        Cursor c;

        c = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE , null);

        c.moveToFirst();
        if (c.moveToFirst()) {
            do {
                eventList.add(c.getString(1));
            } while (c.moveToNext());
        }
        c.close();
        close();

        Log.v("TAG",eventList.toString());

        return eventList;
    }

    private static class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String query = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" +
                    EVENT_ID + " INTEGER NOT NULL PRIMARY KEY, " +
                    EVENT_NAME + " TEXT );";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

}
