package com.example.aniomi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Rafi on 10-Oct-17.
 */

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<String> items;

    public Adapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.bus_trip,parent,false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String[] s = items.get(position).split("-");
        String[] s2 = s[1].split("\\+");
        if(s.length==2) {
            ((Item) holder).busName.setText(s[0]);
            ((Item)holder).time.setText(s2[0] + ":" + s2[1] + " " + s2[2]);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Item extends RecyclerView.ViewHolder{
        View view;
        TextView busName, time;


        public Item(final View itemView) {
            super(itemView);
            view = itemView;
            busName = (TextView) itemView.findViewById(R.id.bus_name);
            time = (TextView) itemView.findViewById(R.id.time);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowRoute.busName = busName.getText().toString().trim();
                    Intent intent = new Intent(context,ShowRoute.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}