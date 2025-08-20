package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewActivity extends AppCompatActivity {

    // Declare UI elements and database objects
    TextView tname, tcollege;
    Button bprev, bnext, bback;
    SQLiteDatabase db;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        // Link UI elements to their IDs
        tname = findViewById(R.id.tname);
        tcollege = findViewById(R.id.tcollege);
        bprev = findViewById(R.id.bprev);
        bnext = findViewById(R.id.bnext);
        bback = findViewById(R.id.bback);

        // Open the database and retrieve all records
        db = openOrCreateDatabase("Mydb", MODE_PRIVATE, null);
        c = db.rawQuery("select * from student", null);

        // Move to the first record and display the data
        c.moveToFirst();
        tname.setText(c.getString(c.getColumnIndex("name")));
        tcollege.setText(c.getString(c.getColumnIndex("college")));

        // Set click listener for the Home button
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to MainActivity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set click listener for the Next button
        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Move to the next record
                    c.moveToNext();
                    tname.setText(c.getString(c.getColumnIndex("name")));
                    tcollege.setText(c.getString(c.getColumnIndex("college")));
                } catch (Exception e) {
                    // Handle case where there are no more records
                    Toast.makeText(getApplicationContext(), "Last Record", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        // Set click listener for the Prev button
        bprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Move to the previous record
                    c.moveToPrevious();
                    tname.setText(c.getString(c.getColumnIndex("name")));
                    tcollege.setText(c.getString(c.getColumnIndex("college")));
                } catch (Exception e) {
                    // Handle case where there are no previous records
                    Toast.makeText(getApplicationContext(), "First Record", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
