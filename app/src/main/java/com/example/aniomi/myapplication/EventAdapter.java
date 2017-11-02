package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/24/17.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by asif on 10/3/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.sViewHolder> {

    private List<EventDetails> list;
    private Context context;
    private DatabaseReference mDatabase ,mDatabaseCount;
    private FirebaseAuth mAuth;
    public EventAdapter(List<EventDetails> list) {
        this.list = list;
        this.context = context;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text1,text2,text3,text4;
        public CheckBox checkBox1,checkBox2,checkBox3;
        public ImageButton add,arrow,edit;
        public RadioGroup rB;
        public CardView cv;
        public sViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            text1=(TextView) itemView.findViewById(R.id.nameCV);
            text2=(TextView) itemView.findViewById(R.id.dateCV);
            text3=(TextView) itemView.findViewById(R.id.detailsCV);
            text4=(TextView) itemView.findViewById(R.id.locCV);
            cv=(CardView) itemView.findViewById(R.id.cv);
            checkBox1 =(CheckBox) itemView.findViewById(R.id.checkBox2); // NOT GOING
            checkBox2 = (CheckBox) itemView.findViewById(R.id.checkBox3); // MAYBE
            checkBox3 = (CheckBox) itemView.findViewById(R.id.checkBox4); // GOING
            checkBox3.setChecked(true);
            add = itemView.findViewById(R.id.addPerson);
            edit = itemView.findViewById(R.id.edit);
            arrow = itemView.findViewById(R.id.arrow);
        }
    }

    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final sViewHolder holder, int position) {

        final EventDetails temp=list.get(position);
        holder.text1.setText(temp.getEventName());
        holder.text2.setText(temp.getEventTime()+" , "+temp.getEventDate());
        holder.text3.setText(temp.getEventDescription());
        holder.text4.setText(temp.getEventLoc());

        mAuth = FirebaseAuth.getInstance();
        final String userID = Students.current.getUid();

        if(userID.equals(temp.openerID))
            holder.edit.setVisibility(View.VISIBLE);
        else
            holder.edit.setVisibility(View.INVISIBLE);

        final int[] count_Attending={0};
        final int[] count_Not_Interested={0};
        final int[] count_Interested = {0};
        final String eventID = temp.eventID;
        final String[] s = new String[1];

        mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Attending").child(eventID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count_Attending[0] = 0;
                for(DataSnapshot users : dataSnapshot.getChildren())
                {
                    count_Attending[0]++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Not Interested").child(eventID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count_Not_Interested[0]=0;
                for(DataSnapshot users : dataSnapshot.getChildren())
                {
                    count_Not_Interested[0] ++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Interested").child(eventID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count_Interested[0]=0;
                for(DataSnapshot users : dataSnapshot.getChildren())
                {
                    count_Interested[0] ++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                String string = "<b>"+temp.getEventName()+"</b> <br>";
                string = string+temp.getEventTime()+" , "+temp.getEventDate()+"<br>";
                string = string+temp.getEventLoc()+"<br><br>"+temp.getEventDescription();
                builder1.setMessage(Html.fromHtml(string));
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        holder.text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                String string = "<b>"+temp.getEventName()+"</b> <br>";
                string = string+temp.getEventTime()+" , "+temp.getEventDate()+"<br>";
                string = string+temp.getEventLoc()+"<br><br>"+temp.getEventDescription();
                builder1.setMessage(Html.fromHtml(string));
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        holder.checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.checkBox3.isChecked())
                {
                    holder.checkBox2.setChecked(false);
                    holder.checkBox1.setChecked(false);

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Interested").child(temp.eventID);
                    mDatabase.child(userID).removeValue();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Not Interested").child(temp.eventID);
                    mDatabase.child(userID).removeValue();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Attending").child(temp.eventID);
                    mDatabase.child(userID).setValue(true);

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Attending_Events").child(userID);
                    mDatabase.child(temp.eventID).setValue(temp);

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Not_Interested_Events").child(userID);
                    mDatabase.child(temp.eventID).removeValue();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Interested_Events").child(userID);
                    mDatabase.child(temp.eventID).removeValue();
                }
                else
                {
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Attending_Events").child(userID);
                    mDatabase.child(temp.eventID).removeValue();
                }
            }
        });

        holder.checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.checkBox1.isChecked())
                {
                    holder.checkBox2.setChecked(false);
                    holder.checkBox3.setChecked(false);

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Interested").child(temp.eventID);
                    mDatabase.child(userID).removeValue();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Attending").child(temp.eventID);
                    mDatabase.child(userID).removeValue();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Not Interested").child(temp.eventID);
                    mDatabase.child(userID).setValue(true);

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Not_Interested_Events").child(userID);
                    mDatabase.child(temp.eventID).setValue(temp);

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Interested_Events").child(userID);
                    mDatabase.child(temp.eventID).removeValue();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Attending_Events").child(userID);
                    mDatabase.child(temp.eventID).removeValue();
                }
                else
                {
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Not_Interested_Events").child(userID);
                    mDatabase.child(temp.eventID).removeValue();
                }
            }
        });

        holder.checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.checkBox2.isChecked())
                {
                    holder.checkBox1.setChecked(false);
                    holder.checkBox3.setChecked(false);

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Not Interested").child(temp.eventID);
                    mDatabase.child(userID).removeValue();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Attending").child(temp.eventID);
                    mDatabase.child(userID).removeValue();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Interested").child(temp.eventID);
                    mDatabase.child(userID).setValue(true);

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Interested_Events").child(userID);
                    mDatabase.child(temp.eventID).setValue(temp);

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Attending_Events").child(userID);
                    mDatabase.child(temp.eventID).removeValue();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Not_Interested_Events").child(userID);
                    mDatabase.child(temp.eventID).removeValue();
                }
                else
                {
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Interested_Events").child(userID);
                    mDatabase.child(temp.eventID).removeValue();
                }
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventDetails.tName = temp.eventName;
                EventDetails.tDate = temp.eventDate;
                EventDetails.tTime = temp.eventTime;
                EventDetails.tDescription = temp.eventDescription;
                EventDetails.tEventID = temp.eventID;
                EventDetails.tOpenerID = temp.openerID;
                EventDetails.tLoc = temp.eventLoc;
                Intent intent = new Intent(context,InviteActivity.class);
                context.startActivity(intent);            }
        });

        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                String string = "<b>"+temp.getEventName()+"</b> <br> <br>"+count_Attending[0]+" People Going<br>" + count_Interested[0]+" People Maybe<br>"+count_Not_Interested[0]+" People Not Going";
                builder2.setMessage(Html.fromHtml(string));
                builder2.setCancelable(true);

                builder2.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder2.create();
                alert11.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventDetails.tName = temp.eventName;
                EventDetails.tDate = temp.eventDate;
                EventDetails.tTime = temp.eventTime;
                EventDetails.tDescription = temp.eventDescription;
                EventDetails.tEventID = temp.eventID;
                EventDetails.tOpenerID = temp.openerID;
                EventDetails.tLoc = temp.eventLoc;
                Intent intent = new Intent(context,CreatEventsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
