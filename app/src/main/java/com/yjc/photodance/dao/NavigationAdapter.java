package com.yjc.photodance.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjc.photodance.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class NavigationAdapter extends ArrayAdapter<Navigation> {

    private int resourceId;

    public NavigationAdapter(@NonNull Context context, int resource, List<Navigation> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Navigation navigation = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.navigationImage = view.findViewById(R.id.navigation_image);
            viewHolder.navigationTitle = view.findViewById(R.id.navigation_title);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.navigationImage.setImageResource(navigation.getImageId());
        viewHolder.navigationTitle.setText(navigation.getTitle());
        return view;
    }


    class ViewHolder {
        ImageView navigationImage;
        TextView navigationTitle;
    }
}
