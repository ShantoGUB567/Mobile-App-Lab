package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText eTime;
    Button bClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTime = findViewById(R.id.eTime);
        bClick = findViewById(R.id.bClick);

        bClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = eTime.getText().toString().trim();

                if (input.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter time in seconds", Toast.LENGTH_SHORT).show();
                    return;
                }

                int seconds = Integer.parseInt(input);

                Intent intent = new Intent(getApplicationContext(), Alarm.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                if (alarmManager != null) {
                    long triggerAtMillis = System.currentTimeMillis() + (seconds * 1000L);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
                    Toast.makeText(getApplicationContext(), "Alarm set after " + seconds + " seconds.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
