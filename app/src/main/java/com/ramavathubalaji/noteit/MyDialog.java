package com.ramavathubalaji.noteit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by RAMAVATHU BALAJI on 26-02-2018.
 */

public  class MyDialog extends Dialog implements View.OnClickListener {
    public Activity c;
    public Dialog d;
    public ImageButton list;
    public ImageButton text;
    int count=0;

    public MyDialog(Activity act) {
        super(act);
        c = act;
        setTitle("Add note");

        setContentView(R.layout.custom_dialog);
        text = (ImageButton) findViewById(R.id.imgbtntxt);
        text.setOnClickListener(this);
        list = (ImageButton) findViewById(R.id.imgbtnlist);
        list.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == list)
            dismiss();
        else
        {

            Intent i = new Intent(c, Main2Activity.class);
            c.startActivity(i);

            dismiss();


        }
    }


    }

