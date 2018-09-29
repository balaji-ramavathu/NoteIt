package com.ramavathubalaji.noteit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RAMAVATHU BALAJI on 11-03-2018.
 */

public class ClickItemActivity extends AppCompatActivity{
    EditText editText;
    EditText etTitle;
    private Toolbar toolbar;
    private MenuItem item_edit;
    ArrayList idList=new ArrayList<String>();
    private boolean isEditable = false;
    TextDatabase db=new TextDatabase(this);

    //TextDatabase db=new TextDatabase(this);
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        item_edit = menu.findItem(R.id.edit_note);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if (id == R.id.edit_note) {
            editText.setEnabled(true);
            etTitle.setEnabled(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

            return true;

        }
        if (id == R.id.settings) {
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click_item);
        editText=(EditText) findViewById(R.id.etEditItem);

        editText.setEnabled(false);
        toolbar= (Toolbar) findViewById(R.id.tlbarEdit);
        setSupportActionBar(toolbar);
        ActionBar action = getSupportActionBar();
        action.setDisplayShowCustomEnabled(true);
        action.setCustomView(R.layout.title_et);
        etTitle = (EditText)action.getCustomView().findViewById(R.id.etTitle);
        etTitle.setEnabled(false);
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
        try
        {



            Intent posint=getIntent();
            String val=posint.getExtras().getString("text");
            String tit=posint.getExtras().getString("title");
            editText.setText(val);
            etTitle.setText(tit);
            etTitle.setSelection(tit.length());
            editText.setSelection(val.length());//brings cursor at end of text
            //Toast.makeText(ClickItemActivity.this,position,Toast.LENGTH_LONG).show();

        }
        catch (Exception e){}

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pos=getIntent();
        String gotid=pos.getExtras().getString("id");
        String title=etTitle.getText().toString();
        db.open();
       // idList=db.getIDList();
        db.updateEntry(gotid,title,editText.getText().toString());
        db.close();
        editText.setEnabled(false);
        /*Intent myIntent=new Intent(ClickItemActivity.this,MainActivity.class);
        boolean ifinished=true;
        myIntent.putExtra("Editfinished",ifinished);
        startActivity(myIntent);*/
        SharedPreferences isfinished=getSharedPreferences("A", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=isfinished.edit();
        editor.putBoolean("Editfinished",true);
        editor.commit();
        //this.startActivity(new Intent(ClickItemActivity.this,MainActivity.class));
        finish();

    }
}
