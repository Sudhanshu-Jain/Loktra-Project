package com.example.gitapi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sudhanshu on 3/6/16.
 */
public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Commit> commitList;

    public CustomAdapter(Context context, List<Commit> commitList) {
        this.context = context;
        this.commitList = commitList;
    }

    @Override
    public int getCount() {
        return commitList.size();
    }

    @Override
    public Object getItem(int position) {
        return commitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.title1);
            holder.sha = (TextView) convertView.findViewById(R.id.title2);
            holder.message = (TextView) convertView.findViewById(R.id.title3);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Commit rowItem = (Commit) getItem(position);

        holder.name.setText(rowItem.getUsername());
        holder.sha.setText(rowItem.getSha());
        holder.message.setText(rowItem.getMessage());

        return convertView;
    }

    public class ViewHolder{
        TextView name;
        TextView sha;
        TextView message;
    }
}
