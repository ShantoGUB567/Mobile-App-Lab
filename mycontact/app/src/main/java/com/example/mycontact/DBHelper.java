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

    // Table & columns
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public long insertContact(String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_PHONE, phone);
        return db.insert(TABLE_NAME, null, cv);
    }


    public ArrayList<String> getAllContacts() {
        ArrayList<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT " + COL_NAME + ", " + COL_PHONE + " FROM " + TABLE_NAME,
                null
        );

        if (c != null) {
            while (c.moveToNext()) {
                String name = c.getString(0);
                String phone = c.getString(1);
                data.add(name + " - " + phone);
            }
            c.close();
        }
        return data;
    }
}