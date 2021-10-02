package com.project.kda.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.kda.Adapter.ImageListAdapter;
import com.project.kda.Model.AdminImageRegistration;
import com.project.kda.R;

import java.util.ArrayList;

public class ImageListActivity extends AppCompatActivity {

    RecyclerView rv_imglst;
    DatabaseReference dbImageData;
    ArrayList<AdminImageRegistration> arrlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        rv_imglst=(RecyclerView) findViewById(R.id.image_recycler);
        LoadData();
    }

    private void LoadData() {
        arrlist=new ArrayList<>();
        dbImageData = FirebaseDatabase.getInstance().getReference().child("AdminImageRegistration");
        dbImageData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrlist.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AdminImageRegistration track = postSnapshot.getValue(AdminImageRegistration.class);
                    arrlist.add(track);
                }
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_imglst.setLayoutManager(gridLayoutManager);
                ImageListAdapter eve=new ImageListAdapter(ImageListActivity.this,arrlist);
                rv_imglst.setAdapter(eve);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addpostClick(View view) {
        startActivity(new Intent(ImageListActivity.this,ImageRegisterActivity.class));
    }
}
