package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/2/17.
 */

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import static android.view.LayoutInflater.from;
import static com.example.aniomi.myapplication.Students.lat;
import static com.example.aniomi.myapplication.Students.lng;

/**
 * Created by aniomi on 10/1/17.
 */

public class FindAdapter  extends RecyclerView.Adapter<FindAdapter.sViewHolder>{

    private List<Students> list;
    private Context context;
    DatabaseReference mDatabase;
    HashMap<String ,String > hashMap = new HashMap<String, String>();
    public FindAdapter(List<Students> list,Context context) {
        this.list = list;this.context=context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ShareLocation").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String shareLocation = new String();
                String id = new String();
                id = dataSnapshot.getKey();
                shareLocation = dataSnapshot.getValue(String.class);
                hashMap.put(id,shareLocation);
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
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text1,text2,text3,text4,text5,text6;
        public ImageButton rlay,b2;
        public CardView cv;
        public Button find;
        ImageView profile;
        public sViewHolder(View itemView) {
            super(itemView);
            text1=(TextView) itemView.findViewById(R.id.t1);
            text2=(TextView) itemView.findViewById(R.id.t2);
            text3=(TextView) itemView.findViewById(R.id.t3);
            text4=(TextView) itemView.findViewById(R.id.t5);
            text5=(TextView) itemView.findViewById(R.id.t6);
            text6=(TextView) itemView.findViewById(R.id.t7);
            find = itemView.findViewById(R.id.button3);

            profile=itemView.findViewById(R.id.profile);
            rlay= itemView.findViewById(R.id.b1);
            b2=itemView.findViewById(R.id.b2);
            View B=itemView.findViewById(R.id.t5);
            B.setVisibility(View.GONE);
            B=itemView.findViewById(R.id.t6);
            B.setVisibility(View.GONE);
            B=itemView.findViewById(R.id.t7);
            B.setVisibility(View.GONE);


            cv=(CardView) itemView.findViewById(R.id.cv);
        }


    }
    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = from(parent.getContext()).inflate(R.layout.find_item,parent,false);
        return new sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final sViewHolder holder, int position) {

        final Students temp=list.get(position);
        //holder.setIsRecyclable(false);
        holder.text1.setText(temp.getName());
        holder.text2.setText(temp.getDept());
        holder.text3.setText(temp.getYear());
        holder.text4.setText(temp.getUid());
        holder.text5.setText(temp.getBlood());
        holder.text6.setText(temp.getLoc());
        MainActivity.setImageFromStorage(context,"images/"+holder.text4.getText().toString()+".jpg",holder.profile);
        holder.rlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(context);
                final View mView=LayoutInflater.from(context).inflate(R.layout.activity_popupfind,null);

                String t=holder.text1.getText().toString();
                t=t.replace("User Name : ","");

                final EditText ett=(EditText) mView.findViewById(R.id.et2);
                Button send=(Button) mView.findViewById(R.id.b9);
                final String uid=holder.text4.getText().toString();
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
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(context);
                final View mView=LayoutInflater.from(context).inflate(R.layout.fragment_profile_my,null);
                final ImageView imageView=(ImageView) mView.findViewById(R.id.imageView);
                MainActivity.setImageFromStorage(context,"images/"+holder.text4.getText().toString()+".jpg",imageView);
                TextView t1,t2,t3,t4,t5;
                t1=mView.findViewById(R.id.t1);
                t2=mView.findViewById(R.id.t2);
                t3=mView.findViewById(R.id.t3);
                t4=mView.findViewById(R.id.t4);
                t5=mView.findViewById(R.id.t5);
                t1.setText(holder.text1.getText().toString());
                t2.setText(holder.text2.getText().toString());
                t3.setText(holder.text3.getText().toString());
                t4.setText("Blood Group : "+holder.text5.getText().toString());
                t5.setText("location : "+holder.text6.getText().toString());
                mBuilder.setView(mView);
                AlertDialog alertDialog=mBuilder.create();
                alertDialog.show();
            }
        });

        holder.find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hashMap.containsKey(temp.getUid())) {
                    if (hashMap.get(temp.getUid()).equals("true")) {
                        String words[] = temp.location.split(",");
                        lat = Double.parseDouble(words[0]);
                        lng = Double.parseDouble(words[1]);
                        Intent intent = new Intent(context, FindPeople.class);
                        context.startActivity(intent);
                    } else {
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                        builder2.setMessage("Access Not Granted");
                        builder2.setCancelable(true);
                        AlertDialog alert11 = builder2.create();
                        alert11.show();
                    }
                }
                else
                {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                    builder2.setMessage("Access Not Granted");
                    builder2.setCancelable(true);
                    AlertDialog alert11 = builder2.create();
                    alert11.show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

