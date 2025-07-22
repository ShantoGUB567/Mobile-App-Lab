package com.example.new_app_5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editTextStudentId;
    private EditText editTextPassword;
    private TextView textViewCaptcha;
    private EditText editTextCaptchaResult;
    private Button buttonLogin;

    private int captchaNum1;
    private int captchaNum2;
    private int captchaCorrectResult;

    private static final String DEFAULT_STUDENT_ID = "221002567";
    private static final String DEFAULT_PASSWORD = "shanto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextStudentId = findViewById(R.id.editTextStudentId);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewCaptcha = findViewById(R.id.textViewCaptcha);
        editTextCaptchaResult = findViewById(R.id.editTextCaptchaResult);
        buttonLogin = findViewById(R.id.buttonLogin);

        generateCaptcha();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    private void generateCaptcha() {
        Random random = new Random();
        captchaNum1 = random.nextInt(9) + 1; // Range 1-9
        captchaNum2 = random.nextInt(9) + 1; // Range 1-9
        captchaCorrectResult = captchaNum1 * captchaNum2;
        textViewCaptcha.setText("Captcha: " + captchaNum1 + " * " + captchaNum2 + " = ?");
    }

    private void checkLogin() {
        String studentId = editTextStudentId.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String captchaInput = editTextCaptchaResult.getText().toString().trim();

        if (studentId.isEmpty() || password.isEmpty() || captchaInput.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate Student ID and Password
        boolean idValid = studentId.equals(DEFAULT_STUDENT_ID);
        boolean passValid = password.equals(DEFAULT_PASSWORD);

        // Validate Captcha
        int userCaptchaResult;
        try {
            userCaptchaResult = Integer.parseInt(captchaInput);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid captcha input", Toast.LENGTH_SHORT).show();
            generateCaptcha(); // Regenerate captcha on invalid input
            return;
        }

        boolean captchaValid = (userCaptchaResult == captchaCorrectResult);

        if (idValid && passValid && captchaValid) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            intent.putExtra("STUDENT_ID", studentId);
            intent.putExtra("MULTIPLY_RESULT", captchaCorrectResult);
            startActivity(intent);
            finish(); // Close MainActivity so user can't go back with back button
        } else {
            String errorMessage = "";
            if (!idValid) {
                errorMessage += "Invalid Student ID. ";
            }
            if (!passValid) {
                errorMessage += "Invalid Password. ";
            }
            if (!captchaValid) {
                errorMessage += "Invalid Captcha. ";
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            generateCaptcha(); // Regenerate captcha on failed login
            editTextCaptchaResult.setText(""); // Clear captcha input
        }
    }
}