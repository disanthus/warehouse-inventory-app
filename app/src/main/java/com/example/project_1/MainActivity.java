package com.example.project_1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    Button signupButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // connect java to XML
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);

        // create database helper
        databaseHelper = new DatabaseHelper(this);


        // login button functions
        loginButton.setOnClickListener(v -> {
            String usernameText = username.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            // check if username or password fields are empty
            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a username and password!", Toast.LENGTH_SHORT).show();
                return;
            }

            // check db and validate login
            boolean validLogin = databaseHelper.checkUser(usernameText, passwordText);
            if (validLogin) {
                Toast.makeText(MainActivity.this, "Login was successful!", Toast.LENGTH_SHORT).show();
                // after successful login, app moves to inventory activity
                Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
                startActivity(intent);
            } else {
                // let user know their login failed
                Toast.makeText(MainActivity.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
            }
        });

        // signup button functions
        signupButton.setOnClickListener(v -> {
            String usernameText = username.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            // check if username or password fields are empty
            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a username and password!", Toast.LENGTH_SHORT).show();
                return;
            }

            // check if username already exists
            if (databaseHelper.usernameExists((usernameText))) {
                Toast.makeText(MainActivity.this, "Username already exists.", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean added = databaseHelper.addUser(usernameText, passwordText);

            if (added) {
                Toast.makeText(MainActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}