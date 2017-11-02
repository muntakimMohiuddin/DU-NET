package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/24/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by aniomi on 9/26/17.
 */

public class messanger_search_adapter extends RecyclerView.Adapter<messanger_search_adapter.sViewHolder> {



    private List<Students> list;
    private Context context;
    public messanger_search_adapter(List<Students> list,Context context) {
        this.list = list;this.context=context;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView t1,t2;
        public ImageView imageView;
        public ImageButton imdb;
        public CardView cv;
        public sViewHolder(View itemView) {
            super(itemView);
            t1=(TextView) itemView.findViewById(R.id.t1);
            t2=(TextView) itemView.findViewById(R.id.t2);
            imageView=(ImageView) itemView.findViewById(R.id.imageView);
            imdb=itemView.findViewById(R.id.imdb);
            //text4=(TextView) itemView.findViewById(R.id.text4);
            cv=(CardView) itemView.findViewById(R.id.cv);
        }


    }
    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messanger_search_item,parent,false);
        return new sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final sViewHolder holder, int position) {

        Students temp=list.get(position);
        //holder.setIsRecyclable(false);
        holder.t1.setText(temp.getName());
        holder.t2.setText(temp.getUid());
        View B=holder.t2;
        B.setVisibility(View.GONE);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference forestRef = storageRef.child("images/"+holder.t2.getText().toString()+".jpg");
        Glide.with(context).using(new FirebaseImageLoader())
                .load(forestRef)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new CircleTransform(context))
                .into(holder.imageView);

        holder.imdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpeningActivity.chatter=holder.t2.getText().toString();
                OpeningActivity.chattername=holder.t1.getText().toString();
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

