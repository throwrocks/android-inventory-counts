package rocks.athrow.android_inventory_counts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rocks.athrow.android_inventory_counts.R;
import rocks.athrow.android_inventory_counts.data.Employee;
import rocks.athrow.android_inventory_counts.data.RealmQueries;
import rocks.athrow.android_inventory_counts.util.PreferencesHelper;

/**
 * LoginActivity
 * Created by joselopez1 on 3/31/2017.
 */

public class LoginActivity extends BaseActivity {
    Button cancelButton;
    Button submitButton;
    int countType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        countType = intent.getIntExtra("count_type",0);
        PreferencesHelper preferencesHelper = new PreferencesHelper(getApplicationContext());
        preferencesHelper.save("count_type", countType);
        setCountTypeView(countType);
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
                submitLogin(employeeNumber);
            }
        });
    }

    private void submitLogin(String employeeNumber){
        Employee employee = RealmQueries.getEmployee(getApplicationContext(), Integer.valueOf(employeeNumber));
        if ( employee == null ){
            showToast("Employee not found.", Toast.LENGTH_SHORT);
            return;
        }
        String employeeName = employee.getEmployeeName();
        showToast("Login Success!", Toast.LENGTH_SHORT);
        Intent intent = new Intent(getApplicationContext(), ScanLocationActivity.class);
        intent.putExtra("count_type", countType);
        intent.putExtra("employee_number", employeeNumber);
        intent.putExtra("employee_name", employeeName);
        startActivity(intent);
    }
}
