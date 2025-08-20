package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Declare UI elements and database objects
    EditText ename, ecollege;
    Button binsert, bexit, bdisplay;
    String nam, coll;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI elements to their IDs
        ename = findViewById(R.id.ename);
        ecollege = findViewById(R.id.ecollege);
        binsert = findViewById(R.id.binsert);
        bdisplay = findViewById(R.id.bdisplay);
        bexit = findViewById(R.id.bexit);

        // Create or open the database and create the table
        db = openOrCreateDatabase("Mydb", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student (name VARCHAR, college VARCHAR);");

        // Set click listener for the Insert button
        binsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nam = ename.getText().toString();
                coll = ecollege.getText().toString();

                // Insert data into the student table
                db.execSQL("INSERT INTO student VALUES('" + nam + "', '" + coll + "');");

                // Show a toast message to confirm the insertion
                Toast.makeText(getApplicationContext(), "Row Inserted", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for the Display button
        bdisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start PreviewActivity to display the data
                Intent intent = new Intent(getApplicationContext(), PreviewActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set click listener for the Exit button
        bexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exit the application
                System.exit(0);
            }
        });
    }
}
