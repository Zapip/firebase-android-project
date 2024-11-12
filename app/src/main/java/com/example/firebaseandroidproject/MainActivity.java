package com.example.firebaseandroidproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CafeAdapter adapter;
    private ArrayList<Cafe> arrayList = new ArrayList<>(); // ArrayList untuk adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        TextView textViewUserEmail = findViewById(R.id.textViewUserEmail);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set layout manager
        adapter = new CafeAdapter(MainActivity.this, arrayList); // Inisialisasi adapter
        recyclerView.setAdapter(adapter);

        Button buttonLogout = findViewById(R.id.buttonLogout);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonRefresh = findViewById(R.id.buttonRefresh);

        // Listener untuk item klik
        adapter.setOnItemClickListener(cafe -> {
            App.cafe = cafe;
            startActivity(new Intent(MainActivity.this, EditCafeActivity.class));
        });

        // Set email user jika login berhasil
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            String userEmail = currentUser.getEmail();
            textViewUserEmail.setText("Hi, " + userEmail);
        }

        // Fungsi Logout
        buttonLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(MainActivity.this, "Anda berhasil logout", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        // Fungsi untuk refresh data cafe dari Firestore
        buttonRefresh.setOnClickListener(view -> refreshData());

        // Menambahkan data cafe baru
        buttonAdd.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddCafeActivity.class)));

        // Muat data awal
        refreshData();
    }

    private void refreshData() {
        db.collection("cafes").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                arrayList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Cafe cafe = document.toObject(Cafe.class);
                    cafe.setId(document.getId());
                    arrayList.add(cafe);
                }
                adapter.notifyDataSetChanged(); // Hanya update data tanpa membuat instance baru
            } else {
                Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}