package com.geval6.techscreener.Source.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Modals.DrawerItems;


public class NavigationDrawerAdapter extends ArrayAdapter<DrawerItems> {
    Context context;
    int layoutResourceId;
    DrawerItems data[] = null;

    public NavigationDrawerAdapter(Context context, int layoutResourceId, DrawerItems[] data) {

        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            TextView textViewName = (TextView) convertView.findViewById(R.id.textViewName);
            DrawerItems folder = data[position];
            imageViewIcon.setImageResource(folder.icon);
            textViewName.setText(folder.name);

        return convertView;
    }
}
