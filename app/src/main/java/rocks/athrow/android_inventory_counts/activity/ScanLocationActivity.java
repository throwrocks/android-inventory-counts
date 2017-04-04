package rocks.athrow.android_inventory_counts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import rocks.athrow.android_inventory_counts.R;

public class ScanLocationActivity extends BaseActivity {
    int countType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_location);
        Intent intent = getIntent();
        countType = intent.getIntExtra("count_type",0);
        setCountTypeView(countType);
    }

    public void scanLocation(View view){
        initiateScan("location");
    }
}
