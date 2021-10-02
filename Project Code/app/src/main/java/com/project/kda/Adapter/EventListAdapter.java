package com.project.kda.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.kda.Model.AdminEventRegistration;
import com.project.kda.R;
import com.project.kda.Utils.Utils;
import com.project.kda.activities.EventDetailsActivity;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {

    ArrayList lst_event;
    Context context;
    private static int selectedpos=RecyclerView.NO_POSITION;

    Context mContext ;
    ArrayList<AdminEventRegistration> mData ;

    public EventListAdapter(Context mContext, ArrayList<AdminEventRegistration> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_eventlist,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(mData.get(position).getEventTitle());
        String img=mData.get(position).getEventimage();
        holder.img_book_thumbnail.setImageBitmap(Utils.StringToBitMap(img));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(final View itemView) {
            super(itemView);
            tv_book_title = (TextView) itemView.findViewById(R.id.tv_event_title) ;
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            img_book_thumbnail=(ImageView) itemView.findViewById(R.id.iv_event);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedpos=getAdapterPosition();
                    Intent intent=new Intent(mContext, EventDetailsActivity.class);
                    intent.putExtra("eventid",mData.get(selectedpos).eventId);
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
