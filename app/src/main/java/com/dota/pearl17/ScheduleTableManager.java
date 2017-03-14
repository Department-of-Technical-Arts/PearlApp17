package com.dota.pearl17;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static com.dota.pearl17.R.id.venue;

/**
 * Created by SHREEDA on 07-03-2017.
 */

public class ScheduleTableManager {

    public static final String KEY_ID = "ID";
    public static final String KEY_EVENT_ID = "EventID";
    public static final String KEY_EVENT_ROUND = "tag";
    public static final String KEY_EVENT_NAME = "Name";
    public static final String KEY_START_TIME = "StartTime";
    public static final String KEY_VENUE = "Venue";

    public static final String TAG = "ScheduleTable";

    private static final String DATABASE_TABLE = "ScheduleTable";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ScheduleDatabase";
    private Context context;
    private DBHelper ourHelper;
    private SQLiteDatabase ourDatabase;

    public ScheduleTableManager(Context cl) {
        context = cl;


        addSchedule(2, "final","SCRIBBLER", 2345L, "F102" );


    }

    public ScheduleTableManager open() {
        ourHelper = new DBHelper(context);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
        ourDatabase.close();
    }

    public long addEntry1(ScheduleSet newschedule) {
        int id = 0;
        long success = -1;

        ContentValues cv = new ContentValues();

        open();

        cv.put(KEY_EVENT_NAME, newschedule.getName());
        cv.put(KEY_EVENT_ID, newschedule.getEvent_id());
        cv.put(KEY_EVENT_ROUND, newschedule.getRound());
        cv.put(KEY_START_TIME, newschedule.getTime());
        cv.put(KEY_VENUE, newschedule.getVenue());


        try {
            success = ourDatabase.insertOrThrow(DATABASE_TABLE, null, cv);
//            Log.d(TAG, "addEntry: added " + success);

        } catch (SQLiteConstraintException e) {
            success = ourDatabase.update(DATABASE_TABLE, cv, KEY_ID + "=" + id, null);
//            Log.d(TAG, ourDatabase.toString() + success);
        }


        close();
        return success;

    }

    public long addSchedule(int event_id,
                         String event_round,
                         String name,
                         Long start_time,
                         String venue) {
        int id=0;
        long success = -1;
        open();

        ContentValues cv = new ContentValues();

        cv.put(KEY_EVENT_NAME, name);
        cv.put(KEY_EVENT_ID, event_id);
        cv.put(KEY_EVENT_ROUND, event_round);
        cv.put(KEY_START_TIME, start_time);
        cv.put(KEY_VENUE, venue);


        try {
            success = ourDatabase.insertOrThrow(DATABASE_TABLE, null, cv);
        }catch (SQLiteConstraintException e){
            success=ourDatabase.update(DATABASE_TABLE,cv,KEY_ID+"="+id,null);
        }

        close();
        return success;
    }


