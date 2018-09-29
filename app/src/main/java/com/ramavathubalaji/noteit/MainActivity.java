package com.ramavathubalaji.noteit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity
{
    private Toolbar tlbar;
    private ListView items;
    private MyListView mylist;
    private TextView tvData;
    private TextView tvid;
    DBConstants dbConstants=new DBConstants();
    Cursor c;


    TextDatabase db;
    ArrayList<String> temp=new ArrayList<String>();
    ArrayList<String> idList=new ArrayList<String>();
    //ArrayList<String> titleList=new ArrayList<String>();
    ArrayList<String> textList=new ArrayList<String>();
    //private MyListView<String> adapter;
    MyListView adapter;
    private ArrayList<String> itemList=new ArrayList<String>();



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if (id == R.id.action_addnote) {


            MyDialog cdd=new MyDialog(MainActivity.this) {
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                }

            };
           cdd.show();

        }
        if (id == R.id.action_search) {
            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        if(id==R.id.btnrefresh)
        {
            updateList();
        }
        return super.onOptionsItemSelected(item);
    }
    protected void removeItemFromList(int gtid,int position) {
        final int deletePosition = position;
        final int gotid=gtid;
        AlertDialog.Builder alert = new AlertDialog.Builder(
                MainActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                try
                {
                    itemList.remove(deletePosition);
                   adapter.notifyDataSetChanged();
                    db.open();
                  db.deleteEntry(String.valueOf(gotid));

                    //start here item not getting deleted from database
                   temp=db.getData();


                    Arrays.toString(temp.toArray());
                    tvData.setText(Arrays.toString(temp.toArray()));
                    db.close();

                }
                catch (SQLException e){
                    Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
                //adapter.notifyDataSetInvalidated();

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }
    public void updateList()
    {
        db.open();
        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList=db.getData();
        adapter=new MyListView(this,itemList);
        //adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemList);            //setting adapter
        items.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        db.close();

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tlbar= (Toolbar) findViewById(R.id.tlbar);
        tvData=(TextView) findViewById(R.id.textView5);
        //tvid=(TextView) findViewById(R.id.tvid);
        items=(ListView) findViewById(R.id.lstview);
        items.setLongClickable(true);

        db=new TextDatabase(this);
        setSupportActionBar(tlbar);

        SharedPreferences sharedPreferences=this.getSharedPreferences("A",MODE_PRIVATE);
       Boolean m2finished= sharedPreferences.getBoolean("isfinished",false);
        Boolean clickfinished=sharedPreferences.getBoolean("editfinished",false);
        try
        {
            Bundle msgint=getIntent().getExtras();
            Boolean val=msgint.getBoolean("finished");

            //Boolean Clickfinished=msgint.getBoolean("Editfinished");

            //String main2=dbConstants.main2finished;
            //Toast.makeText(MainActivity.this,String.valueOf(main2),Toast.LENGTH_SHORT).show();
            if(val)
            {
                updateList();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        try
        {
            db.open();
            itemList=db.getData();
            tvData.setText(Arrays.toString(itemList.toArray()));
            db.close();
            updateList();

            items.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View view,int position,long id)
                {
                    try
                    {
                        String tit = items.getItemAtPosition(position).toString();
                        //Toast.makeText(MainActivity.this,val, Toast.LENGTH_LONG).show();
                        Intent myIntent=new Intent(view.getContext(),ClickItemActivity.class);
                        db.open();
                        idList=db.getIDList();
                        textList=db.getTexts();
                        String gotid=idList.get(position);
                        String val=textList.get(position);
                        myIntent.putExtra("id",gotid);
                        myIntent.putExtra("text",val);
                        myIntent.putExtra("title",tit);
                        startActivity(myIntent);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            });

        }
        catch (SQLException e)
        {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                db.open();
                idList=db.getIDList();
                String gotid=idList.get(pos);
                System.out.println(Arrays.toString(idList.toArray()));
                db.close();
                removeItemFromList(Integer.parseInt(gotid),pos);
                return true;
            }
        });

    }

}
