package com.example.aniomi.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by asif on 11/3/17.
 */

public class Group_search_adapter extends RecyclerView.Adapter<Group_search_adapter.sViewHolder> {

    private List<Group_details> list;
    private Context context;
    private DatabaseReference mDatabase ,mDatabaseCount;
    private FirebaseAuth mAuth;
    public Group_search_adapter(List<Group_details> list) {
        this.list = list;
        this.context = context;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public ImageView image;
        public CardView cv;
        public sViewHolder(View itemView){
            super(itemView);
            context = itemView.getContext();
            cv=(CardView) itemView.findViewById(R.id.cv);
            name= (TextView) itemView.findViewById(R.id.grp_name);
            image= (ImageView) itemView.findViewById(R.id.pic);
        }
    }
    @Override
    public Group_search_adapter.sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_cardview,parent,false);
        return new sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Group_search_adapter.sViewHolder holder, int position) {
        final Group_details temp=list.get(position);
        holder.name.setText(temp.getName());

        StorageReference storageRef;
        StorageReference forestRef;

        storageRef = FirebaseStorage.getInstance().getReference();
        forestRef = storageRef.child("GROUPimages/"+temp.groupID+".jpg");
        Glide.with(context).using(new FirebaseImageLoader())
                .load(forestRef)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //.transform(new CircleTransform(context))
                .into(holder.image);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!temp.operner.equals(Students.current.getUid()))
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    View mView = layoutInflater.inflate(R.layout.password_check, null);
                    final TextView text = (TextView) mView.findViewById(R.id.pass);
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "DONE",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    String string = text.getText().toString();
                                    if(string.toString().equals(temp.getUserPass()))
                                    {
                                        Group_details.tname = temp.name;
                                        Group_details.tabout = temp.about;
                                        Group_details.toperner = temp.operner;
                                        Group_details.tadminPass = temp.adminPass;
                                        Group_details.tuserPass = temp.userPass;
                                        Group_details.tgroupID = temp.groupID;

                                        mDatabase = FirebaseDatabase.getInstance().getReference().child("MyGroup").child(Students.current.getUid());
                                        mDatabase.child(temp.groupID).setValue(temp);

                                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Group_People").child(temp.groupID);
                                        mDatabase.child(Students.current.getUid()).setValue(Students.current);

                                        Intent intent = new Intent(context,Group_post.class);
                                        context.startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(context,"Wrong Password",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder1.setView(mView);
                    AlertDialog alert11 = builder1.create();
                    alert11.setCanceledOnTouchOutside(false);
                    alert11.show();
                }
                else
                {
                    Group_details.tname = temp.name;
                    Group_details.tabout = temp.about;
                    Group_details.toperner = temp.operner;
                    Group_details.tadminPass = temp.adminPass;
                    Group_details.tuserPass = temp.userPass;
                    Group_details.tgroupID = temp.groupID;

                    /*mDatabase = FirebaseDatabase.getInstance().getReference().child("MyGroup").child(Students.current.getUid());
                    mDatabase.child(temp.groupID).setValue(temp);*/

                    Intent intent = new Intent(context,Group_post.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
