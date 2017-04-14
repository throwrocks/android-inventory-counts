package rocks.athrow.android_inventory_counts.data;

import android.content.Context;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by joselopez1 on 3/31/2017.
 */

public final class RealmQueries {
    private RealmQueries() {
        throw new AssertionError("No Utilities instances for you!");
    }

    public static Employee getEmployee(Context context, int employeeNumber) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Employee> realmResults =
                realm.where(Employee.class).equalTo(Employee.FIELD_EMPLOYEE_NUMBER, employeeNumber).findAll();
        realm.commitTransaction();
        if (realmResults.size() == 0) {
            return null;
        } else {
            return realmResults.get(0);
        }
    }

    /**
     * getLocationByBarcode
     * A method to get a Location object. Used with the barcode scanner.
     *
     * @param context a Context object
     * @param barcode the location's barcode
     * @return a Location object
     */
    public static RealmResults<Location> getLocationByBarcode(Context context, String barcode) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Location> realmResults =
                realm.where(Location.class).equalTo(Location.FIELD_BARCODE, barcode).findAll();
        realm.beginTransaction();
        realm.commitTransaction();
        return realmResults;
    }

    /**
     * getItemByTagNumber
     * A method to get an Item record by id
     *
     * @param context   a Context object
     * @param tagNumber the item's tag number
     * @return an Item object
     */
    public static RealmResults<Item> getItemByTagNumber(Context context, String tagNumber) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Item> realmResults = realm.where(Item.class).equalTo(Item.FIELD_TAG_NUMBER, tagNumber).findAll();
        realm.beginTransaction();
        realm.commitTransaction();
        return realmResults;
    }

    public static boolean saveCount(
            Context context,
            String location,
            String tagNumber,
            int sku,
            String description,
            String packSize,
            String receivedDate,
            String expirationDate,
            int employeeNumber,
            String employeeName,
            int countType,
            int pallets,
            int caseQty,
            int looseQty
    ){
        String id = UUID.randomUUID().toString();
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        InventoryCount count = new InventoryCount();
        count.setId(id);
        count.setLocation(location);
        count.setTagNumber(tagNumber);
        count.setSKU(sku);
        count.setDescription(description);
        count.setPackSize(packSize);
        count.setReceivedDate(receivedDate);
        count.setExpirationDate(expirationDate);
        count.setEmployeeNumber(employeeNumber);
        count.setEmployeeName(employeeName);
        count.setCountType(countType);
        count.setPallets(pallets);
        count.setCaseQty(caseQty);
        count.setLooseQty(looseQty);
        realm.copyToRealmOrUpdate(count);
        realm.commitTransaction();
        realm.close();
        return false;

    }
}
