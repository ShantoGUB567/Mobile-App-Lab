package com.example.new_java_1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    private EditText etTemperature;
    private RadioGroup radioGroupInputUnit;
    private RadioGroup radioGroupOutputUnit;
    private Button btnConvert;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        etTemperature = findViewById(R.id.etTemperature);
        radioGroupInputUnit = findViewById(R.id.radioGroupInputUnit);
        radioGroupOutputUnit = findViewById(R.id.radioGroupOutputUnit);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.tvResult);

        // Convert বাটনে ক্লিক লিসেনার সেট করা হচ্ছে
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTemperature();
            }
        });
    }

    private void convertTemperature() {
        String tempStr = etTemperature.getText().toString();
        if (tempStr.isEmpty()) {
            Toast.makeText(this, "Please enter a temperature.", Toast.LENGTH_SHORT).show();
            return;
        }

        double inputTemp;
        try {
            inputTemp = Double.parseDouble(tempStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid temperature value.", Toast.LENGTH_SHORT).show();
            return;
        }

        // ইনপুট ইউনিট নির্ধারণ করা হচ্ছে
        int selectedInputId = radioGroupInputUnit.getCheckedRadioButtonId();
        String inputUnit;
        if (selectedInputId == R.id.radioCelsiusInput) {
            inputUnit = "C";
        } else if (selectedInputId == R.id.radioFahrenheitInput) {
            inputUnit = "F";
        } else if (selectedInputId == R.id.radioKelvinInput) {
            inputUnit = "K";
        } else {
            Toast.makeText(this, "Please select an input unit.", Toast.LENGTH_SHORT).show();
            return;
        }

        // আউটপুট ইউনিট নির্ধারণ করা হচ্ছে
        int selectedOutputId = radioGroupOutputUnit.getCheckedRadioButtonId();
        String outputUnit;
        if (selectedOutputId == R.id.radioCelsiusOutput) {
            outputUnit = "C";
        } else if (selectedOutputId == R.id.radioFahrenheitOutput) {
            outputUnit = "F";
        } else if (selectedOutputId == R.id.radioKelvinOutput) {
            outputUnit = "K";
        } else {
            Toast.makeText(this, "Please select an output unit.", Toast.LENGTH_SHORT).show();
            return;
        }

        // রূপান্তর লজিক
        double convertedTemp = 0;

        // প্রথমে সব ইনপুটকে সেলসিয়াসে রূপান্তর করুন
        double tempInCelsius;
        switch (inputUnit) {
            case "C":
                tempInCelsius = inputTemp;
                break;
            case "F":
                tempInCelsius = (inputTemp - 32) * 5 / 9;
                break;
            case "K":
                tempInCelsius = inputTemp - 273.15;
                break;
            default:
                tempInCelsius = 0; // Should not happen
        }

        // তারপর সেলসিয়াস থেকে কাঙ্ক্ষিত আউটপুট ইউনিটে রূপান্তর করুন
        switch (outputUnit) {
            case "C":
                convertedTemp = tempInCelsius;
                break;
            case "F":
                convertedTemp = (tempInCelsius * 9 / 5) + 32;
                break;
            case "K":
                convertedTemp = tempInCelsius + 273.15;
                break;
        }

        // ফলাফল প্রদর্শন করা হচ্ছে
        String resultText = String.format("Result: %.2f %s", convertedTemp, getUnitSymbol(outputUnit));
        tvResult.setText(resultText);
    }

    // ইউনিটের প্রতীক পাওয়ার জন্য একটি সহায়ক মেথড
    private String getUnitSymbol(String unit) {
        switch (unit) {
            case "C":
                return "°C";
            case "F":
                return "°F";
            case "K":
                return "K";
            default:
                return "";
        }
    }
}