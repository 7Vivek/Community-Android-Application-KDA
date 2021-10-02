package com.project.kda.activities.intro_activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.kda.Model.RegistrationModel;
import com.project.kda.R;
import com.project.kda.activities.AboutActivity;
import com.project.kda.activities.LoginActivity;
import com.project.kda.activities.RegionalHeadActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class More extends Fragment {
    TextView txtEditProfile,txtchangepswd,txtcontact,txtcontact1,txthead,t1,LogOut,tv_username,tv_email;
    ArrayList<RegistrationModel> arr;
    DatabaseReference database;
    String userid = "";
    SharedPreferences shared;

    public More() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_more, container, false);
        t1 = view.findViewById(R.id.about_kda);
        txtEditProfile = view.findViewById(R.id.txtEdit);
        txtchangepswd = view.findViewById(R.id.txtChangePswd);
        txthead = view.findViewById(R.id.txt_head);
        txtcontact=view.findViewById(R.id.txtContactUs);
        txtcontact1=view.findViewById(R.id.txtContactUs1);
        LogOut =view.findViewById(R.id.logout);
        tv_username = view.findViewById(R.id.tv_username);
        tv_email = view.findViewById(R.id.tv_email);

        database = FirebaseDatabase.getInstance().getReference().child("Registration");
        LoadData();

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });
        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
        txtchangepswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
            }
        });
        txthead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegionalHeadActivity.class));
            }
        });
        txtcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContactActivity.class));
            }
        });
        txtcontact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContactActivity.class));
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;

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
                            tv_username.setText(arr.get(0).name);
                            tv_email.setText(arr.get(0).email);
                        } else {

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



