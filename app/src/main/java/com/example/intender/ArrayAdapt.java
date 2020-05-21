package com.example.intender;

import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ArrayAdapt extends ArrayAdapter<Cards> {
    Context context;
    public ArrayAdapt(Context context, int resourceID, List<Cards>items) {
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
        switch (card_item.getImageprofileURL())
        {
            case "default":
                Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher).into(image);
                break;
            default:
                    Glide.with(convertView.getContext()).load(card_item.getImageprofileURL()).into(image);
                    break;
        }


        return convertView;
}
}
