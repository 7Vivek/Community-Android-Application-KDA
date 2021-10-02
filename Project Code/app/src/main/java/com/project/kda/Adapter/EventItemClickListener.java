package com.project.kda.Adapter;

import android.widget.ImageView;

import com.project.kda.Model.Event;

public interface EventItemClickListener {

    void onEventClick(Event event, ImageView eventImageView); // we will need the imageview to make the shared animation between the two activity

}
