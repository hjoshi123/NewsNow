package com.hemantjoshi.newsapp.utils;

import android.content.Context;
import androidx.annotation.NonNull;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * @author HemantJ.
 * Utility class for scheduling and setting the service execution
 */

public class ReminderUtility{
    private static final int REMINDER_INTERVAL_MINUTES = 30;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String REMINDER_JOB_TAG = "news_reminder_tag";

    private static boolean sInitialized;

    /**
     * The method schedules reminder in the background taking care of the battery needs
     * @param context to access the service/activity running it
     * synchronized is used so that any thread accessing this method gets the same method and not different
     * instance.
     */
    synchronized public static void scheduleReminder(@NonNull final Context context){
        if(sInitialized)
            return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(NewsReminderJobService.class)
                .setTag(REMINDER_JOB_TAG)
                /*
                 * service to run forever
                 */
                .setLifetime(Lifetime.FOREVER)
                /*
                 * We want these reminders to continuously happen, so we tell this Job to recur.
                 */
                .setRecurring(true)
                /*
                 * We want the reminders to happen every 30 minutes or so. The first argument for
                 * Trigger class's static executionWindow method is the start of the time frame
                 * when the
                 * job should be performed. The second argument is the latest point in time at
                 * which the data should be synced. Please note that this end time is not
                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
                 */
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                /*
                 * replaces the old notification if there
                 */
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintReminderJob);
        sInitialized = true;
    }
}
