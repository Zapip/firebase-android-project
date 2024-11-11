package com.example.firebaseandroidproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        TextView textViewUserEmail = findViewById(R.id.textViewUserEmail);
        Button buttonLogout = findViewById(R.id.buttonLogout);

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddCafeActivity.class)));
        db.collection("cafes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Cafe> arrayList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Cafe cafe = document.toObject(Cafe.class);
                        cafe.setId(document.getId());
                        arrayList.add(cafe);
                    }
                    CafeAdapter adapter = new CafeAdapter(MainActivity.this, arrayList);
                    recyclerView.setAdapter(adapter);

                    adapter.setOnItemClickListener(new CafeAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(Cafe cafe) {
                            App.cafe = cafe;
                            startActivity(new Intent(MainActivity.this, EditCafeActivity.class));
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

//        FloatingActionButton refresh = findViewById(R.id.refresh);
//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                db.collection("cafes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            ArrayList<Cafe> arrayList = new ArrayList<>();
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Cafe cafe = document.toObject(Cafe.class);
//                                cafe.setId(document.getId());
//                                arrayList.add(cafe);
//                            }
//                            CafeAdapter adapter = new CafeAdapter(MainActivity.this, arrayList);
//                            recyclerView.setAdapter(adapter);
//
//                            adapter.setOnItemClickListener(new CafeAdapter.OnItemClickListener() {
//                                @Override
//                                public void onClick(Cafe cafe) {
//                                    App.cafe = cafe;
//                                    startActivity(new Intent(MainActivity.this, EditCafeActivity.class));
//                                }
//                            });
//                        } else {
//                            Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            String userEmail = currentUser.getEmail();
            textViewUserEmail.setText("Hi, " + userEmail);
        }
        buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCafeActivity.class);
            startActivity(intent);
        });


        buttonLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(MainActivity.this, "Anda berhasil logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}