    public ArrayList<ScheduleSet> getSchedule(long time) {
        open();
        Log.e("getSchedule","inside");
        ArrayList<ScheduleSet> sets = new ArrayList<>();
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + KEY_START_TIME + "='" + time + "'", null);
        if (cursor.moveToFirst()) {
            do {
                ScheduleSet set = new ScheduleSet(cursor.getString(3), cursor.getString(2), cursor.getString(5), cursor.getLong(4), cursor.getInt(1));
                sets.add(set);
                Log.e("set","added");
            } while (cursor.moveToNext());
        }
        Log.e("set", sets.toString());
        cursor.close();
        close();
        return sets;
    }

    public ArrayList<ScheduleSet> getSchedule(int event_id) {
        open();
        ArrayList<ScheduleSet> sets = new ArrayList<>();
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + KEY_EVENT_ID + "=" + event_id, null);
        if (cursor.moveToFirst()) {
            do {
                ScheduleSet set = new ScheduleSet(cursor.getString(3), cursor.getString(2), cursor.getString(5), cursor.getLong(4), cursor.getInt(1));
                sets.add(set);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return sets;
    }

//    public ArrayList<Long> getDistinctTime(int day) {
//
//        Calendar start = Calendar.getInstance(), end = Calendar.getInstance();
//        start.setTimeZone(TimeZone.getTimeZone("GMT"));
//        end.setTimeZone(TimeZone.getTimeZone("GMT"));
//        start.set(2017, Calendar.MARCH, 17 + day, 0, 0);
//        end.set(2017, Calendar.MARCH, 18 + day, 0, 0);
//        open();
//        ArrayList<Long> times = new ArrayList<>();
//        Cursor cursor = ourDatabase.rawQuery("SELECT DISTINCT " + KEY_START_TIME + " FROM " + DATABASE_TABLE +
//                " WHERE CAST(" + KEY_START_TIME + " AS INTEGER) >= " + start.getTimeInMillis() +
//                " AND CAST(" + KEY_START_TIME + " AS INTEGER) < " + end.getTimeInMillis() +
//                " ORDER BY CAST(" + KEY_START_TIME + " AS INTEGER) ", null);
//        if (cursor.moveToFirst()) {
//            do {
//                times.add(cursor.getLong(0));
//            }
//            while (cursor.moveToNext());
//        }
//        cursor.close();
//        close();
//
//        return times;
//    }

    public ArrayList<Long> getDistinctTime(int day) {

        Calendar start = Calendar.getInstance(), end = Calendar.getInstance();
        start.setTimeZone(TimeZone.getTimeZone("GMT"));
        end.setTimeZone(TimeZone.getTimeZone("GMT"));
        start.set(2017, Calendar.MARCH, 17 + day, 0, 0);
        end.set(2017, Calendar.MARCH, 18 + day, 0, 0);
        open();
        Log.e("day", String.valueOf(day));
        ArrayList<Long> times = new ArrayList<>();
        Cursor cursor = ourDatabase.rawQuery("SELECT DISTINCT " + KEY_START_TIME + " FROM " + DATABASE_TABLE +
                " WHERE CAST(" + KEY_START_TIME + " AS INTEGER) >= " + start.getTimeInMillis() +
                " AND CAST(" + KEY_START_TIME + " AS INTEGER) < " + end.getTimeInMillis() +
                " ORDER BY CAST(" + KEY_START_TIME + " AS INTEGER) ", null);
        if (cursor.moveToFirst()) {
            do {
                Log.e("indistincttime", "time");
                times.add(cursor.getLong(0));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        close();

        return times;
    }

    public Cursor getScheduleById(int id) {
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + KEY_EVENT_ID + "=" + id +
                " ORDER BY CAST(" + KEY_START_TIME + " AS INTEGER) ", null);
        close();
        return cursor;
    }

    public void deleteAllEntry() {
        open();
        ourDatabase.delete(DATABASE_TABLE, null, null);
        close();
    }

    public long addEntry(JSONObject jsonObject) throws JSONException {

        ContentValues cv = new ContentValues();

        cv.put(KEY_EVENT_NAME, jsonObject.getString("event"));
        cv.put(KEY_EVENT_ID, jsonObject.getInt("event_id"));
        cv.put(KEY_EVENT_ROUND, jsonObject.getString("round"));
        cv.put(KEY_START_TIME, jsonObject.getLong("time") * 1000);
        cv.put(KEY_VENUE, jsonObject.getString("venue"));
        //cv.put(KEY_ID, jsonObject.getInt("id"));

        open();
        try {
            ourDatabase.insertOrThrow(DATABASE_TABLE, null, cv);
        } catch (SQLiteConstraintException e) {
            cv.remove(KEY_ID);
            ourDatabase.update(DATABASE_TABLE, cv, KEY_ID + "=" + jsonObject.getInt("id"), null);
        }
        close();
        return 1;
    }

    public void deleteEntry(JSONObject jsonObject) throws JSONException {
        open();
        ourDatabase.delete(DATABASE_TABLE, KEY_ID + "=" + jsonObject.getInt("id"), null);
        close();
    }



    public void updateSchedule() {
        Log.e("enter","done");
        deleteAllEntry();
        StringRequest request = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String s) {
                Log.e("enter","done3");

                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getInt("success") == 1) {
//                        Toast.makeText(context, "Event data updated successfully", Toast.LENGTH_SHORT).show();
                        //update all events

                        try {
                            JSONArray array = new JSONObject(s).getJSONArray("data");
                            for (int j = 0; j < array.length(); j++) {
//                                addSchedule(2,"final","shows", 1234545645L , "F102" );
                                JSONObject Object = array.getJSONObject(j);
//                               addEntry1(new ScheduleSet(Object.getString("event"), Object.getString("round"), Object.getString("venue"),
//                                       Object.getLong("time"), Object.getInt("event_id")));
                                addEntry(array.getJSONObject(j));
                                Log.e(String.valueOf(j), "added");
//                                addSchedule(Object.getInt("event_id"),
//                                        Object.getString("round"),
//                                        Object.getString("event"),
//                                        Object.getLong("time") * 1000,
//                                        Object.getString("venue"));

                            }
                            Log.e("values", "set");
//                            Log.v("Events", s);
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(context, "Event data update failed. Please check internet connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
//                        Toast.makeText(context, "Event data update failed. Please check internet connection", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(context, "Event data update failed. Please check internet connection", Toast.LENGTH_SHORT).show();
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
                params.put("tag", "getSchedule");
                //Log.e("Sent", params.toString());
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(request);
    }



    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String query = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_EVENT_ID + " INTEGER NOT NULL, " +
                    KEY_EVENT_ROUND + " INTEGER NOT NULL, " +
                    KEY_EVENT_NAME + " TEXT NOT NULL, " +
                    KEY_START_TIME + " TEXT NOT NULL, " +
                    KEY_VENUE + " TEXT NOT NULL);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

}
