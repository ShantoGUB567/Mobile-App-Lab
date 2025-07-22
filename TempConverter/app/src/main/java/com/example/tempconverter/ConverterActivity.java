package com.example.tempconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConverterActivity extends AppCompatActivity {

    private Button cToF,fToC;
    private TextView result;
    private EditText enterTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);


        cToF = findViewById(R.id.cToF);
        fToC = findViewById(R.id.fToC);
        result = findViewById(R.id.result);
        enterTemp = findViewById(R.id.enterTemp);


        cToF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = enterTemp.getText().toString().trim();

                if (input.isEmpty()) {
                    Toast.makeText(ConverterActivity.this, "Please enter a temperature", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double celsius = Double.parseDouble(input);
                    double fahrenheit = (celsius * 1.8) + 32;
                    result.setText(String.format("%.2f°C = %.2f°F", celsius, fahrenheit));
                } catch (NumberFormatException e) {
                    Toast.makeText(ConverterActivity.this, "Invalid input! Please enter a number.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fToC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = enterTemp.getText().toString().trim();

                if (input.isEmpty()) {
                    Toast.makeText(ConverterActivity.this, "Please enter a temperature", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double fahrenheit = Double.parseDouble(input);
                    double celsius = (fahrenheit - 32) * 5 / 9;
                    result.setText(String.format("%.2f°F = %.2f°C", fahrenheit, celsius));
                } catch (NumberFormatException e) {
                    Toast.makeText(ConverterActivity.this, "Invalid input! Please enter a number.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
