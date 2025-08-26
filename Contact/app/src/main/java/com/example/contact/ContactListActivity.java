package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    private ListView listViewContacts;
    private ArrayList<String> contactList;
    private ArrayAdapter<String> adapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        listViewContacts = findViewById(R.id.listViewContacts);
        contactList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
        listViewContacts.setAdapter(adapter);

        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
    }

    private void loadContacts() {
        contactList.clear();
        Cursor cursor = dbHelper.getAllContacts();
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                        String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                        contactList.add(name + " - " + phone);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        adapter.notifyDataSetChanged();
    }
}
