package com.dota.pearl17;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ADMIN on 7.3.17.
 */

public class EventDatabaseManager {

        public static final String KEY = "_id";     //pearl Id PE0001
        public static final String EVENT_ID = "EVENT_ID";
        public static final String EVENT_NAME = "EVENT_NAME";           //name of the guy fetched from server
        public static final String CONTACT_NAME = "CONTACT_NAME"; //1 if done, 0 if not done
        public static final String CONTACT_PHONE = "PHONE";
        public static final String CONTACT_MAIL = "MAIL";
        public static final String EVENT_CLUB = "MAIL";
        public static final String EVENT_LOCATION = "LOCATION";
        public static final String EVENT_DESC = "DESC";
        public static final String EVENT_TIME = "TIME";



    public static final String TAG = "Event Manager";

        private static final String DATABASE_TABLE = "Event_Manager";
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "Event_Manager_Database";
        private Context context;
        private DBHelper ourHelper;
        private SQLiteDatabase ourDatabase;

        public EventDatabaseManager(Context c) {
            context = c;

        }

        public EventDatabaseManager open() {
            ourHelper = new DBHelper(context);
            ourDatabase = ourHelper.getWritableDatabase();
            return this;
        }

        public void close() {
            ourHelper.close();
            ourDatabase.close();
        }

    public Event getEvent(int eventId){

        open();
        Cursor c;

        c = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " + EVENT_ID + " LIKE " + eventId, null);

        c.moveToFirst();
        Event event = new Event(c.getInt(c.getColumnIndex(EVENT_ID)),
                c.getString(c.getColumnIndex(EVENT_NAME)),
                c.getString(c.getColumnIndex(EVENT_DESC)),
                c.getString(c.getColumnIndex(CONTACT_NAME)),
                c.getString(c.getColumnIndex(CONTACT_MAIL)),
                c.getString(c.getColumnIndex(CONTACT_PHONE)),
                c.getString(c.getColumnIndex(EVENT_CLUB)),
                c.getString(c.getColumnIndex(EVENT_LOCATION)),
                c.getString(c.getColumnIndex(EVENT_TIME)));
        c.close();
        close();

        return event;


    }



        private static class DBHelper extends SQLiteOpenHelper {


            public DBHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {

                String query = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" +
                        KEY + " INTEGER AUTOINCREMENT, " +
                        EVENT_NAME + " TEXT NOT NULL, " +
                        EVENT_ID + " INTEGER NOT NULL PRIMARY KEY, " +//1, 2, etc event associated id
                        EVENT_CLUB + " TEXT " +
                        EVENT_DESC + " TEXT " +
                        EVENT_LOCATION + " TEXT " +
                        EVENT_TIME + " TEXT " +
                        CONTACT_NAME + " TEXT, " +
                        CONTACT_PHONE + " TEXT, " +
                        CONTACT_MAIL + " TEXT, " +
                        "UNIQUE(" + EVENT_ID + "));";

                db.execSQL(query);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
                onCreate(db);
            }
        }
}
