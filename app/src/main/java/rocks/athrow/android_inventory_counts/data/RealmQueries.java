package rocks.athrow.android_inventory_counts.data;

import android.content.Context;

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

    public static RealmResults<Employee> getEmployee(Context context, int employeeNumber) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Employee> realmResults =
                realm.where(Employee.class).equalTo(Employee.FIELD_EMPLOYEE_NUMBER, employeeNumber).findAll();
        realm.commitTransaction();
        return realmResults;
    }

}
