package com.example.mycontact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "ContactDB";
    private static final int DB_VERSION = 1;


    public static final String TABLE_NAME = "contacts";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE = "phone";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // (ii) Create table contacts(name VARCHAR, phone VARCHAR)
        String createSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + COL_NAME + " VARCHAR, " + COL_PHONE + " VARCHAR)";
        db.execSQL(createSql);
    }


