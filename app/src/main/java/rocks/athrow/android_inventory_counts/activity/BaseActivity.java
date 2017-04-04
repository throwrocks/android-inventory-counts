package rocks.athrow.android_inventory_counts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import rocks.athrow.android_inventory_counts.R;

/**
 * BaseActivity
 * Created by joselopez1 on 4/4/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    TextView countTypeView;
    boolean isScanning;
    void setCountTypeView(int countType){
        if ( countType == 0 ){
            finish();
        }
        String countTypeDisplay = null;
        switch (countType){
            case 1:
                countTypeDisplay = "1st";
                break;
            case 2:
                countTypeDisplay = "2nd";
                break;
            case 3:
                countTypeDisplay = "3rd";
                break;
        }
        countTypeDisplay = countTypeDisplay + " Count";
        countTypeView = (TextView) findViewById(R.id.count_type);
        countTypeView.setText(countTypeDisplay);
    }

    void showToast(String message, int duration) {
        Toast toast = Toast.makeText(getApplicationContext(), message, duration);
        toast.show();
    }

    /**
     * initiateScan
     * Initiates the barcode scanner app
     */
    void initiateScan(String scanType) {
        if (scanType != null) {
            isScanning = true;
            rocks.athrow.android_inventory_counts.zxing.IntentIntegrator integrator = new rocks.athrow.android_inventory_counts.zxing.IntentIntegrator(this);
            integrator.initiateScan();
        }
    }
}
