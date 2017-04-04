package rocks.athrow.android_inventory_counts.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import rocks.athrow.android_inventory_counts.data.SyncDB;

/**
 * Created by joselopez1 on 3/29/2017.
 */

public class SyncDBJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        SynDBAsyncTask synDBAsyncTask = new SynDBAsyncTask();
        synDBAsyncTask.execute(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

    private class SynDBAsyncTask extends AsyncTask<JobParameters, Void, JobParameters[]> {
        SynDBAsyncTask() {
        }

        @Override
        protected JobParameters[] doInBackground(JobParameters... params) {
            Log.e("JobService", "is running");
            Context context = getApplicationContext();
            SyncDB.downloadNewRecords(context);
            return params;
        }

        @Override
        protected void onPostExecute(JobParameters[] result) {
            for (JobParameters params : result) {
                jobFinished(params, true);
            }
        }
    }
}