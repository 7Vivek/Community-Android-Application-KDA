package com.project.kda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.kda.Model.RegistrationModel;
import com.project.kda.R;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    EditText et_name, et_email, et_mobile, et_pswd;
    Button btn_signup;
    Spinner region_spinner;
    DatabaseReference databaseArtists;

    public void OpenLoginPage(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(RegistrationActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Init();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo
                if (ValidateData()) {
                    if (region_spinner.getSelectedItemPosition() == 0) {
                        Toast.makeText(RegistrationActivity.this, "Please select Region.", Toast.LENGTH_LONG).show();
                    } else {
                        Query query = databaseArtists.orderByChild("email").equalTo(et_email.getText().toString().trim());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    et_email.setError("Email already registered.");
                                    et_email.requestFocus();
                                } else {
                                    Signup();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(RegistrationActivity.this, "Something went Wrong!", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }

            }
        });
    }

    private boolean ValidateData() {
        int ErrCount = 0;
        if (et_name.getText().toString().trim().equals("")) {
            et_name.setError("Enter Name!");
            et_name.requestFocus();
            ErrCount++;
            return false;
        }
        else if (et_name.getText().toString().trim().length()==0)

        {
            et_name.requestFocus();
            et_name.setError("FIELD CANNOT BE EMPTY");
            ErrCount++;
            return false;
        }
        if (et_email.getText().toString().trim().equals("")) {
            et_email.setError("Please enter your email.");
            et_email.requestFocus();
            ErrCount++;
            return false;
        } else {
            boolean valid = isEmailValid(et_email.getText().toString());
            if (!valid) {
                et_email.setError("Invalid Email!");
                et_email.requestFocus();
                ErrCount++;
                return false;
            }
        }

       /** private boolean validatePassword() {
            String passwordInput = et_pswd.getText().toString().trim();

            if (passwordInput.isEmpty()) {
                et_pswd.setError("Field can't be empty");
                return false;
            } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
                et_pswd.setError("Password too weak");
                return false;
            } else {
                et_pswd.setError(null);
                return true;
            }
        }**/
        if (et_pswd.getText().toString().trim().equals("")) {
            et_pswd.setError("Enter Password!");
            et_pswd.requestFocus();
            ErrCount++;
            return false;
        }

        if (et_mobile.getText().toString().trim().equals("")) {
            et_mobile.setError("Enter Mobile!");
            et_mobile.requestFocus();
            ErrCount++;
            return false;
        } else if (et_mobile.getText().toString().length() < 10) {
            et_mobile.setError("Invalid Contact no.!");
            et_mobile.requestFocus();
            ErrCount++;
            return false;
        }
        if (ErrCount > 0)
            return false;
        else
            return true;
    }

    public final static boolean isEmailValid(String target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private void Signup() {
        try {
            String id = databaseArtists.push().getKey();
            String name = et_name.getText().toString().trim();
            String email = et_email.getText().toString().trim();
            String passwd = et_pswd.getText().toString().trim();
            String mobiles = et_mobile.getText().toString().trim();
            String region = region_spinner.getSelectedItem().toString();
            RegistrationModel register = new RegistrationModel(id, name, email, passwd, mobiles, region);
            databaseArtists.child(id).setValue(register);
            Toast.makeText(this, "Registered Successfully!!", Toast.LENGTH_LONG).show();
            onBackPressed();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

    }

    private void Init() {
        databaseArtists = FirebaseDatabase.getInstance().getReference().child("Registration");
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pswd = (EditText) findViewById(R.id.et_pswd);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        region_spinner = (Spinner) findViewById(R.id.spinner);
    }
}
