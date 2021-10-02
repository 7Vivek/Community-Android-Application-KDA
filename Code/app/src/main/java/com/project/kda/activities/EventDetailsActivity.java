package com.project.kda.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.kda.Model.AdminEventRegistration;
import com.project.kda.R;
import com.project.kda.Utils.Utils;

import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity {

    SharedPreferences shared;
    String eventid = "";
    String thumbnail;
    ImageView event_image;
    DatabaseReference database;
    TextView detail_event_title,detail_event_date,event_details;
    ArrayList<AdminEventRegistration> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
       /* shared = getSharedPreferences("myPrefs", MODE_PRIVATE);
        eventid = getIntent().getStringExtra("eventid");*/

        eventid= getIntent().getStringExtra("eventid");
        detail_event_title = (TextView) findViewById(R.id.detail_event_title);
        detail_event_date = (TextView)findViewById(R.id.detail_event_date);
        event_details = (TextView)findViewById(R.id.event_details);
        event_image=(ImageView)findViewById(R.id.event_image);
        database=FirebaseDatabase.getInstance().getReference().child("AdminEventRegistration");
        LoadData();

    /**    String poster = "https://image.tmdb.org/t/p/w500" + thumbnail;

        Glide.with(this)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(event_image);**/
    }

    private void LoadData() {
        database.orderByChild("eventId").equalTo(eventid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        arr = new ArrayList<AdminEventRegistration>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            AdminEventRegistration artist = postSnapshot.getValue(AdminEventRegistration.class);
                            arr.add(artist);
                        }
                        if (arr.size() > 0) {
                            detail_event_title.setText(arr.get(0).eventTitle);
                            detail_event_date.setText(arr.get(0).eventDate);
                            event_details.setText(arr.get(0).eventDescription);
                            String img=arr.get(0).eventimage;
                            event_image.setImageBitmap(Utils.StringToBitMap(img));
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
