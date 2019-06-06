package com.pcp.unittest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {

    // Logger for this class
    private static final String TAG = "MainActivity";

    // The helper that manages writing to SharedPreferences
    private SharedPreferencesHelper mSharedPreferencesHelper;

    // The input field where the user enters his name.
    private EditText etName;

    // The date picker where the user enters his date of birth
    private DatePicker dpDob;

    // The input field where the user enters his email
    private EditText etEmail;

    // The validator for the email input field
    private EmailValidator emailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shortcuts to input fields
        etName = (EditText) findViewById(R.id.etUserNameInput);
        dpDob = (DatePicker) findViewById(R.id.dpDateOfBirthInput);
        etEmail = (EditText) findViewById(R.id.etEmailInput);

        // Set up field validators
        emailValidator = new EmailValidator();
        etEmail.addTextChangedListener(emailValidator);

        // Instantiate a SharedPreferencesHelper
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferencesHelper = new SharedPreferencesHelper(sharedPreferences);

        // Fill input fields from data retrieved from the SharedPreferences
        populateUi();
    }

    /*
    ** Initialize all fields from the personal info saved in the SharedPreferences
     */

    private void populateUi(){
        SharedPreferenceEntry sharedPreferenceEntry;
        sharedPreferenceEntry = mSharedPreferencesHelper.getPersonalInfo();

        etName.setText(sharedPreferenceEntry.getName());
        Calendar dateOfBirth = sharedPreferenceEntry.getDateOfBirth();

        dpDob.init(dateOfBirth.get(Calendar.YEAR), dateOfBirth.get(Calendar.MONTH), dateOfBirth.get(Calendar.DAY_OF_MONTH), null);

        etEmail.setText(sharedPreferenceEntry.getEmail());
    }

    // Called when the "Save" button is clicked

    public void onSaveClick(View view) {
        // Don't save if the fields do not validate
        if(!emailValidator.isValid()){
            etEmail.setError("Invalid Email");
            Log.w(TAG, "Not saving personal information: Invalid email");
            return;
        }
        // Get the text from the input fields
        String name = etName.getText().toString();
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(dpDob.getYear(), dpDob.getMonth(), dpDob.getDayOfMonth());
        String email = etEmail.getText().toString();

        // Create a setting model to persist
        SharedPreferenceEntry sharedPreferenceEntry = new SharedPreferenceEntry(name, dateOfBirth, email);

        // Persist the personal information
        boolean isSuccess = mSharedPreferencesHelper.savePersonalInfo(sharedPreferenceEntry);
        if (isSuccess){
            Toast.makeText(this, "Personal Information saved", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Personal Information saved");
        }else{
            Log.e(TAG, "Failed to write personal information to SharedPreferences");
        }
    }

        // Called when the revert button is clicked
    public void onRevertClick(View view) {
        populateUi();
        Toast.makeText(this, "Personal information reverted", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Personal information reverted");
    }
}
