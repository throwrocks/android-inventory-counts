package rocks.athrow.android_inventory_counts.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.RealmResults;
import rocks.athrow.android_inventory_counts.R;
import rocks.athrow.android_inventory_counts.data.Location;
import rocks.athrow.android_inventory_counts.data.RealmQueries;
import rocks.athrow.android_inventory_counts.util.Utilities;

import static rocks.athrow.android_inventory_counts.data.Constants.COUNT_TYPE;
import static rocks.athrow.android_inventory_counts.data.Constants.COUNT_TYPE_DISPLAY;
import static rocks.athrow.android_inventory_counts.data.Constants.EMPLOYEE_NAME;
import static rocks.athrow.android_inventory_counts.data.Constants.EMPLOYEE_NUMBER;
import static rocks.athrow.android_inventory_counts.data.Constants.LOCATION;

public class ScanLocationActivity extends BaseActivity {
    private static ScanLocationActivity instance;
    private int countType;
    private String countTypeDisplay;
    private int employeeNumber;
    private String employeeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_location);
        instance = this;
        Intent intent = getIntent();
        employeeNumber = Integer.parseInt(intent.getStringExtra(EMPLOYEE_NUMBER));
        employeeName = intent.getStringExtra(EMPLOYEE_NAME);
        countType = Integer.parseInt(intent.getStringExtra(COUNT_TYPE));
        countTypeDisplay = intent.getStringExtra(COUNT_TYPE_DISPLAY);
        TextView countTypeView = (TextView) findViewById(R.id.count_type);
        countTypeView.setText(countTypeDisplay);
    }

    public static ScanLocationActivity getInstance(){
        return instance;
    }

    public void scanLocation(View view){
        initiateScan(LOCATION);
    }

    /**
     * scanLocation
     *
     * @param contents the scan or intent contents
     */
    public void processLocation(String contents) {
        Context context = getApplicationContext();
        Resources res = getResources();
        int toastLength = Toast.LENGTH_SHORT;
        RealmResults<Location> locations;
        locations = RealmQueries.getLocationByBarcode(context, contents);
        if (locations != null && locations.size() > 0) {
            Intent intent = new Intent(this, ScanItemActivity.class);
            intent.putExtra(EMPLOYEE_NUMBER, employeeNumber);
            intent.putExtra(EMPLOYEE_NAME, employeeName);
            intent.putExtra(COUNT_TYPE, countType);
            intent.putExtra(COUNT_TYPE_DISPLAY, countTypeDisplay);
            intent.putExtra(LOCATION, locations.get(0).getLocation());
            this.startActivity(intent);
        } else {
            Utilities.showToast(context, res.getString(R.string.error_location_not_found), toastLength);
        }
    }
}
