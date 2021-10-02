package com.project.kda.activities.intro_activities;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.kda.Adapter.EventListAdapter;
import com.project.kda.Adapter.SlidingImage_Adapter;
import com.project.kda.Model.AdminEventRegistration;
import com.project.kda.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    TextView ViewMore;

    RecyclerView rv_eventlst;
    DatabaseReference dbEventData;
    ArrayList<AdminEventRegistration> arrlist;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES = {R.drawable.img6, R.drawable.img3, R.drawable.img5, R.drawable.img2};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private ShimmerFrameLayout mShimmerViewContainer;
     MyReceiver myReceiver;


    public Home() {
        
        // Required empty public constructor
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mShimmerViewContainer =(ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        rv_eventlst = (RecyclerView) view.findViewById(R.id.Rv_movies);
        ViewMore = view.findViewById(R.id.viewmore);

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new MyReceiver();
        getActivity().registerReceiver(myReceiver,intentFilter);

        ViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Event.class));
            }
        });
        init(view);

        LoadData();
        return view;
    }

    private void LoadData() {
        arrlist = new ArrayList<>();
        dbEventData = FirebaseDatabase.getInstance().getReference().child("AdminEventRegistration");
        dbEventData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrlist.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AdminEventRegistration track = postSnapshot.getValue(AdminEventRegistration.class);
                    arrlist.add(track);
                }
                LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                rv_eventlst.setLayoutManager(gridLayoutManager);
                rv_eventlst.setItemAnimator(new DefaultItemAnimator());
                EventListAdapter eve = new EventListAdapter(getActivity(), arrlist);
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


    private void init(View view) {
        int images=IMAGES.length;
        ImagesArray.clear();
        for (int i = 0; i < images; i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) view.findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(getContext(), ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

}