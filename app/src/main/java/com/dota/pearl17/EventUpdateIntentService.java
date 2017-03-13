package com.dota.pearl17;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class EventUpdateIntentService extends IntentService {

    public EventUpdateIntentService() {
        super("EventUpdateIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        EventDatabaseManager eventDB = new EventDatabaseManager(this);
        eventDB.updateEvents();

    }
}
