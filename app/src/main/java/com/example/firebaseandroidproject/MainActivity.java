package com.example.firebaseandroidproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView textViewUserEmail;
    private Button buttonLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        buttonLogout = findViewById(R.id.buttonLogout);  // Pastikan baris ini ada

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            String userEmail = currentUser.getEmail();
            textViewUserEmail.setText("Logged in as: " + userEmail);
        }

        buttonLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(MainActivity.this, "Anda berhasil logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}