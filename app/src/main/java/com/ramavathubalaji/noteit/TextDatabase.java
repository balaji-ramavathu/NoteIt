package com.ramavathubalaji.noteit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by RAMAVATHU BALAJI on 10-03-2018.
 */

public class TextDatabase {


    private DBHelper helper;
    DBConstants dbConstants=new DBConstants();
    private final Context context;
    private SQLiteDatabase database;
    ArrayList arrList=new ArrayList();
    ArrayList idList=new ArrayList();
    ArrayList dataList=new ArrayList();
    public TextDatabase(Context context)
    {
        this.context=context;
    }



    public class DBHelper extends SQLiteOpenHelper
    {


        DBHelper(Context context)
        {
            super(context,dbConstants.DATABASE_NAME,null,dbConstants.DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create table textData(id integer autoincrement,Text text);
            String dbcode="CREATE TABLE "+dbConstants.DATABASE_TABLE+"("+dbConstants.KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                                                        +dbConstants.KEY_TITLE+" TEXT ,"
                                                       +dbConstants.KEY_TEXT+" TEXT NOT NULL);";
            db.execSQL(dbcode);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+dbConstants.DATABASE_TABLE);
            onCreate(db);

        }
    }
    public TextDatabase open() throws SQLException
    {
        helper=new DBHelper(context);
        database=helper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        helper.close();
    }

    public long createEntry(String Title,String Text)
    {
        String [] columns=new String [] {dbConstants.KEY_ID,dbConstants.KEY_TITLE,dbConstants.KEY_TEXT};
        //int count
        Cursor c=database.query(dbConstants.DATABASE_TABLE,columns,null,null,null,null,null);
        ContentValues cv=new ContentValues();
       cv.put(dbConstants.KEY_TITLE,Title);
        cv.put(dbConstants.KEY_TEXT,Text);
        return database.insert(dbConstants.DATABASE_TABLE,null,cv);
    }
    public ArrayList getIDList()
    {
        String [] columns=new String [] {dbConstants.KEY_ID,dbConstants.KEY_TEXT};
        Cursor c=database.query(dbConstants.DATABASE_TABLE,columns,null,null,null,null,null);

        String rsult ="";


        idList.clear();
        int id=c.getColumnIndex(dbConstants.KEY_ID);
        //System.out.println("get colum index : "+text);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            rsult=c.getString(id);
            idList.add(rsult);

        }
        c.close();
        return idList;

    }
    public  ArrayList getTexts()
    {
        String [] columns=new String [] {dbConstants.KEY_ID,dbConstants.KEY_TITLE,dbConstants.KEY_TEXT};
        Cursor c=database.query(dbConstants.DATABASE_TABLE,columns,null,null,null,null,null);

        String result ="";


        dataList.clear();
        int text=c.getColumnIndex(dbConstants.KEY_TEXT);
        //System.out.println("get colum index : "+text);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            result=c.getString(text);
            dataList.add(result);

        }
        c.close();
        return dataList;

    }


    public  ArrayList getData()
    {
        String [] columns=new String [] {dbConstants.KEY_ID,dbConstants.KEY_TITLE,dbConstants.KEY_TEXT};
        Cursor c=database.query(dbConstants.DATABASE_TABLE,columns,null,null,null,null,null);

        String result ="";


        arrList.clear();
        int text=c.getColumnIndex(dbConstants.KEY_TITLE);
        //System.out.println("get colum index : "+text);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            result=c.getString(text);
            arrList.add(result);

        }
        c.close();
        return arrList;

    }
    public void deleteEntry(String  id)
    {

         database.delete(dbConstants.DATABASE_TABLE,dbConstants.KEY_ID+"="+id,null);
        String [] columns=new String [] {dbConstants.KEY_ID,dbConstants.KEY_TITLE,dbConstants.KEY_TEXT};
        int did=Integer.parseInt(id)+1;
        int ind=did;
        Cursor c=database.query(dbConstants.DATABASE_TABLE,columns,null,null,null,null,null);

        String updatecode="UPDATE "+dbConstants.DATABASE_TABLE+" SET "+dbConstants.KEY_ID+" = "+ind+" - 1 ;";

        for(c.moveToPosition(did-2);!c.isAfterLast();c.moveToNext())
        {
            database.execSQL(updatecode);
            ind=ind+1;

        }
        c.close();

    }

    public long updateEntry(String id,String title,String text)
    {
        ContentValues cv=new ContentValues();
        cv.put(dbConstants.KEY_TITLE,title);
        cv.put(dbConstants.KEY_TEXT,text);


        return database.update(dbConstants.DATABASE_TABLE,cv,dbConstants.KEY_ID+"=?",new String[]{id});

    }


}
