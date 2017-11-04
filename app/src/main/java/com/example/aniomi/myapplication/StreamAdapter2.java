package com.example.aniomi.myapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
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

import java.util.List;

/**
 * Created by aniomi on 10/1/17.
 */

public class StreamAdapter2  extends RecyclerView.Adapter<StreamAdapter2.sViewHolder>{

    private List<Streamo> list;
    private Context context;
    public StreamAdapter2(List<Streamo> list,Context context) {
        this.list = list;this.context=context;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView t1,t2,t3;
        public ImageButton b1,b2;;
        public CardView cv;
        public ImageView imageView;
        public sViewHolder(View itemView) {
            super(itemView);
            t1=(TextView) itemView.findViewById(R.id.t1);
            t2=(TextView) itemView.findViewById(R.id.t2);
            t3=(TextView) itemView.findViewById(R.id.t3);
            b1=itemView.findViewById(R.id.b1);
            b2=itemView.findViewById(R.id.b2);
            imageView=itemView.findViewById(R.id.imageView);
            View B=itemView.findViewById(R.id.t2);
            B.setVisibility(View.GONE);
            cv=(CardView) itemView.findViewById(R.id.cv);
        }


    }
    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stream_item2,parent,false);
        return new sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final sViewHolder holder, int position) {

        Streamo temp=list.get(position);
        //holder.setIsRecyclable(false);
        holder.t1.setText(temp.getName());
        holder.t2.setText(temp.getSen());
        holder.t3.setText(temp.getMai());
       MainActivity.setImageFromStorage(context,"images/"+holder.t2.getText().toString()+".jpg",holder.imageView);
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(context);
                final View mView=LayoutInflater.from(context).inflate(R.layout.activity_popupfind,null);

                String t=holder.t1.getText().toString();


                final EditText ett=(EditText) mView.findViewById(R.id.et2);
                Button send=(Button) mView.findViewById(R.id.b9);
                final String uid=holder.t2.getText().toString();
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mai=ett.getText().toString();
                        String name=Students.current.name;
                        String sen=Students.current.uid;
                        String rec=uid;
                        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("streams");
                        databaseUsers.push().setValue(new Streamo(mai,sen,rec,name));


                    }
                });
                mBuilder.setView(mView);
                AlertDialog alertDialog=mBuilder.create();
                alertDialog.show();
            }
        });
        holder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage(holder.t3.getText().toString());

                builder1.setCancelable(true);
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
