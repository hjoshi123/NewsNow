package com.hemantjoshi.newsapp.newsmain;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author HemantJ .
 */

public class NewsIntentService extends IntentService {

    /*
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public NewsIntentService() {
        super("NewsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ReminderTasks.executeTasks(this, action);
    }
}
