package com.example.bhavy.lifesaver.sync;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

import android.app.PendingIntent;
import android.content.Context;
import android.os.AsyncTask;

import java.text.ParseException;

/**
 * Created by bhavy on 06-08-2017.
 */

public class waterReminderJob extends com.firebase.jobdispatcher.JobService {
    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mBackgroundTask = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                Context context = waterReminderJob.this;
                try {
                    ReminderTasks.executeTask(context,ReminderTasks.ACTION_NOTIFICATION);
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                /*
                 * Once the AsyncTask is finished, the job is finished. To inform JobManager that
                 * you're done, you call jobFinished with the jobParamters that were passed to your
                 * job and a boolean representing whether the job needs to be rescheduled. This is
                 * usually if something didn't work and you want the job to try running again.
                 */

                jobFinished(job, false);
            }
        };

        mBackgroundTask.execute();
        return true;
    }
    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}
