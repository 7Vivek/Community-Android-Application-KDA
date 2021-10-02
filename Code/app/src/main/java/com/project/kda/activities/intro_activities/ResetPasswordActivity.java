package com.project.kda.activities.intro_activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.kda.Model.RegistrationModel;
import com.project.kda.R;

import java.util.ArrayList;

public class ResetPasswordActivity extends AppCompatActivity {

    SharedPreferences shared;
    String userid = "";
    DatabaseReference database;
    EditText et_oldpass, et_newpasswrd;
    Button btn_reset;
    ArrayList<RegistrationModel> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        shared = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userid = (shared.getString("userid", ""));
        btn_reset = (Button) findViewById(R.id.btn_resetpass);
        et_oldpass = (EditText) findViewById(R.id.et_oldpass);
        et_newpasswrd = (EditText) findViewById(R.id.et_newpasswrd);
        database = FirebaseDatabase.getInstance().getReference().child("Registration");
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateData()) {
                    database.orderByChild("userId").equalTo(userid).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                try {
                                    arr = new ArrayList<>();
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        RegistrationModel artist = postSnapshot.getValue(RegistrationModel.class);
                                        arr.add(artist);
                                    }
                                    if (arr.size() > 0) {
                                        String oldpssword = arr.get(0).password;
                                        String newpassword = et_newpasswrd.getText().toString().trim();
                                        if (oldpssword.equals(et_oldpass.getText().toString().trim())) {
                                            try {
                                                database.child("password");
                                                //String region = region_spinner.getSelectedItem().toString();
                                                RegistrationModel register = new RegistrationModel(userid, arr.get(0).name, arr.get(0).email,newpassword,arr.get(0).mobile, arr.get(0).region);
                                                database.child(userid).setValue(register);
                                                SharedPreferences.Editor editor = shared.edit();
                                                editor.putString("password", newpassword);
                                                editor.commit();
                                                Toast.makeText(ResetPasswordActivity.this, "Password Changes Successfully!!", Toast.LENGTH_LONG).show();
                                                et_oldpass.setText("");
                                                et_newpasswrd.setText("");
                                            } catch (Exception e) {
                                                Log.e("Error", e.getMessage());
                                            }
                                        } else {
                                            Toast.makeText(ResetPasswordActivity.this, "Please enter correct password!", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(ResetPasswordActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Log.e("Login", e.getMessage());
                                }

                            } else {
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private boolean ValidateData() {
        int ErrCount = 0;
        if (et_oldpass.getText().toString().trim().equals("")) {
            et_oldpass.setError("Enter Old Password!");
            et_oldpass.requestFocus();
            ErrCount++;
            return false;
        }

        if (et_newpasswrd.getText().toString().trim().equals("")) {
            et_newpasswrd.setError("Enter New Password!");
            et_newpasswrd.requestFocus();
            ErrCount++;
            return false;
        }

        if (ErrCount > 0)
            return false;
        else
            return true;
    }

    private void LoadData() {

    }
}
