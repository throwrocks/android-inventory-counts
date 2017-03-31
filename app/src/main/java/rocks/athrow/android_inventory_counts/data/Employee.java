package rocks.athrow.android_inventory_counts.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Employee
 * Created by joselopez1 on 3/31/2017.
 */

public class Employee extends RealmObject {
    public static final String FIELD_EMPLOYEE_NUMBER = "employee_number";
    public static final String FIELD_EMPLOYEE_NAME = "employee_name";
    @PrimaryKey
    private int employee_number;
    private String employee_name;

    public int getEmployeeNumber() {
        return employee_number;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employee_number = employeeNumber;
    }

    public String getEmployeeName() {
        return employee_name;
    }

    public void setEmployeeName(String employeeName) {
        this.employee_name = employeeName;
    }
}
