package com.example.project_1;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SmsNotificationActivity extends AppCompatActivity {
    private static final int SMS_PERMISSION_CODE = 100;
    EditText phoneNumberInput;
    Switch smsSwitch;
    Button enableSmsButton;
    Button backButton;
    TextView smsStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sms_notif);

        // connect to xml
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        smsSwitch = findViewById(R.id.smsSwitch);
        enableSmsButton = findViewById(R.id.enableSmsButton);
        backButton = findViewById(R.id.backButton);
        smsStatus = findViewById(R.id.smsStatus);

        // enable sms button
        enableSmsButton.setOnClickListener(v -> {
            String phoneNumber = phoneNumberInput.getText().toString().trim();
            if (phoneNumber.isEmpty()) {
                Toast.makeText(SmsNotificationActivity.this, "Please enter a phone number.", Toast.LENGTH_SHORT).show();
                return;
            }

            // turn switch on
            smsSwitch.setChecked(true);

            // check perms
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                // permission not granted
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
            } else {
                // permission granted
                smsStatus.setText("SMS notifcations are enabled.");
                Toast.makeText(this, "SMS notifications enabled!", Toast.LENGTH_SHORT).show();
            }
        });

        // switch toggle
        smsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                smsStatus.setText("SMS notifications are disabled.");
            }
        });

        // back button
        backButton.setOnClickListener(v -> finish());
    }

    // permission response handler
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions,grantResults);
            if (requestCode == SMS_PERMISSION_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted
                    smsStatus.setText("SMS notifications are enabled.");
                    Toast.makeText(this, "SMS notifications enabled!", Toast.LENGTH_SHORT).show();
                } else {
                    // permission is denied
                    smsStatus.setText("SMS permission denied." + "The rest of the application will continue to work.");
                    Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void sendSMSAlert (String phoneNumber, String message) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "SMS permission has not been granted", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS alert sent!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Unable to send SMS alert.", Toast.LENGTH_SHORT).show();
        }
    }
}
