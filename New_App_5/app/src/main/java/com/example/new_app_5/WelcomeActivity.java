package com.example.new_app_5;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private TextView textViewWelcomeMessage;
    private TextView textViewMultiplyResult;
    private TextView textViewWaysToMultiply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        textViewWelcomeMessage = findViewById(R.id.textViewWelcomeMessage);
        textViewMultiplyResult = findViewById(R.id.textViewMultiplyResult);
        textViewWaysToMultiply = findViewById(R.id.textViewWaysToMultiply);

        // Get data from the Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String studentId = extras.getString("STUDENT_ID");
            int multiplyResult = extras.getInt("MULTIPLY_RESULT");

            textViewWelcomeMessage.setText("Welcome, " + studentId + "!");
            textViewMultiplyResult.setText("Multiply Result: " + multiplyResult);
            displayWaysToMultiply(multiplyResult);
        }
    }

    private void displayWaysToMultiply(int number) {
        StringBuilder ways = new StringBuilder("Ways to get " + number + ":\n");
        boolean foundAtLeastOne = false;

        for (int i = 1; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                int j = number / i;
                if (i <= j) { // Avoid duplicates like 6*2 and 2*6
                    ways.append(i).append(" * ").append(j).append("\n");
                    foundAtLeastOne = true;
                }
            }
        }

        if (!foundAtLeastOne) {
            ways.append("No other integer pairs found (excluding 1 and the number itself).");
        }

        textViewWaysToMultiply.setText(ways.toString());
    }
}