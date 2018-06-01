package com.technoexponent.sampleandroidapp.AdapterClass;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterSpinner extends ArrayAdapter<String> {

    private List<String> objects;
    private Activity activity;
    private boolean isWhiteBack=false;


    public CustomAdapterSpinner(Activity activity, int resourceId,
                                List<String> objects,boolean isWhiteBack) {
        super(activity, resourceId, objects);
        this.objects = objects;
        this.activity = activity;
        this.isWhiteBack = isWhiteBack;

    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater) activity.getSystemService(  Context.LAYOUT_INFLATER_SERVICE );
        View row = null;


            //  logo.setImageResource(R.drawable.duch);






        return row;
    }


}
