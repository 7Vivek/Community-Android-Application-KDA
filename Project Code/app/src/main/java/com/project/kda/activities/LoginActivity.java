package com.project.kda.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.kda.AdminActivity.Admin_Menu;
import com.project.kda.Model.RegistrationModel;
import com.project.kda.R;
import com.project.kda.activities.intro_activities.MainActivity;

import java.util.ArrayList;

import static com.project.kda.activities.RegistrationActivity.isEmailValid;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    SharedPreferences pref;
    EditText et_email, et_passed;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        // getWindow().setStatusBarColor(Color.TRANSPARENT);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_email = (EditText) findViewById(R.id.et_email);
        et_passed = (EditText) findViewById(R.id.et_passwrd);
        pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        database = FirebaseDatabase.getInstance().getReference().child("Registration");
    }

    public void OpenSignupPage(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }

    private boolean ValidateData() {
        int ErrCount = 0;
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
        if (et_passed.getText().toString().trim().equals("")) {
            et_passed.setError("Enter Password!");
            et_passed.requestFocus();
            ErrCount++;
            return false;
        }

        if (ErrCount > 0)
            return false;
        else
            return true;
    }

    public void onLoginClick(View view) {
        if (ValidateData()) {
            String email = et_email.getText().toString().trim();
            if(email.equals("Admin@gmail.com") && et_passed.getText().toString().trim().equals("Admin@123")){
                startActivity(new Intent(LoginActivity.this, Admin_Menu.class));
                finish();
            }
            else
            {
                database.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            try{
                                ArrayList<RegistrationModel> arr=new ArrayList<>();
                                String passwd="";
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    RegistrationModel artist = postSnapshot.getValue(RegistrationModel.class);
                                    //adding artist to the list
                                    arr.add(artist);
                                }
                                passwd=arr.get(0).password;
                                if (!passwd.equals(et_passed.getText().toString().trim())) {
                                    Toast.makeText(LoginActivity.this, "Email or Password incorrect", Toast.LENGTH_LONG).show();
                                } else {
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("email", et_email.getText().toString().trim());
                                    editor.putString("Password", et_passed.getText().toString().trim());
                                    editor.putString("userid", arr.get(0).getUserId().trim());
                                    editor.commit();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            catch(Exception e){
                                Log.e("Login",e.getMessage());
                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "Email or Password incorrect", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        }
    }
}
