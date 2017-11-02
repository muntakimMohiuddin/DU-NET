package com.example.aniomi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static com.example.aniomi.myapplication.post.hm;

/**
 * Created by aniomi on 10/1/17.
 */

public class PostAdapter  extends RecyclerView.Adapter<PostAdapter.sViewHolder>{

    private List<posto> list;
    private Context context;

    public PostAdapter(List<posto> list,Context context) {
        this.list = list;this.context=context;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView t1,t2,t3,t4,t5;
        public ImageView imageView,pic;
        public ImageButton b1,b2,imdb;
        int cp=1,cnt;
        public CardView cv;
        public sViewHolder(View itemView) {
            super(itemView);
            t1=(TextView) itemView.findViewById(R.id.t1);
            t2=(TextView) itemView.findViewById(R.id.t2);
            t3=(TextView) itemView.findViewById(R.id.t3);
            t4=(TextView) itemView.findViewById(R.id.t4);
            t5=(TextView) itemView.findViewById(R.id.t5);
            b1=itemView.findViewById(R.id.b1);
            b2=itemView.findViewById(R.id.b2);
            imdb=itemView.findViewById(R.id.imdb);
            imageView=itemView.findViewById(R.id.imageView);
            cv=(CardView) itemView.findViewById(R.id.cv);
        }


    }
    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        return new sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final sViewHolder holder, final int position) {

        final posto temp=list.get(position);
        holder.cnt=Integer.parseInt(temp.getCnt());

        //holder.setIsRecyclable(false);
        //holder.text4.setText("Send");
        holder.t1.setText(((temp.getSen())));
        holder.t2.setText(temp.getD());
        holder.t3.setText(temp.getT());
        holder.t4.setText(temp.getDescription());
        holder.t5.setText(temp.getDept());

        if(holder.cnt==0)
        {
            holder.b1.setVisibility(View.GONE);
            holder.b2.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
        }
        else
        {
            holder.b1.setVisibility(View.VISIBLE);
            holder.b2.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.VISIBLE);
            StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
            StorageReference forestRef;
            storageRef = FirebaseStorage.getInstance().getReference();
            forestRef = storageRef.child("POSTimages/"+temp.id+(post.a[position]+1)+".jpg");
            Glide.with(context).using(new FirebaseImageLoader())
                    .load(forestRef)
                    .into(holder.imageView);
            holder.b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(post.a[position]+1==1)
                    {
                        post.a[position]=post.a[position]-1;
                    }

                    else post.a[position]--;
                    StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
                    StorageReference forestRef;

                    storageRef = FirebaseStorage.getInstance().getReference();
                    forestRef = storageRef.child("POSTimages/"+temp.id+(post.a[position]+1)+".jpg");
                    Glide.with(context).using(new FirebaseImageLoader())
                            .load(forestRef)
                            .into(holder.imageView);

                }
            });

            holder.b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(post.a[position]+1==holder.cnt)
                    {
                        post.a[position]=0;
                    }

                    else post.a[position]++;

                    StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
                    StorageReference forestRef;

                    storageRef = FirebaseStorage.getInstance().getReference();
                    forestRef = storageRef.child("POSTimages/"+temp.id+(post.a[position]+1)+".jpg");
                    Glide.with(context).using(new FirebaseImageLoader())
                            .load(forestRef)
                            .into(holder.imageView);

                }
            });
            holder.imdb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    post_details.currentpost=temp;
                    Intent my=new Intent(context,post_details.class);
                    context.startActivity(my);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
