package com.example.aniomi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by aniomi on 10/20/17.
 */

public class messanger_home_adapter extends RecyclerView.Adapter<messanger_home_adapter.sViewHolder>{
    private List<Messanger> list;
    private Context context;
    public messanger_home_adapter(List<Messanger> list,Context context) {
        this.list = list;this.context=context;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView t1,t2,t3,t4,t5;
        public CardView cv;
        public ImageView imageView;

        public sViewHolder(View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.date);
            t2=itemView.findViewById(R.id.time);
            t3=itemView.findViewById(R.id.tmail);
            t4=itemView.findViewById(R.id.tuser);
            t5=itemView.findViewById(R.id.tid);
            imageView=itemView.findViewById(R.id.imageView);
            View B=itemView.findViewById(R.id.tid);
            B.setVisibility(View.GONE);

            cv=(CardView) itemView.findViewById(R.id.cv);

        }


    }
    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messenger_home_item,parent,false);
        return new sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final messanger_home_adapter.sViewHolder holder, int position) {

        Messanger temp=list.get(position);
        //holder.setIsRecyclable(false);
        holder.t1.setText("");
        holder.t2.setText(temp.getTime_());
        holder.t3.setText(temp.getMail());
        holder.t4.setText(temp.getSender_name());
        holder.t5.setText(temp.getSender());
        StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
        StorageReference forestRef;

        storageRef = FirebaseStorage.getInstance().getReference();
        forestRef = storageRef.child("images/"+holder.t5.getText().toString()+".jpg");
        Glide.with(context).using(new FirebaseImageLoader())
                .load(forestRef)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new CircleTransform(context))
                .into(holder.imageView);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpeningActivity.chatter=holder.t5.getText().toString();
                OpeningActivity.chattername=holder.t4.getText().toString();
                Intent my=new Intent(context,chatbox.class);
                context.startActivity(my);
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
