package com.ramavathubalaji.noteit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RAMAVATHU BALAJI on 11-03-2018.
 */

public class DBHelper extends SQLiteOpenHelper
{
    DBConstants dbConstants;



    DBHelper(Context context)
    {
        super(context,"TextDB",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table textData(id integer autoincrement,Text text);
        String dbcode="CREATE TABLE "+dbConstants.DATABASE_TABLE+"("+dbConstants.KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +dbConstants.KEY_TEXT+" TEXT NOT NULL);";
        db.execSQL(dbcode);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+dbConstants.DATABASE_TABLE);
        onCreate(db);

    }
}