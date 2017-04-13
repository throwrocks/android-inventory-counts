package rocks.athrow.android_inventory_counts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rocks.athrow.android_inventory_counts.R;
import rocks.athrow.android_inventory_counts.data.Employee;
import rocks.athrow.android_inventory_counts.data.RealmQueries;
import rocks.athrow.android_inventory_counts.util.PreferencesHelper;
import rocks.athrow.android_inventory_counts.util.Utilities;

import static rocks.athrow.android_inventory_counts.data.Constants.COUNT_TYPE;
import static rocks.athrow.android_inventory_counts.data.Constants.COUNT_TYPE_DISPLAY;
import static rocks.athrow.android_inventory_counts.data.Constants.EMPLOYEE_NAME;
import static rocks.athrow.android_inventory_counts.data.Constants.EMPLOYEE_NUMBER;

/**
 * LoginActivity
 * Created by joselopez1 on 3/31/2017.
 */

public class LoginActivity extends BaseActivity {
    Button cancelButton;
    Button submitButton;
    int countType;
    String countTypeDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        countType = intent.getIntExtra(COUNT_TYPE,0);
        if ( countType == 0 ){
            finish();
        }
        PreferencesHelper preferencesHelper = new PreferencesHelper(getApplicationContext());
        countTypeDisplay = Utilities.getCountDisplay(countType);
        preferencesHelper.save(COUNT_TYPE, countType);
        preferencesHelper.save(COUNT_TYPE_DISPLAY, countTypeDisplay);
        TextView countTypeView = (TextView) findViewById(R.id.count_type);
        countTypeView.setText(countTypeDisplay);
        cancelButton = (Button) findViewById(R.id.login_cancel);
        submitButton = (Button) findViewById(R.id.login_submit);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputNumber = (EditText) findViewById(R.id.input_employee_number);
                String employeeNumber = inputNumber.getText().toString();
                if (!employeeNumber.isEmpty()){
                    submitLogin(employeeNumber);
                }else{
                    Utilities.showToast(getApplicationContext(),
                            getResources().getString(R.string.error_please_enter_employee_number),
                            Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void submitLogin(String employeeNumber){
        Employee employee = RealmQueries.getEmployee(getApplicationContext(), Integer.valueOf(employeeNumber));
        if ( employee == null ){
            Utilities.showToast(getApplicationContext(),
                    getResources().getString(R.string.error_employee_not_found),
                    Toast.LENGTH_SHORT);
            return;
        }
        String employeeName = employee.getEmployeeName();
        Utilities.showToast(getApplicationContext(),
                getResources().getString(R.string.login_success),
                Toast.LENGTH_SHORT);
        Intent intent = new Intent(getApplicationContext(), ScanLocationActivity.class);
        intent.putExtra(COUNT_TYPE, countType);
        intent.putExtra(COUNT_TYPE_DISPLAY, countTypeDisplay);
        intent.putExtra(EMPLOYEE_NUMBER, employeeNumber);
        intent.putExtra(EMPLOYEE_NAME, employeeName);
        startActivity(intent);
    }
}
