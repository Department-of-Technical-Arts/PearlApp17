package com.dota.pearl17;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by SHREEDA on 13-03-2017.
 */

public class ScheduleUpdateIntentService extends IntentService {

    public ScheduleUpdateIntentService() {
        super("ScheduleUpdateIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("function", "schedule" );
        ScheduleTableManager scheduledatabase = new ScheduleTableManager(this);
        scheduledatabase.updateSchedule();

    }
}
