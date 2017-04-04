package rocks.athrow.android_inventory_counts.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rocks.athrow.android_inventory_counts.R;
import rocks.athrow.android_inventory_counts.api.API;
import rocks.athrow.android_inventory_counts.api.APIResponse;
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
        ServerLogin serverLogin = new ServerLogin();
        serverLogin.execute(employeeNumber);
    }

    private class ServerLogin extends AsyncTask<String, Void, Employee>{

        @Override
        protected Employee doInBackground(String... params) {
            return RealmQueries.getEmployee(getApplicationContext(), Integer.valueOf(params[0]));
        }

        @Override
        protected void onPostExecute(Employee employee) {
            super.onPostExecute(employee);
            Context context = getApplicationContext();
            if ( employee == null ){
                showToast("Employee not found.", Toast.LENGTH_SHORT);
                return;
            }

            int employeeNumber = employee.getEmployeeNumber();
            String employeeName = employee.getEmployeeName();
            showToast("Login Success!", Toast.LENGTH_SHORT);
            Intent intent = new Intent(context, ScanLocationActivity.class);
            intent.putExtra("count_type", countType);
            intent.putExtra("employee_number", employeeNumber);
            intent.putExtra("employee_name", employeeName);
            startActivity(intent);
        }

        /*@Override
        protected APIResponse doInBackground(String... params) {
            return API.findEmployee(params[0]);
        }

        @Override
        protected void onPostExecute(APIResponse apiResponse) {
            super.onPostExecute(apiResponse);
            Context context = getApplicationContext();
            int responseCode = apiResponse.getResponseCode();
            if ( responseCode != 200 ){
                showToast("Employee not found.", Toast.LENGTH_SHORT);
                return;
            }
            JSONArray jsonArray;
            JSONObject jsonObject;
            int employeeNumber;
            String employeeName;
            try {
                jsonArray = new JSONObject(apiResponse.getResponseText()).getJSONArray("data");
                jsonObject = jsonArray.getJSONObject(0);
                employeeNumber = jsonObject.getInt("employee_number");
                employeeName = jsonObject.getString("employee_name");
            }catch(JSONException e){
                e.printStackTrace();
                showToast("Parsing error. Contact your system administrator.", Toast.LENGTH_SHORT);
                return;
            }
            showToast("Login Success!", Toast.LENGTH_SHORT);
            Intent intent = new Intent(context, ScanLocationActivity.class);
            intent.putExtra("count_type", countType);
            intent.putExtra("employee_number", employeeNumber);
            intent.putExtra("employee_name", employeeName);
            startActivity(intent);
        }*/
    }
}
