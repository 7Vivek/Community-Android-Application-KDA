package com.project.kda.activities.intro_activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.kda.Adapter.EventListAdapter;
import com.project.kda.Model.AdminEventRegistration;
import com.project.kda.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Event extends Fragment {

    public Event() {
        // Required empty public constructor
    }

    RecyclerView rv_eventlst;
    DatabaseReference dbEventData;
    ArrayList<AdminEventRegistration> arrlist;
    private ShimmerFrameLayout mShimmerViewContainer;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        mShimmerViewContainer =(ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        rv_eventlst = (RecyclerView) view.findViewById(R.id.event_recycler);
        LoadData();
        return view;
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
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_eventlst.setLayoutManager(gridLayoutManager);
                rv_eventlst.setItemAnimator(new DefaultItemAnimator());
                EventListAdapter eve=new EventListAdapter(getActivity(), arrlist);
                rv_eventlst.setAdapter(eve);
                // stop animating Shimmer and hide the layout
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
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
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

}






