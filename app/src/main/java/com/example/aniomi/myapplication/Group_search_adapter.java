package com.example.aniomi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Group_details.tname = temp.name;
                Group_details.tabout = temp.about;
                Group_details.toperner = temp.operner;
                Group_details.tadminPass = temp.adminPass;
                Group_details.tuserPass = temp.userPass;
                Group_details.tgroupID = temp.groupID;

                Intent intent = new Intent(context,Group_post.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
