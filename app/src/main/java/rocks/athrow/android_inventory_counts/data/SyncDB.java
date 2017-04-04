package rocks.athrow.android_inventory_counts.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rocks.athrow.android_inventory_counts.api.API;
import rocks.athrow.android_inventory_counts.api.APIResponse;
import rocks.athrow.android_inventory_counts.util.PreferencesHelper;

import static android.R.attr.name;

/**
 * SyncDB
 * Created by joselopez1 on 3/29/2017.
 */

public final class SyncDB {
    private static final String LOG_TAG = "SyncDB";

    public static void downloadNewRecords(Context context) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Number itemLastSerialNumber = realm.where(Item.class).findAll().max(Item.FIELD_SERIAL_NUMBER);
        Log.e(LOG_TAG, "Last item id: " + itemLastSerialNumber);
        int itemSerialNumber = 0;
        if (itemLastSerialNumber != null) {
            itemSerialNumber = itemLastSerialNumber.intValue();
        }
        Number locationLastSerialNumber = realm.where(Location.class).findAll().max(Location.FIELD_SERIAL_NUMBER);
        Log.e(LOG_TAG, "Last location id: " + locationLastSerialNumber);
        int locationSerialNumber = 0;
        if (locationLastSerialNumber != null) {
            locationSerialNumber = locationLastSerialNumber.intValue();
        }
        realm.commitTransaction();
        realm.close();
        APIResponse itemsResponse = API.getItems(itemSerialNumber);
        APIResponse employeesResponse = API.getEmployees();
        APIResponse locationsResponse = API.getLocations(locationSerialNumber);
        if (itemsResponse.getResponseCode() == 200) {
            updateDB(context, "items", itemsResponse.getResponseText());
        }

        if ( employeesResponse.getResponseCode() == 200 ){
            updateDB(context, "employees", employeesResponse.getResponseText());
        }
        if (locationsResponse.getResponseCode() == 200) {
            updateDB(context, "locations", locationsResponse.getResponseText());
        }
        PreferencesHelper preferencesHelper = new PreferencesHelper(context);
        preferencesHelper.save("last_sync", new Date().toString());
    }

    private static void updateDB(Context context, String type, String responseText) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm.compactRealm(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        switch (type) {
            case "items":
                JSONArray itemsArray = ParseJSON.getJSONArray(responseText);
                if (itemsArray == null) {
                    return;
                }
                int countItems = itemsArray.length();
                Log.e(LOG_TAG, "Items: " + countItems);
                realm.beginTransaction();
                for (int i = 0; i < countItems; i++) {
                    try {
                        Log.e(LOG_TAG, "Item update: " + i);
                        Item item = new Item();
                        JSONObject record = itemsArray.getJSONObject(i);
                        item.setId(record.getString(Item.FIELD_ID));
                        item.setSerialNumber(record.getInt(Item.FIELD_SERIAL_NUMBER));
                        item.setTagNumber(record.getString(Item.FIELD_TAG_NUMBER));
                        item.setSKU(record.getInt(Item.FIELD_SKU));
                        item.setDescription(record.getString(Item.FIELD_DESCRIPTION));
                        item.setPackSize(record.getString(Item.FIELD_PACK_SIZE));
                        item.setReceivingId(record.getInt(Item.FIELD_RECEIVING_ID));
                        item.setReceivedDate(record.getString(Item.FIELD_RECEIVED_DATE));
                        item.setExpirationDate(record.getString(Item.FIELD_EXPIRATION_DATE));
                        item.setItemType(record.getString(Item.FIELD_ITEM_TYPE));
                        item.setPrimaryLocation(record.getString(Item.FIELD_PRIMARY_LOCATION));
                        realm.copyToRealmOrUpdate(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                realm.commitTransaction();
                realm.close();
                Realm.compactRealm(realmConfig);
                break;
            case "employees":
                JSONArray employeesArray = ParseJSON.getJSONArray(responseText);
                if (employeesArray == null) {
                    return;
                }
                int countEmployees = employeesArray.length();
                Log.e(LOG_TAG, "Employee: " + countEmployees);
                realm.beginTransaction();
                for (int i = 0; i < countEmployees; i++) {
                    try {
                        Log.e(LOG_TAG, "Employee update: " + i);
                        Employee employee = new Employee();
                        JSONObject record = employeesArray.getJSONObject(i);
                        employee.setEmployeeNumber(record.getInt(Employee.FIELD_EMPLOYEE_NUMBER));
                        employee.setEmployeeName(record.getString(Employee.FIELD_EMPLOYEE_NAME));
                        realm.copyToRealmOrUpdate(employee);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                realm.commitTransaction();
                realm.close();
                Realm.compactRealm(realmConfig);
                break;
            case "locations":
                JSONArray locationsArray = ParseJSON.getJSONArray(responseText);
                if (locationsArray == null) {
                    return;
                }
                int countLocations = locationsArray.length();
                Log.e(LOG_TAG, "Locations: " + countLocations);
                realm.beginTransaction();
                for (int i = 0; i < countLocations; i++) {
                    try {
                        Location location = new Location();
                        JSONObject record = locationsArray.getJSONObject(i);
                        String locationName = record.getString(Location.FIELD_LOCATION);
                        location.setSerialNumber(record.getInt(Location.FIELD_SERIAL_NUMBER));
                        location.setBarcode(record.getString(Location.FIELD_BARCODE));
                        location.setLocation(locationName);
                        location.setType(record.getString(Location.FIELD_TYPE));
                        location.setPrimary(record.getBoolean(Location.FIELD_IS_PRIMARY));
                        location.setRow(record.getString(Location.FIELD_ROW));
                        realm.copyToRealmOrUpdate(location);
                        Log.d(LOG_TAG, "Save Location " + i + ": " + name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                realm.commitTransaction();
                realm.close();
                Realm.compactRealm(realmConfig);
                break;
        }

        realm.close();
    }

}
