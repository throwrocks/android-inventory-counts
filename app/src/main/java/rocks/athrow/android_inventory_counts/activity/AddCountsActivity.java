package rocks.athrow.android_inventory_counts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import rocks.athrow.android_inventory_counts.R;

import static rocks.athrow.android_inventory_counts.data.Constants.COUNT_TYPE_DISPLAY;
import static rocks.athrow.android_inventory_counts.data.Constants.DESCRIPTION;
import static rocks.athrow.android_inventory_counts.data.Constants.EXPIRATION_DATE;
import static rocks.athrow.android_inventory_counts.data.Constants.LOCATION;
import static rocks.athrow.android_inventory_counts.data.Constants.PACK_SIZE;
import static rocks.athrow.android_inventory_counts.data.Constants.RECEIVED_DATE;
import static rocks.athrow.android_inventory_counts.data.Constants.SKU;
import static rocks.athrow.android_inventory_counts.data.Constants.TAG_NUMBER;

/**
 * Created by joselopez1 on 4/13/2017.
 */

public class AddCountsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_count);
        Intent intent = getIntent();
        String location = intent.getStringExtra(LOCATION);
        String countTypeDisplay = intent.getStringExtra(COUNT_TYPE_DISPLAY);
        String tagNumber = intent.getStringExtra(TAG_NUMBER);
        String sku = intent.getStringExtra(SKU);
        String description = intent.getStringExtra(DESCRIPTION);
        String packSize = intent.getStringExtra(PACK_SIZE);
        String receivedDate = intent.getStringExtra(RECEIVED_DATE);
        String expirationDate = intent.getStringExtra(EXPIRATION_DATE);
        TextView locationView = (TextView) findViewById(R.id.location);
        TextView countTypeView = (TextView) findViewById(R.id.count_type);
        TextView tagNumberView = (TextView) findViewById(R.id.item_tag_number);
        TextView skuView = (TextView) findViewById(R.id.item_sku);
        TextView descriptionView = (TextView) findViewById(R.id.item_description);
        TextView packSizeView = (TextView) findViewById(R.id.item_pack_size);
        TextView receivedDateView = (TextView) findViewById(R.id.item_received_date);
        TextView expirationDateView = (TextView) findViewById(R.id.item_expiration_date);
        locationView.setText(location);
        countTypeView.setText(countTypeDisplay);
        tagNumberView.setText(tagNumber);
        skuView.setText(sku);
        descriptionView.setText(description);
        packSizeView.setText(packSize);
        receivedDateView.setText(receivedDate);
        expirationDateView.setText(expirationDate);
    }
}
