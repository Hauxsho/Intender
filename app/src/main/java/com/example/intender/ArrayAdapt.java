package com.example.intender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapt extends ArrayAdapter<Cards> {
    Context context;
    public ArrayAdapt(Context context, int resourceID, int text, List<Cards>items) {
        super(context,resourceID,items);
    }

    public View getView(int position , View convertView , ViewGroup parent)
    {
        Cards card_item = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView name = (TextView)convertView.findViewById(R.id.helloText);
        ImageView image = (ImageView)convertView.findViewById(R.id.swipeImage);
        name.setText(card_item.getName());
        image.setImageResource(R.mipmap.ic_launcher);
        return convertView;
}
}
