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

import com.project.kda.Model.AdminImageRegistration;
import com.project.kda.R;
import com.project.kda.Utils.Utils;
import com.project.kda.activities.intro_activities.ImageDetailsActivity;

import java.util.ArrayList;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {

    ArrayList lst_image;
    Context context;
    private static int selectedpos=RecyclerView.NO_POSITION;

     Context mContext ;
     ArrayList<AdminImageRegistration> mData ;

    public ImageListAdapter(Context mContext, ArrayList<AdminImageRegistration> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View view ;
      LayoutInflater mInflater = LayoutInflater.from(mContext);
      view = mInflater.inflate(R.layout.card_imagelist,parent,false);
     return new MyViewHolder(view);
      //  return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_image_title.setText(mData.get(position).getImageTitle());
        String img=mData.get(position).getEventimage();
        holder.img_image_thumbnail.setImageBitmap(Utils.StringToBitMap(img));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_image_title;
        ImageView img_image_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_image_title = (TextView) itemView.findViewById(R.id.tv_image_title) ;
            cardView = (CardView) itemView.findViewById(R.id.imageview_id);
            img_image_thumbnail=(ImageView) itemView.findViewById(R.id.iv_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedpos=getAdapterPosition();
                    Intent intent=new Intent(mContext, ImageDetailsActivity.class);
                    intent.putExtra("imageid",mData.get(selectedpos).imageId);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
