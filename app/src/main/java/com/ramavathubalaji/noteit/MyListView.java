package com.ramavathubalaji.noteit;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RAMAVATHU BALAJI on 27-02-2018.
 */

public class MyListView extends ArrayAdapter<String> {


    private final Activity context;
    private ArrayList<String> itemList=new ArrayList<String>();

    public MyListView(Activity context,ArrayList itemList) {
        super(context, R.layout.list_view,itemList);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemList=itemList;


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_view, null,true);

        TextView textItem = (TextView) rowView.findViewById(R.id.txtview);

        textItem.setText(itemList.get(position));
        //left here dont know from where position value comes from

        return rowView;

    };
}

