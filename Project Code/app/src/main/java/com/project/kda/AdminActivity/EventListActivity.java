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
import com.project.kda.Adapter.EventListAdapter;
import com.project.kda.Model.AdminEventRegistration;
import com.project.kda.R;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {

    RecyclerView rv_eventlst;
    DatabaseReference dbEventData;
    ArrayList<AdminEventRegistration> arrlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        rv_eventlst=(RecyclerView) findViewById(R.id.event_recycler);
        LoadData();
    }

    private void LoadData() {
        arrlist=new ArrayList<>();
        dbEventData = FirebaseDatabase.getInstance().getReference().child("AdminEventRegistration");
        dbEventData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrlist.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AdminEventRegistration track = postSnapshot.getValue(AdminEventRegistration.class);
                    arrlist.add(track);
                }
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_eventlst.setLayoutManager(gridLayoutManager);
                EventListAdapter eve=new EventListAdapter(EventListActivity.this,arrlist);
                rv_eventlst.setAdapter(eve);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addpostClick(View view) {
        startActivity(new Intent(EventListActivity.this,EventRegisterActivity.class));
    }
}
