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
        public static final String EVENT_CLUB = "CLUB";
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
            addEvent(new Event(1,"vividHz", "test1", "anuj", "anuj@movie.com", "1234567890", "VFX", "F103", "1234" ));
            addEvent(new Event(2,"Kaleidoscope", "test2", "anuj", "anuj@movie.com", "1234567890", "VFX", "F104", "1235" ));
            addEvent(new Event(3,"Spoiler Alert", "test3", "anuj", "anuj@movie.com", "1234567890", "VFX", "F105", "1236" ));
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

    public long addEvent(Event newEvent){

        long success = -1;

        ContentValues cv = new ContentValues();

        cv.put(EVENT_ID, newEvent.getId());
        cv.put(EVENT_NAME, newEvent.getName());
        cv.put(EVENT_CLUB, newEvent.getClub());
        cv.put(EVENT_DESC, newEvent.getDesc());
        cv.put(EVENT_LOCATION, newEvent.getLocation());
        cv.put(CONTACT_MAIL, newEvent.getContact_mail());
        cv.put(CONTACT_NAME, newEvent.getContact_name());
        cv.put(CONTACT_PHONE, newEvent.getContact_phone());
        cv.put(EVENT_TIME, newEvent.getTime());

        open();
        try {
            success = ourDatabase.insertOrThrow(DATABASE_TABLE, null, cv);
        } catch (SQLiteConstraintException e) {
            //repeat hora hai. lite.
        }
        close();
        return success;

    }

    public Event getEvent(int eventId){

        open();
        Cursor c;
        Event event;

        c = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " + EVENT_ID + " EQUALS " + eventId, null);

        if(c.moveToFirst()) {
            event = new Event(c.getInt(c.getColumnIndex(EVENT_ID)),
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

        c.close();
        close();

        return new Event();
    }

    public Event getEvent(String eventName){

        open();
        Cursor c;
        Event event;

        c = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " + EVENT_NAME + " LIKE \"" + eventName + "\"", null);

        if(c.moveToFirst()) {
            event = new Event(c.getInt(c.getColumnIndex(EVENT_ID)),
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

        c.close();
        close();

        return new Event();
    }

    public ArrayList<Event> getClubEvents(String clubName){

        open();
        Cursor c;
        ArrayList<Event> events = new ArrayList<>();

        c = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " + EVENT_CLUB + " LIKE \"" + clubName + "\"", null);

        if(c.moveToFirst()) {
            do {
                events.add(new Event(c.getInt(c.getColumnIndex(EVENT_ID)),
                        c.getString(c.getColumnIndex(EVENT_NAME)),
                        c.getString(c.getColumnIndex(EVENT_DESC)),
                        c.getString(c.getColumnIndex(CONTACT_NAME)),
                        c.getString(c.getColumnIndex(CONTACT_MAIL)),
                        c.getString(c.getColumnIndex(CONTACT_PHONE)),
                        c.getString(c.getColumnIndex(EVENT_CLUB)),
                        c.getString(c.getColumnIndex(EVENT_LOCATION)),
                        c.getString(c.getColumnIndex(EVENT_TIME))));
            }while(c.moveToNext());
            c.close();
            close();
            return events;
        }

        c.close();
        close();

        return null;


    }



        private static class DBHelper extends SQLiteOpenHelper {


            public DBHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {

                String query = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " ( " +
                        KEY + " INTEGER, " +
                        EVENT_NAME + " TEXT NOT NULL, " +
                        EVENT_ID + " INTEGER NOT NULL PRIMARY KEY, " +//1, 2, etc event associated id
                        EVENT_CLUB + " TEXT, " +
                        EVENT_DESC + " TEXT, " +
                        EVENT_LOCATION + " TEXT, " +
                        EVENT_TIME + " TEXT, " +
                        CONTACT_NAME + " TEXT, " +
                        CONTACT_PHONE + " TEXT, " +
                        CONTACT_MAIL + " TEXT );";

                db.execSQL(query);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
                onCreate(db);
            }
        }
}
