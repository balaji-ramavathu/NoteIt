package com.ramavathubalaji.noteit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {

    EditText editText,etTitle;
    public Toolbar tbTitle;
    public Activity m1;
    public String title;
    public int k=0;
    int id=0;
    ArrayList arrayList=new ArrayList();
    //ArrayList olddata=new ArrayList<String>();
    DBConstants dbConstants=new DBConstants();
    SQLiteDatabase db;
    DBHelper dbh;
    TextDatabase tdb;
    public static final String MY_TEXT_DATA="com.ramavathubalaji.noteit.text";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.etTitle:
                storeTitle();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if (id == R.id.action_addnote) {



        }
        if (id == R.id.action_search) {

        }
        return super.onOptionsItemSelected(item);
    }*/

    public void storeTitle()
    {
        title=etTitle.getText().toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText=(EditText) findViewById(R.id.edText);
        tbTitle=(Toolbar) findViewById(R.id.tbTitle);
        //etTitle=(EditText) findViewById(R.id.etTitle);
        dbh=new DBHelper(this);
        tdb=new TextDatabase(this);
        setSupportActionBar(tbTitle);

        ActionBar action = getSupportActionBar();
        action.setDisplayShowCustomEnabled(true);
        action.setCustomView(R.layout.title_et);
        etTitle = (EditText)action.getCustomView().findViewById(R.id.etTitle);

        etTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    return true;
                }
                return false;
            }
        });


        etTitle.requestFocus();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        try
        {
            String txt=editText.getText().toString();
            title=etTitle.getText().toString();
            Toast.makeText(Main2Activity.this,title, Toast.LENGTH_LONG).show();
            tdb.open();

            arrayList=tdb.getData();
            tdb.createEntry(title,txt);
            tdb.close();
        }
        catch (SQLException e)
        {
            Toast.makeText(Main2Activity.this,e.getMessage(), Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(Main2Activity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        /*Intent myIntent=new Intent(Main2Activity.this,MainActivity.class);
        boolean ifinished=true;
        myIntent.putExtra("finished",ifinished);
        startActivity(myIntent);*/
        SharedPreferences isfinished=getSharedPreferences("A", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=isfinished.edit();
        editor.putBoolean("isfinished",true);
        editor.commit();

        //dbConstants.main2finished="true";
        finish();


    }
}
