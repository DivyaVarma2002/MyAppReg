package com.reg.myappreg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.reg.myappreg.model.User;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views and database helper
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);


        databaseHelper = new DatabaseHelper(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate and add to database
                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    User user = new User(username, email, password);
                    boolean success = databaseHelper.addUser(user);
                    if (success) {
                        Toast.makeText(MainActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                        // Clear input fields after successful registration
                        usernameEditText.getText().clear();
                        emailEditText.getText().clear();
                        passwordEditText.getText().clear();


                        Log.d("MainActivity", "Navigating to UserListActivity");

                        // Navigate to UserListActivity
                        Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to add user", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
