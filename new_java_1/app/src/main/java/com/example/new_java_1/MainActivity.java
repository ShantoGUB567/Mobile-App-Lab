package com.example.new_java_1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;;


public class MainActivity extends AppCompatActivity {
    EditText editName1; //unsername
    EditText editName2; //password
    Button btnOk;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editName1 = findViewById(R.id.edit_name1);
        editName2 = findViewById(R.id.edit_name2);
        btnOk = findViewById(R.id.btn_ok);
        btnReset = findViewById(R.id.btn_reset);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editName1.getText().toString();
                String password = editName2.getText().toString();
                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter both username and password.", Toast.LENGTH_SHORT).show();
                }else{
                    if (username.equals("user") && password.equals("pass")){
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Input",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName1.setText("");
                editName2.setText("");
                Toast.makeText(getApplicationContext(), "Reset Clicked", Toast.LENGTH_LONG).show();
            }
        });
    }
}