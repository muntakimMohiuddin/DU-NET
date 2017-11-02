package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 11/2/17.
 */
import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Rafi on 28-Oct-17.
 */

public class FileAdapter extends ArrayAdapter<Document> {
    private Activity activity;
    private ArrayList<Document> documents;
    private static LayoutInflater inflater;

    public FileAdapter(Activity activity, int resourceID, ArrayList<Document> documents){
        super(activity,resourceID,documents);
        this.activity = activity;
        this.documents = documents;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){
        return documents.size();
    }

    public Document getItem(int position){
        return documents.get(position);
    }

    public static class ViewHolder {
        public TextView docName;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        final ViewHolder holder;

        v = inflater.inflate(R.layout.document_item, null);
        holder = new ViewHolder();

        holder.docName = (TextView) v.findViewById(R.id.tv_file_name);
        holder.docName.setText(documents.get(position).getTitle());

        return v;
    }
}