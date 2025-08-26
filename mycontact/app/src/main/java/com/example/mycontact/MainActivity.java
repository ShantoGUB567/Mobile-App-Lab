package com.example.mycontact;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etPhone;
    private DBHelper dbHelper; // DB helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnView = findViewById(R.id.btnView);

        dbHelper = new DBHelper(this); // (i) open/create ContactDB (auto)

        btnSave.setOnClickListener(v -> saveContactToDb());

        btnView.setOnClickListener(v -> {
            // (i) Navigate to ContactListActivity (no extras needed now)
            Intent intent = new Intent(MainActivity.this, ContactlistActivity.class);
            startActivity(intent);
        });
    }

    private void saveContactToDb() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Name required");
            etName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Phone required");
            etPhone.requestFocus();
            return;
        }

        long rowId = dbHelper.insertContact(name, phone);
        if (rowId == -1) {
            Toast.makeText(this, "Save failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Saved to database", Toast.LENGTH_SHORT).show();
            etName.setText("");
            etPhone.setText("");
            etName.requestFocus();
        }
    }
}