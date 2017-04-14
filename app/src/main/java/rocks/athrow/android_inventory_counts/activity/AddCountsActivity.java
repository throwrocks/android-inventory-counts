package rocks.athrow.android_inventory_counts.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rocks.athrow.android_inventory_counts.R;
import rocks.athrow.android_inventory_counts.data.RealmQueries;
import rocks.athrow.android_inventory_counts.util.Utilities;

import static rocks.athrow.android_inventory_counts.data.Constants.COUNT_TYPE;
import static rocks.athrow.android_inventory_counts.data.Constants.COUNT_TYPE_DISPLAY;
import static rocks.athrow.android_inventory_counts.data.Constants.DESCRIPTION;
import static rocks.athrow.android_inventory_counts.data.Constants.EMPLOYEE_NAME;
import static rocks.athrow.android_inventory_counts.data.Constants.EMPLOYEE_NUMBER;
import static rocks.athrow.android_inventory_counts.data.Constants.EXPIRATION_DATE;
import static rocks.athrow.android_inventory_counts.data.Constants.LOCATION;
import static rocks.athrow.android_inventory_counts.data.Constants.PACK_SIZE;
import static rocks.athrow.android_inventory_counts.data.Constants.RECEIVED_DATE;
import static rocks.athrow.android_inventory_counts.data.Constants.SKU;
import static rocks.athrow.android_inventory_counts.data.Constants.TAG_NUMBER;

/**
 * AddCountsActivity
 * Created by joselopez1 on 4/13/2017.
 */

public class AddCountsActivity extends AppCompatActivity {
    private int employeeNumber;
    private String employeeName;
    private String location;
    private int countType;
    private String countTypeDisplay;
    private String tagNumber;
    private int sku;
    private String description;
    private String packSize;
    private String receivedDate;
    private String expirationDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_count);
        Intent intent = getIntent();
        employeeNumber = Integer.parseInt(intent.getStringExtra(EMPLOYEE_NUMBER));
        employeeName = intent.getStringExtra(EMPLOYEE_NAME);
        location = intent.getStringExtra(LOCATION);
        countType = Integer.parseInt(intent.getStringExtra(COUNT_TYPE));
        countTypeDisplay = intent.getStringExtra(COUNT_TYPE_DISPLAY);
        tagNumber = intent.getStringExtra(TAG_NUMBER);
        sku = Integer.parseInt(intent.getStringExtra(SKU));
        description = intent.getStringExtra(DESCRIPTION);
        packSize = intent.getStringExtra(PACK_SIZE);
        receivedDate = intent.getStringExtra(RECEIVED_DATE);
        expirationDate = intent.getStringExtra(EXPIRATION_DATE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.count_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                doneButton();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doneButton() {
        Context ctx = getApplicationContext();
        int d = Toast.LENGTH_SHORT;
        int pallets = 0;
        int caseQty = 0;
        int looseQty = 0;
        EditText inputPallets = (EditText) findViewById(R.id.input_pallets);
        EditText inputCaseQty = (EditText) findViewById(R.id.input_case_qty);
        EditText inputLooseQty = (EditText) findViewById(R.id.input_loose_qty);
        String palletsString = inputPallets.getText().toString();
        String caseQtyString = inputCaseQty.getText().toString();
        String looseQtyString = inputLooseQty.getText().toString();
        boolean isEmptyPallets = palletsString.isEmpty();
        boolean isEmptyCaseQty = caseQtyString.isEmpty();
        boolean isEmptyLooseQty = looseQtyString.isEmpty();
        if (isEmptyPallets && isEmptyCaseQty && isEmptyLooseQty) {
            Utilities.showToast(ctx, "All quantities are empty.", d);
            return;
        }
        if (!isEmptyPallets && isEmptyCaseQty) {
            Utilities.showToast(ctx, "The case qty is empty.", d);
            return;
        }
        if (isEmptyPallets && !isEmptyCaseQty) {
            Utilities.showToast(ctx, "The pallets qty is empty.", d);
            return;
        }
        if (!isEmptyPallets) {
            pallets = Integer.parseInt(palletsString);
        }
        if (!isEmptyCaseQty) {
            caseQty = Integer.parseInt(caseQtyString);
        }
        if (!isEmptyLooseQty) {
            looseQty = Integer.parseInt(looseQtyString);
        }
        if (pallets == 0 && caseQty == 0 && looseQty == 0) {
            Utilities.showToast(ctx, "All quantities are 0.", d);
            return;
        }
        if (pallets > 0 && caseQty == 0) {
            Utilities.showToast(ctx, "The case qty must be higher than 0,", d);
            return;
        }
        if (pallets == 0 && caseQty > 0) {
            Utilities.showToast(ctx, "The pallets qty must be higher than 0.", d);
            return;
        }
        RealmQueries.saveCount(
                ctx,
                location,
                tagNumber,
                sku,
                description,
                packSize,
                receivedDate,
                expirationDate,
                employeeNumber,
                employeeName,
                countType,
                pallets,
                caseQty,
                looseQty
        );
    }
}
