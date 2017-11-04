package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/20/17.
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

public class chatbox_adapter extends RecyclerView.Adapter<chatbox_adapter.sViewHolder> {



    private List<Messanger> list;
    private Context context;
    private RelativeLayout r1,r2;
    public chatbox_adapter(List<Messanger> list,Context context) {
        this.list = list;this.context=context;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {

        public TextView text1,text2;
        public CardView cv;
        public ImageView imageView1,imageView2;
        public sViewHolder(View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.t1);
            text2=itemView.findViewById(R.id.t2);
            imageView1=itemView.findViewById(R.id.imageView1);
            imageView2=itemView.findViewById(R.id.imageView2);
            cv=itemView.findViewById(R.id.cv);

        }


    }
    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        return new sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(sViewHolder holder, int position) {

        Messanger temp=list.get(position);
        if(temp.sender.equals(Students.current.uid))
        {
            holder.text1.setVisibility(View.GONE);
            holder.imageView1.setVisibility(View.GONE);
            holder.text2.setVisibility(View.VISIBLE);
            holder.imageView2.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.text2.setVisibility(View.GONE);
            holder.imageView2.setVisibility(View.GONE);
            holder.text1.setVisibility(View.VISIBLE);
            holder.imageView1.setVisibility(View.VISIBLE);
        }
        holder.text1.setText(temp.getMail());
        holder.text2.setText(temp.getMail());
        MainActivity.setImageFromStorage(context,"images/"+temp.sender+".jpg",holder.imageView1);
        MainActivity.setImageFromStorage(context,"images/"+temp.sender+".jpg",holder.imageView2);

            /*View B=holder.text2;
            B.setVisibility(View.INVISIBLE);
            B=holder.imageView2;
            B.setVisibility(View.INVISIBLE);*/
            //holder.text2.setText("");
            //holder.text1.setText(temp.getMail());
            /*TextView someView = holder.text1;
            View root = someView.getRootView();*/
            /*if(temp.sender.equals(Students.current.uid)){
            TextView layout =holder.text1;
            layout.setBackgroundResource(R.drawable.receivers);layout.setTextColor(Color.parseColor("#000000"));}
            else
            {
                TextView layout =holder.text1;
                layout.setBackgroundResource(R.drawable.senders);layout.setTextColor(Color.parseColor("#ffffff"));
            }
            StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
            StorageReference forestRef;

            storageRef = FirebaseStorage.getInstance().getReference();
            forestRef = storageRef.child("images/"+temp.sender+".jpg");
            Glide.with(context).using(new FirebaseImageLoader())
                    .load(forestRef)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(new CircleTransform(context))
                    .into(holder.imageView1);*/


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}