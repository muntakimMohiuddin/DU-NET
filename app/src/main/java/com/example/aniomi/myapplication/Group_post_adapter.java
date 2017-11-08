package com.example.aniomi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by aniomi on 10/1/17.
 */

public class Group_post_adapter  extends RecyclerView.Adapter<Group_post_adapter.sViewHolder> {

    private List<posto> list;
    private Context context;

    public Group_post_adapter(List<posto> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class sViewHolder extends RecyclerView.ViewHolder {
        public TextView t1, t2, t3, t4, t5;
        public ImageView imageView, pic, image;
        public ImageButton b1, b2, imdb, edit;
        int cp = 1, cnt;
        public CardView cv;

        public sViewHolder(View itemView) {
            super(itemView);
            t1 = (TextView) itemView.findViewById(R.id.t1);
            t2 = (TextView) itemView.findViewById(R.id.t2);
            t3 = (TextView) itemView.findViewById(R.id.t3);
            t4 = (TextView) itemView.findViewById(R.id.t4);
            t5 = (TextView) itemView.findViewById(R.id.t5);
            b1 = itemView.findViewById(R.id.b1);
            b2 = itemView.findViewById(R.id.b2);
            imdb = itemView.findViewById(R.id.imdb);
            imageView = itemView.findViewById(R.id.imageView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            image = (ImageView) itemView.findViewById(R.id.pic);
            edit = itemView.findViewById(R.id.edit);
            imdb.setVisibility(View.GONE);
        }


    }

    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_group, parent, false);
        return new sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final sViewHolder holder, final int position) {

        final posto temp = list.get(position);
        holder.cnt = Integer.parseInt(temp.getCnt());

        //holder.setIsRecyclable(false);
        //holder.text4.setText("Send");
        holder.t1.setText(((temp.getSen())));
        //holder.t2.setText(temp.getD());
        //holder.t3.setText(temp.getT());
        holder.t4.setText(temp.getDescription());
        // holder.t5.setText(temp.getDept());

        StorageReference storageRef;
        StorageReference forestRef;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String userID = Students.current.getUid();

        if (userID.equals(temp.dept))
            holder.edit.setVisibility(View.VISIBLE);
        else
            holder.edit.setVisibility(View.INVISIBLE);

        storageRef = FirebaseStorage.getInstance().getReference();
        forestRef = storageRef.child("images/" + temp.getDept() + ".jpg");
        Glide.with(context).using(new FirebaseImageLoader())
                .load(forestRef)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new CircleTransform(context))
                .into(holder.image);

        if (holder.cnt == 0) {
            holder.b1.setVisibility(View.GONE);
            holder.b2.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
        } else {
            if (holder.cnt == 1) {
                holder.b1.setVisibility(View.GONE);
                holder.b2.setVisibility(View.GONE);
            } else {
                holder.b1.setVisibility(View.VISIBLE);
                holder.b2.setVisibility(View.VISIBLE);
            }
            holder.imageView.setVisibility(View.VISIBLE);
            storageRef = FirebaseStorage.getInstance().getReference();
            forestRef = storageRef.child("Group_post_image/" + temp.id + (posto.b[position] + 1) + ".jpg");
            Glide.with(context).using(new FirebaseImageLoader())
                    .load(forestRef)
                    .into(holder.imageView);
            holder.b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (posto.b[position] + 1 == 1) {
                        posto.b[position] = posto.b[position] - 1;
                    } else posto.b[position]--;
                    StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
                    StorageReference forestRef;

                    storageRef = FirebaseStorage.getInstance().getReference();
                    forestRef = storageRef.child("Group_post_image/" + temp.id + (posto.b[position] + 1) + ".jpg");
                    Glide.with(context).using(new FirebaseImageLoader())
                            .load(forestRef)
                            .into(holder.imageView);

                }
            });
        }

            holder.b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (posto.b[position] + 1 == holder.cnt) {
                        posto.b[position] = 0;
                    } else posto.b[position]++;

                    StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
                    StorageReference forestRef;

                    storageRef = FirebaseStorage.getInstance().getReference();
                    forestRef = storageRef.child("Group_post_image/" + temp.id + (posto.b[position] + 1) + ".jpg");
                    Glide.with(context).using(new FirebaseImageLoader())
                            .load(forestRef)
                            .into(holder.imageView);

                }
            });


            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    posto.ttext = holder.t4.getText().toString();
                    Intent intent = new Intent(context, Group_post_creat.class);
                    context.startActivity(intent);
                }
            });

            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Group_post_comment.currentpost = temp;
                    Group_details.groupPost = true;
                    Intent my = new Intent(context, Group_post_comment.class);
                    context.startActivity(my);
                }
            });

            holder.t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder mBuilder=new AlertDialog.Builder(context);
                    final View mView=LayoutInflater.from(context).inflate(R.layout.fragment_profile_my,null);
                    final ImageView imageView=(ImageView) mView.findViewById(R.id.imageView);
                    MainActivity.setImageFromStorage(context,"images/"+temp.getDept()+".jpg",imageView);
                    final TextView t1,t2,t3,t4,t5;
                    t1=mView.findViewById(R.id.t1);
                    t2=mView.findViewById(R.id.t2);
                    t3=mView.findViewById(R.id.t3);
                    t4=mView.findViewById(R.id.t4);
                    t5=mView.findViewById(R.id.t5);
                    t5.setVisibility(View.GONE);

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("Users").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Students tempStudent=new Students();
                            tempStudent=dataSnapshot.getValue(Students.class);
                            if(tempStudent.getUid().equals(temp.getDept())){
                                t1.setText(tempStudent.name);
                                t2.setText(tempStudent.dept);
                                t3.setText(tempStudent.year+" Year");
                                t4.setText("Blood Group : "+tempStudent.blood);
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mBuilder.setView(mView);
                    AlertDialog alertDialog=mBuilder.create();
                    alertDialog.show();
                }
            });

    }
        @Override
        public int getItemCount() {
            return list.size();
        }

    }
