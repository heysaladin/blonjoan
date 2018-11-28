package com.saladinid.blonjoan.handler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//import com.codingdemos.flowers.R;
import com.saladinid.blonjoan.R;

public class ListAdapter extends ArrayAdapter < JSONObject > {

    private static final String TAG = "ListAdapter";
    private int vg;
    private ArrayList < JSONObject > list;
    private Context context;

    public ListAdapter(Context context, int vg, int id, ArrayList < JSONObject > list) {
        super(context, vg, id, list);
        this.context = context;
        this.vg = vg;
        this.list = list;
    }

    private View.OnClickListener myButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View parentRow = (View) v.getParent();
            ListView listView = (ListView) parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);
        }
    };

    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View itemView = null;
        if (inflater != null) {
            itemView = inflater.inflate(vg, parent, false);
        }
        final TextView txtRestaurantTitle;
        txtRestaurantTitle = (TextView) itemView.findViewById(R.id.karma_resorts_group_title);
        TextView txtRestaurant = (TextView) itemView.findViewById(R.id.karma_resorts_item);
        try {
            txtRestaurant.setText(list.get(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemView;

    }

    @Override
    public boolean isEnabled(int position) {
        boolean returnVal = true;
        returnVal = true;
        return returnVal;
    }

}