package com.project.kda.activities.intro_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.project.kda.Model.AdminImageRegistration;
import com.project.kda.R;
import com.project.kda.Utils.Utils;

import java.util.ArrayList;

public class ImageDetailsActivity extends AppCompatActivity{

    public static final String TAG = "ImageDetailsActivity";

    SharedPreferences shared;
    String imageid = "";
    ImageView image_cover;
    DatabaseReference database;
    TextView image_title,image_date;
    ArrayList<AdminImageRegistration> arr;
    LikeButton share_btn;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        imageid= getIntent().getStringExtra("imageid");
        image_title = (TextView) findViewById(R.id.image_title);
        image_date = (TextView) findViewById(R.id.image_date);
        image_cover=(ImageView)findViewById(R.id.image_cover);
        share_btn = (LikeButton) findViewById(R.id.share_btn);

        database= FirebaseDatabase.getInstance().getReference().child("AdminImageRegistration");
        LoadData();
    }


    private void LoadData() {
        database.orderByChild("imageId").equalTo(imageid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        arr = new ArrayList<AdminImageRegistration>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            AdminImageRegistration artist = postSnapshot.getValue(AdminImageRegistration.class);
                            arr.add(artist);
                        }
                        if (arr.size() > 0) {
                            image_title.setText(arr.get(0).imageTitle);
                            image_date.setText(arr.get(0).eventDate);
                            String img=arr.get(0).eventimage;
                            image_cover.setImageBitmap(Utils.StringToBitMap(img));
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

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent=Intent.createChooser(intent,"Share By");
                startActivity(intent);
            }
        });
    }
}
