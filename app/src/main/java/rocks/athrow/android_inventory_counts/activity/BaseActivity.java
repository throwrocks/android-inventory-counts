package rocks.athrow.android_inventory_counts.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import rocks.athrow.android_inventory_counts.zxing.IntentIntegrator;
import rocks.athrow.android_inventory_counts.zxing.IntentResult;

import static rocks.athrow.android_inventory_counts.data.Constants.ITEM;
import static rocks.athrow.android_inventory_counts.data.Constants.LOCATION;


/**
 * BaseActivity
 * Created by joselopez1 on 4/4/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    String mScanType;
    String mBarcodeContents;
    boolean mIsScanning;

    /**
     * initiateScan
     * Initiates the barcode scanner app
     */
    void initiateScan(String scanType) {
        if (scanType != null) {
            mScanType = scanType;
            mIsScanning = true;
            rocks.athrow.android_inventory_counts.zxing.IntentIntegrator integrator = new rocks.athrow.android_inventory_counts.zxing.IntentIntegrator(this);
            integrator.initiateScan();
        }
    }

    /**
     * onActivityResult
     * Called after scanning a barcode
     * Handles scanning an item or a location (current location or new location)
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param intent      the intent from the barcode initiateScan
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        mIsScanning = false;
        if (mScanType == null) {
            return;
        }
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult == null) {
            return;
        }
        String contents = scanResult.getContents();
        if (contents == null) {
            return;
        }
        mBarcodeContents = contents;
        switch (mScanType) {
            case ITEM:
                ScanItemActivity scanItemActivity = ScanItemActivity.getInstance();
                if (scanItemActivity != null) {
                    scanItemActivity.processItem(contents);
                }
                break;
            case LOCATION:
                ScanLocationActivity scanLocationActivity = ScanLocationActivity.getInstance();
                if (scanLocationActivity != null) {
                    scanLocationActivity.processLocation(contents);
                }
                break;
        }
    }
}
