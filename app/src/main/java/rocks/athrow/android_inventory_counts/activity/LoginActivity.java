package rocks.athrow.android_inventory_counts.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rocks.athrow.android_inventory_counts.R;
import rocks.athrow.android_inventory_counts.api.API;
import rocks.athrow.android_inventory_counts.api.APIResponse;

/**
 * LoginActivity
 * Created by joselopez1 on 3/31/2017.
 */

public class LoginActivity extends AppCompatActivity {
    TextView countTypeView;
    Button cancelButton;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        int countType = intent.getIntExtra("countType",0);
        if ( countType == 0 ){
            finish();
        }
        String countTypeDisplay = null;
        switch (countType){
            case 1:
                countTypeDisplay = "1st";
                break;
            case 2:
                countTypeDisplay = "2nd";
                break;
            case 3:
                countTypeDisplay = "3rd";
                break;
        }
        countTypeDisplay = countTypeDisplay + " Count";
        countTypeView = (TextView) findViewById(R.id.count_type);
        cancelButton = (Button) findViewById(R.id.login_cancel);
        submitButton = (Button) findViewById(R.id.login_submit);
        countTypeView.setText(countTypeDisplay);
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

    private class ServerLogin extends AsyncTask<String, Void, APIResponse>{
        @Override
        protected APIResponse doInBackground(String... params) {
            return API.findEmployee(params[0]);
        }

        @Override
        protected void onPostExecute(APIResponse apiResponse) {
            super.onPostExecute(apiResponse);
            Context context = getApplicationContext();
            CharSequence text = "Login!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
