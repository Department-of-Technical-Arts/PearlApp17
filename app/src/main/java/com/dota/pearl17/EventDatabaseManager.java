package com.dota.pearl17;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventDatabaseManager {

        public static final String KEY = "_id";
        public static final String EVENT_ID = "EVENT_ID";
        public static final String EVENT_NAME = "EVENT_NAME";
        public static final String EVENT_CLUB = "CLUB";
        public static final String EVENT_DESC = "DESC";
        public static final String EVENT_RULES = "RULES";




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

    public long addEvent(Event newEvent){

        long success = -1;

        ContentValues cv = new ContentValues();

        cv.put(EVENT_ID, newEvent.getId());
        cv.put(EVENT_NAME, newEvent.getName());
        cv.put(EVENT_CLUB, newEvent.getClub());
        cv.put(EVENT_DESC, newEvent.getDesc());


        open();
        try {
            success = ourDatabase.insertOrThrow(DATABASE_TABLE, null, cv);
        } catch (SQLiteConstraintException e) {
            //success = ourDatabase.update(DATABASE_TABLE, cv, EVENT_ID + " EQUALS " + newEvent.getId(),null);
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
                    c.getString(c.getColumnIndex(EVENT_CLUB)),
                    c.getString(c.getColumnIndex(EVENT_RULES)));

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
                    c.getString(c.getColumnIndex(EVENT_CLUB)),
                    c.getString(5));

            c.close();
            close();
            return event;
        }

        c.close();
        close();

        return new Event(0,"0","0","0","0");
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
                        c.getString(c.getColumnIndex(EVENT_CLUB)),
                        c.getString(5)));
            }while(c.moveToNext());
            c.close();
            close();
            return events;
        }

        c.close();
        close();
        events.add(new Event(0,"0","0","0","0"));
        return events;
    }

    public void updateEvents(){

        StringRequest request = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject object = new JSONObject(s);
                    if(object.getInt("success")==1){
                        Toast.makeText(context,"Updated successfully",Toast.LENGTH_SHORT).show();
                        //update all events

                        try {
                            JSONArray array = new JSONObject(s).getJSONArray("data");
                            for (int j = 0; j < array.length(); j++) {
                                JSONObject Object = array.getJSONObject(j);
                                addEvent(new Event(Object.getInt("event_id"), Object.getString("name"), Object.getString("description"),
                                         Object.getString("club"), Object.getString("pdf")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(context,"Update failed",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //internet problem, cannot upload Group member
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "getEvents");
                //Log.e("Sent", params.toString());
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(request);
    }

    public void printEvents(){
        open();

        Cursor c;
        c = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE , null);

        if(c.moveToFirst()) {
            do {
                Event event =new Event(c.getInt(c.getColumnIndex(EVENT_ID)),
                        c.getString(c.getColumnIndex(EVENT_NAME)),
                        c.getString(c.getColumnIndex(EVENT_DESC)),
                        c.getString(c.getColumnIndex(EVENT_CLUB)),
                        c.getString(5));
                Log.v("Events",event.getClub());
            }while(c.moveToNext());
        }

        c.close();
        close();
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
                        EVENT_RULES + " TEXT);";

                db.execSQL(query);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
                onCreate(db);
            }
        }
}
