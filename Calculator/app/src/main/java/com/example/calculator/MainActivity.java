package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView; // TextView ব্যবহার করা হয়েছে ইতিহাস প্রদর্শনের জন্য
import android.widget.Toast;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, buttonadd, buttonSub, buttonDiv,
            buttonMul, buttonPoint, buttonReset, buttonEqual, buttonRoot, buttonExp, buttonPow;

    EditText eT; // বর্তমান ইনপুট/ফলাফলের জন্য
    TextView tvHistory; // সম্পূর্ণ ক্যালকুলেশন ইতিহাস প্রদর্শনের জন্য

    double ValueOne = Double.NaN; // প্রথম মান, NaN দিয়ে শুরু করা হয়েছে
    String currentOperator = ""; // বর্তমান অপারেটর (+, -, *, /, √, e^x, x^y) সংরক্ষণের জন্য
    boolean operatorPressed = false; // অপারেটর সবেমাত্র চাপানো হয়েছে কিনা তা নির্দেশ করার জন্য ফ্ল্যাগ
    boolean equalPressed = false; // '=' সবেমাত্র চাপানো হয়েছে কিনা তা নির্দেশ করার জন্য ফ্ল্যাগ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI উপাদানগুলোর রেফারেন্সিং
        button0 = findViewById(R.id.btnZero);
        button1 = findViewById(R.id.btnOne);
        button2 = findViewById(R.id.btnTwo);
        button3 = findViewById(R.id.btnThree);
        button4 = findViewById(R.id.btnFour);
        button5 = findViewById(R.id.btnFive);
        button6 = findViewById(R.id.btnSix);
        button7 = findViewById(R.id.btnSeven);
        button8 = findViewById(R.id.btnEight);
        button9 = findViewById(R.id.btnNine);
        buttonPoint = findViewById(R.id.btnPoint);
        buttonadd = findViewById(R.id.btnadd);
        buttonSub = findViewById(R.id.btnSub);
        buttonMul = findViewById(R.id.btnMul);
        buttonDiv = findViewById(R.id.btnDiv);
        buttonReset = findViewById(R.id.btnReset);
        buttonEqual = findViewById(R.id.btnEqual);
        buttonRoot = findViewById(R.id.btnRoot);
        buttonExp = findViewById(R.id.btnExp);
        buttonPow = findViewById(R.id.btnPow);
        eT = findViewById(R.id.eT);
        tvHistory = findViewById(R.id.tv_history);

        // সংখ্যা বাটনগুলোর জন্য সাধারণ অনক্লিক লিসেনার
        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (operatorPressed || equalPressed) {
                    eT.setText(""); // অপারেটর বা ইকুয়ালের পরে ডিসপ্লে পরিষ্কার করুন
                    operatorPressed = false;
                    equalPressed = false;
                }
                if (eT.getText().toString().equals("0") && !b.getText().toString().equals(".")) {
                    eT.setText(b.getText().toString()); // যদি শুধুমাত্র 0 থাকে তবে তা প্রতিস্থাপন করুন
                } else {
                    eT.append(b.getText().toString()); // সংখ্যা যোগ করুন
                }
            }
        };

        button0.setOnClickListener(numberClickListener);
        button1.setOnClickListener(numberClickListener);
        button2.setOnClickListener(numberClickListener);
        button3.setOnClickListener(numberClickListener);
        button4.setOnClickListener(numberClickListener);
        button5.setOnClickListener(numberClickListener);
        button6.setOnClickListener(numberClickListener);
        button7.setOnClickListener(numberClickListener);
        button8.setOnClickListener(numberClickListener);
        button9.setOnClickListener(numberClickListener);

        buttonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operatorPressed || equalPressed) {
                    eT.setText("0"); // অপারেটর বা ইকুয়ালের পরে দশমিক চাপলে 0. দিয়ে শুরু করুন
                    operatorPressed = false;
                    equalPressed = false;
                }
                // একাধিক দশমিক বিন্দু প্রতিরোধ করুন
                if (!eT.getText().toString().contains(".")) {
                    eT.append(".");
                }
            }
        });

        // রিসেট বাটনের জন্য ইভেন্ট লিসেনার
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eT.setText("");
                tvHistory.setText("");
                ValueOne = Double.NaN;
                currentOperator = "";
                operatorPressed = false;
                equalPressed = false;
            }
        });

        // অপারেটর বাটনগুলোর জন্য সাধারণ অনক্লিক লিসেনার (যোগ, বিয়োগ, গুণ, ভাগ, পাওয়ার)
        View.OnClickListener binaryOperatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String newOperator = b.getText().toString();

                if (eT.getText().length() == 0 && Double.isNaN(ValueOne)) {
                    Toast.makeText(MainActivity.this, "প্রথমে একটি সংখ্যা লিখুন", Toast.LENGTH_SHORT).show();
                    return;
                }

                // যদি '=' চাপার পরে অপারেটর চাপানো হয়, তবে ফলাফলের সাথে চালিয়ে যান
                if (equalPressed) {
                    equalPressed = false;
                }

                // যদি ValueOne সেট করা থাকে এবং নতুন অপারেটর চাপানো হয়, তাহলে নতুন অপারেটর ব্যবহার করুন (পূর্ববর্তী অপারেশন বাদ যাবে)
                if (!Double.isNaN(ValueOne) && operatorPressed) {
                    currentOperator = newOperator;
                    tvHistory.setText(formatNumber(ValueOne) + " " + currentOperator);
                } else {
                    // নতুন অপারেশন শুরু করুন
                    try {
                        ValueOne = Double.parseDouble(eT.getText().toString());
                        currentOperator = newOperator;
                        operatorPressed = true;
                        tvHistory.setText(formatNumber(ValueOne) + " " + currentOperator);
                        eT.setText(""); // নতুন ইনপুটের জন্য ডিসপ্লে পরিষ্কার করুন
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "বৈধ সংখ্যা নয়", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        buttonadd.setOnClickListener(binaryOperatorClickListener);
        buttonSub.setOnClickListener(binaryOperatorClickListener);
        buttonMul.setOnClickListener(binaryOperatorClickListener);
        buttonDiv.setOnClickListener(binaryOperatorClickListener);
        buttonPow.setOnClickListener(binaryOperatorClickListener);

        // রুট বাটনের জন্য ইভেন্ট লিসেনার (একক অপারেটর)
        buttonRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eT.getText().length() == 0) {
                    Toast.makeText(MainActivity.this, "প্রথমে একটি সংখ্যা লিখুন", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    double num = Double.parseDouble(eT.getText().toString());
                    if (num < 0) {
                        eT.setText("Error");
                        tvHistory.setText("√(" + formatNumber(num) + ") = Error");
                        Toast.makeText(MainActivity.this, "ঋণাত্মক সংখ্যার বর্গমূল গণনা করা যাবে না", Toast.LENGTH_LONG).show();
                        ValueOne = Double.NaN; // রিসেট
                    } else {
                        double result = Math.sqrt(num);
                        tvHistory.setText("√(" + formatNumber(num) + ")");
                        eT.setText(formatNumber(result));
                        ValueOne = result; // ফলাফলের সাথে চেইন করার জন্য ValueOne সেট করুন
                    }
                    currentOperator = ""; // একক অপারেশনের পরে অপারেটর পরিষ্কার করুন
                    operatorPressed = false;
                    equalPressed = false;
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "বৈধ সংখ্যা নয়", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // এক্সপোনেনশিয়াল বাটনের জন্য ইভেন্ট লিসেনার (একক অপারেটর)
        buttonExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eT.getText().length() == 0) {
                    Toast.makeText(MainActivity.this, "প্রথমে একটি সংখ্যা লিখুন", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    double num = Double.parseDouble(eT.getText().toString());
                    double result = Math.exp(num);
                    tvHistory.setText("e^(" + formatNumber(num) + ")");
                    eT.setText(formatNumber(result));
                    ValueOne = result; // ফলাফলের সাথে চেইন করার জন্য ValueOne সেট করুন
                    currentOperator = ""; // একক অপারেশনের পরে অপারেটর পরিষ্কার করুন
                    operatorPressed = false;
                    equalPressed = false;
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "বৈধ সংখ্যা নয়", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ইকুয়াল বাটনের জন্য ইভেন্ট লিসেনার
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eT.getText().length() == 0 || Double.isNaN(ValueOne) || currentOperator.isEmpty()) {
                    // যদি দ্বিতীয় সংখ্যা না থাকে বা প্রথম সংখ্যা না থাকে বা অপারেটর না থাকে, কিছু করবেন না
                    return;
                }

                try {
                    double ValueTwo = Double.parseDouble(eT.getText().toString());
                    String fullExpression = formatNumber(ValueOne) + " " + currentOperator + " " + formatNumber(ValueTwo);
                    double result = 0;

                    switch (currentOperator) {
                        case "+":
                            result = ValueOne + ValueTwo;
                            break;
                        case "-":
                            result = ValueOne - ValueTwo;
                            break;
                        case "*":
                            result = ValueOne * ValueTwo;
                            break;
                        case "/":
                            if (ValueTwo == 0) {
                                if (ValueOne == 0) {
                                    eT.setText("Undefined");
                                    tvHistory.setText(fullExpression + " = Undefined");
                                    Toast.makeText(MainActivity.this, "ফলাফল অনির্দিষ্ট (0/0)", Toast.LENGTH_LONG).show();
                                } else {
                                    eT.setText("Infinity");
                                    tvHistory.setText(fullExpression + " = Infinity");
                                    Toast.makeText(MainActivity.this, "ফলাফল অসীম", Toast.LENGTH_LONG).show();
                                }
                                ValueOne = Double.NaN; // রিসেট করুন
                                currentOperator = "";
                                equalPressed = true;
                                return; // ফলাফল সেট না করে পদ্ধতি থেকে বেরিয়ে যান
                            }
                            result = ValueOne / ValueTwo;
                            break;
                        case "x^y":
                            result = Math.pow(ValueOne, ValueTwo);
                            break;
                    }

                    tvHistory.setText(fullExpression + " ="); // সম্পূর্ণ এক্সপ্রেশন দেখান
                    eT.setText(formatNumber(result)); // ফলাফল দেখান
                    ValueOne = result; // চেইন অপারেশনের জন্য ValueOne কে ফলাফলে সেট করুন
                    currentOperator = ""; // গণনার পরে অপারেটর পরিষ্কার করুন
                    operatorPressed = false;
                    equalPressed = true; // ফ্ল্যাগ সেট করুন যে '=' চাপানো হয়েছে

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "বৈধ সংখ্যা নয়", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // ডবল থেকে স্ট্রিং ফর্ম্যাট করার জন্য সহায়ক পদ্ধতি (যদি পূর্ণসংখ্যা হয় তবে .0 বাদ দিন)
    private String formatNumber(double number) {
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.valueOf(number);
        }
    }
}