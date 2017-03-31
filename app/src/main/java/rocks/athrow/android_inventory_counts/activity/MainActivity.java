package rocks.athrow.android_inventory_counts.activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import rocks.athrow.android_inventory_counts.R;
import rocks.athrow.android_inventory_counts.service.SyncDBJobService;

public class MainActivity extends AppCompatActivity {
    Button count1Button;
    Button count2Button;
    Button count3Button;
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
    }

    @Override
    protected void onResume() {
        scheduleSyncDB();
        /*if (isRegistered()) {
            scheduleSyncDB();
        } else {
            openRegistration();
        }*/
        super.onResume();
    }

    private void scheduleSyncDB() {
        ComponentName serviceName = new ComponentName(this, SyncDBJobService.class);
        JobInfo.Builder jobInfo = new JobInfo.Builder(1, serviceName);
        jobInfo.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        jobInfo.setPeriodic(10000);
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(jobInfo.build());
    }

    private void startLoginActivity(int countType){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("countType", countType);
        startActivity(intent);
    }

    /*private boolean isRegistered() {
        PreferencesHelper prefs = new PreferencesHelper(getApplicationContext());
        String employeeNumber = prefs.loadString(SETTINGS_EMPLOYEE_NUMBER, EMPTY);
        String employeeName = prefs.loadString(SETTINGS_EMPLOYEE_NAME, EMPTY);
        return !employeeNumber.isEmpty() && !employeeName.isEmpty();
    }*/

}
