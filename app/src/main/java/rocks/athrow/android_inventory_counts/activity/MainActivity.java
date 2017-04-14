package rocks.athrow.android_inventory_counts.activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import rocks.athrow.android_inventory_counts.R;
import rocks.athrow.android_inventory_counts.service.SyncDBJobService;

import static rocks.athrow.android_inventory_counts.data.Constants.COUNT_TYPE;
import static rocks.athrow.android_inventory_counts.data.Constants.JOB_SUCCESS;

public class MainActivity extends AppCompatActivity {
    private static final int JOB_ID = 1001;
    private static final long REFRESH_INTERVAL  = 5 * 1000;
    private Button count1Button;
    private Button count2Button;
    private Button count3Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
        count1Button = (Button) findViewById(R.id.count1_button);
        count2Button = (Button) findViewById(R.id.count2_button);
        count3Button = (Button) findViewById(R.id.count3_button);
        count1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity(1);
            }
        });
        count2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity(2);
            }
        });
        count3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity(3);
            }
        });
        scheduleSyncDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void scheduleSyncDB() {
        ComponentName serviceName = new ComponentName(getPackageName(), SyncDBJobService.class.getName());
        // http://stackoverflow.com/questions/38344220/job-scheduler-not-running-on-android-n
        JobInfo jobInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobInfo = new JobInfo.Builder(JOB_ID, serviceName)
                    .setMinimumLatency(REFRESH_INTERVAL).build();
        } else {
            jobInfo = new JobInfo.Builder(JOB_ID, serviceName)
                    .setPeriodic(REFRESH_INTERVAL).build();
        }
        JobScheduler scheduler = (JobScheduler) getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) Log.e(MainActivity.class.getName(), JOB_SUCCESS);
    }

    private void startLoginActivity(int countType){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra(COUNT_TYPE, countType);
        startActivity(intent);
    }

}
