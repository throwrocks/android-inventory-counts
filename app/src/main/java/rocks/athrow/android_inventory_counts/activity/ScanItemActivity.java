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
import rocks.athrow.android_inventory_counts.data.Item;
import rocks.athrow.android_inventory_counts.data.RealmQueries;
import rocks.athrow.android_inventory_counts.util.Utilities;

import static rocks.athrow.android_inventory_counts.data.Constants.COUNT_TYPE_DISPLAY;
import static rocks.athrow.android_inventory_counts.data.Constants.DESCRIPTION;
import static rocks.athrow.android_inventory_counts.data.Constants.EXPIRATION_DATE;
import static rocks.athrow.android_inventory_counts.data.Constants.ITEM;
import static rocks.athrow.android_inventory_counts.data.Constants.LOCATION;
import static rocks.athrow.android_inventory_counts.data.Constants.PACK_SIZE;
import static rocks.athrow.android_inventory_counts.data.Constants.RECEIVED_DATE;
import static rocks.athrow.android_inventory_counts.data.Constants.SKU;
import static rocks.athrow.android_inventory_counts.data.Constants.TAG_NUMBER;

public class ScanItemActivity extends BaseActivity {
    private static ScanItemActivity instance;
    String location;
    String countTypeDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_item);
        instance = this;
        Intent intent = getIntent();
        location = intent.getStringExtra(LOCATION);
        countTypeDisplay = intent.getStringExtra(COUNT_TYPE_DISPLAY);
        TextView locationView = (TextView) findViewById(R.id.location);
        TextView countTypeView = (TextView) findViewById(R.id.count_type);
        locationView.setText(location);
        countTypeView.setText(countTypeDisplay);
    }
    public static ScanItemActivity getInstance(){
        return instance;
    }
    public void scanItem(View view){
        initiateScan(ITEM);
    }

    public void processItem(String contents) {
        Context context = getApplicationContext();
        Resources res = getResources();
        int toastLength = Toast.LENGTH_SHORT;
        RealmResults<Item> items = RealmQueries.getItemByTagNumber(context, contents);
        if (items.size() > 0) {
            Item record = items.get(0);
            String tagNumber = record.getTagNumber();
            String sku = Integer.toString(record.getSKU());
            String description = record.getDescription();
            String packSize = record.getPackSize();
            String receivedDate = record.getReceivedDate();
            String expirationDate = record.getExpirationDate();
            Intent intent = new Intent(this, AddCountsActivity.class);
            intent.putExtra(COUNT_TYPE_DISPLAY, countTypeDisplay);
            intent.putExtra(LOCATION, location);
            intent.putExtra(TAG_NUMBER, tagNumber);
            intent.putExtra(SKU, sku);
            intent.putExtra(DESCRIPTION, description);
            intent.putExtra(PACK_SIZE, packSize);
            intent.putExtra(RECEIVED_DATE, receivedDate);
            intent.putExtra(EXPIRATION_DATE, expirationDate);
            startActivity(intent);
        } else {
            Utilities.showToast(context, res.getString(R.string.error_item_not_found), toastLength);
        }
    }

}
