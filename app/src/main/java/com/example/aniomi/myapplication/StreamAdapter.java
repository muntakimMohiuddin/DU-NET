package com.example.aniomi.myapplication;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aniomi on 10/1/17.
 */

public class StreamAdapter  extends RecyclerView.Adapter<StreamAdapter.sViewHolder>{

    private List<Streamo> list;
    private Context context;
    public StreamAdapter(List<Streamo> list,Context context) {
        this.list = list;this.context=context;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text1,text2,text3;
        public Button text4;
        public EditText et1;
        public CardView cv;
        public ArrayList<String> list;
        public sViewHolder(View itemView) {
            super(itemView);
            text1=(TextView) itemView.findViewById(R.id.t1);
            text2=(TextView) itemView.findViewById(R.id.t2);
            text3=(TextView) itemView.findViewById(R.id.t3);
            text4=(Button)itemView.findViewById(R.id.b1);
            et1=(EditText) itemView.findViewById(R.id.et1);
            View B=itemView.findViewById(R.id.t3);
            B.setVisibility(View.GONE);
            /*text4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Massege myFragment = new Massege();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, myFragment).addToBackStack(null).commit();

                }
            });*/
            text4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mai=et1.getText().toString();
                    String sen=Students.current.uid;
                    String name=Students.current.name;
                    String rec=text3.getText().toString();
                    DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("streams");
                    databaseUsers.push().setValue(new Streamo(mai,sen,rec,name));
                }
            });
            cv=(CardView) itemView.findViewById(R.id.cv);
        }


    }
    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stream_item,parent,false);
        return new sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final sViewHolder holder, int position) {

        Streamo temp=list.get(position);
        //holder.setIsRecyclable(false);
        holder.text1.setText(temp.getName());
        holder.text2.setText(temp.getMai());
        holder.text3.setText(temp.getSen());
        //holder.text4.setText("Send");
        holder.et1.setVisibility(View.VISIBLE);
        holder.text4.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
