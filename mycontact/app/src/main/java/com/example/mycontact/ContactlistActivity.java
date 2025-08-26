package com.example.mycontact;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContactlistActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ArrayList<String> contacts;
    private ArrayAdapter<String> adapter;

    private ListView listView;
    private EditText etNewName, etNewPhone;
    private Button btnAddToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);

        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.listContacts);
        etNewName = findViewById(R.id.etNewName);
        etNewPhone = findViewById(R.id.etNewPhone);
        btnAddToList = findViewById(R.id.btnAddToList);

        // প্রথমবার লিস্ট লোড
        refreshListFromDb();

        btnAddToList.setOnClickListener(v -> {
            String name = etNewName.getText().toString().trim();
            String phone = etNewPhone.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                etNewName.setError("Name required");
                etNewName.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                etNewPhone.setError("Phone required");
                etNewPhone.requestFocus();
                return;
            }

            long rowId = dbHelper.insertContact(name, phone);
            if (rowId == -1) {
                Toast.makeText(this, "Insert failed!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Added to database", Toast.LENGTH_SHORT).show();
                etNewName.setText("");
                etNewPhone.setText("");
                refreshListFromDb(); // নতুন ডেটা সহ লিস্ট রিফ্রেশ
            }
        });
    }

    private void refreshListFromDb() {
        contacts = dbHelper.getAllContacts();
        if (adapter == null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
            listView.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(contacts);
            adapter.notifyDataSetChanged();
        }
    }
}