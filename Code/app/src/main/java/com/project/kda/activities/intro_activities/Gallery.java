package com.project.kda.activities.intro_activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.project.kda.Adapter.ImageListAdapter;
import com.project.kda.Model.AdminImageRegistration;
import com.project.kda.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Gallery extends Fragment {


    public Gallery() {
        // Required empty public constructor
    }


    RecyclerView rv_imagelst;
    DatabaseReference dbImageData;
    ArrayList<AdminImageRegistration> arrlist;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mShimmerViewContainer =(ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        rv_imagelst = (RecyclerView) view.findViewById(R.id.image_recycler);
        LoadData();
        return view;
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
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_imagelst.setLayoutManager(gridLayoutManager);
                ImageListAdapter eve = new ImageListAdapter(getActivity(),arrlist);
                rv_imagelst.setAdapter(eve);
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
