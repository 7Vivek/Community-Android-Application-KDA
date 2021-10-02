package com.project.kda.activities.intro_activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.kda.Model.RegistrationModel;
import com.project.kda.R;

import java.util.ArrayList;

import static com.project.kda.activities.RegistrationActivity.isEmailValid;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences shared;
    String userid = "";
    DatabaseReference database;
    EditText et_name, et_mobile, et_email;
    Spinner et_spinner;
    Button btn_submit;
    ArrayList<RegistrationModel> arr;
    private ShimmerFrameLayout mShimmerViewContainer,mShimmerViewContainer1,mShimmerViewContainer2,mShimmerViewContainer3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Spinner mySpinner = (Spinner) findViewById(R.id.et_spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ProfileActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        shared = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userid = (shared.getString("userid", ""));
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_spinner=(Spinner) findViewById(R.id.et_spinner);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        mShimmerViewContainer =(ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer1 =(ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        mShimmerViewContainer2 =(ShimmerFrameLayout) findViewById(R.id.shimmer_view_container2);
        mShimmerViewContainer3 =(ShimmerFrameLayout) findViewById(R.id.shimmer_view_container3);


        database = FirebaseDatabase.getInstance().getReference().child("Registration");
        LoadData();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateData()) {
                   /* if (et_spinner.getSelectedItemPosition() == 0) {
                        Toast.makeText(RegistrationActivity.this, "Please select Region.", Toast.LENGTH_LONG).show();
                    } else {*/
                    Query query = database.orderByChild("email").equalTo(et_email.getText().toString().trim());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                et_email.setError("Email already registered.");
                                et_email.requestFocus();
                            } else {
                                try {
                                    String name = et_name.getText().toString().trim();
                                    String email = et_email.getText().toString().trim();
                                    String mobiles = et_mobile.getText().toString().trim();
                                    String region = et_spinner.getSelectedItem().toString();
                                    //String region = region_spinner.getSelectedItem().toString();
                                    RegistrationModel register = new RegistrationModel(userid, name, email, arr.get(0).password, mobiles, arr.get(0).region);
                                    database.child(userid).setValue(register);
                                    SharedPreferences.Editor editor = shared.edit();
                                    editor.putString("email", register.email);
                                    editor.commit();
                                    Toast.makeText(ProfileActivity.this, "Profile Updated Successfully!!", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Log.e("Error", e.getMessage());
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ProfileActivity.this, "Something went Wrong!", Toast.LENGTH_LONG).show();
                        }
                    });

                    // }

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

    private void LoadData() {
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
                            et_name.setText(arr.get(0).name);
                            et_mobile.setText(arr.get(0).mobile);
                            et_email.setText(arr.get(0).email);
                        } else {

                        }

                    } catch (Exception e) {
                        Log.e("Login", e.getMessage());
                    }

                } else {
                }
                // stop animating Shimmer and hide the layout
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer1.stopShimmerAnimation();
                mShimmerViewContainer1.setVisibility(View.GONE);
                mShimmerViewContainer2.stopShimmerAnimation();
                mShimmerViewContainer2.setVisibility(View.GONE);
                mShimmerViewContainer3.stopShimmerAnimation();
                mShimmerViewContainer3.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer1.startShimmerAnimation();
        mShimmerViewContainer2.startShimmerAnimation();
        mShimmerViewContainer3.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer1.stopShimmerAnimation();
        mShimmerViewContainer2.stopShimmerAnimation();
        mShimmerViewContainer3.stopShimmerAnimation();

        super.onPause();
    }

}
