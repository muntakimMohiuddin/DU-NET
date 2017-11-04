package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 11/1/17.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static com.example.aniomi.myapplication.R.id.parent;

/**
 * Created by aniomi on 9/26/17.
 */

public class postdetailsadapter extends RecyclerView.Adapter<postdetailsadapter.sViewHolder> {



    private List<comment> list;
    private Context context;
    private RelativeLayout r1,r2;
    public postdetailsadapter(List<comment> list,Context context) {
        this.list = list;this.context=context;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {

        public TextView t1,t2;
        public CardView cv;
        public ImageView imageView;
        public sViewHolder(View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            imageView=itemView.findViewById(R.id.imageView);
            cv=itemView.findViewById(R.id.cv);

        }


    }
    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        return new sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(sViewHolder holder, int position) {

        comment temp=list.get(position);
        holder.t1.setText(temp.name);
        holder.t2.setText(temp.getDescription());
        MainActivity.setImageFromStorage(context,"images/"+temp.sender+".jpg",holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
