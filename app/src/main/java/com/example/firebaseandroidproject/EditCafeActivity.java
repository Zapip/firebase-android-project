package com.example.firebaseandroidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class EditCafeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EditText nameET = findViewById(R.id.editTextName);
        EditText descET = findViewById(R.id.editTextDescription);
        EditText picET = findViewById(R.id.editTextPhotoUrl);
        EditText priceET = findViewById(R.id.editTextPriceListUrl);
        EditText locationET = findViewById(R.id.editTextLocation);
        EditText ratingET = findViewById(R.id.editTextRating);
        Button save_btn = findViewById(R.id.buttonSave);

        nameET.setText(App.cafe.getName());
        descET.setText(App.cafe.getDesc());
        picET.setText(App.cafe.getPic());
        priceET.setText(App.cafe.getPrice());
        locationET.setText(App.cafe.getLocation());
        ratingET.setText(App.cafe.getRating());

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> cafe = new HashMap<>();
                cafe.put("nameET", Objects.requireNonNull(nameET.getText()).toString());
                cafe.put("descET", Objects.requireNonNull(descET.getText()).toString());
                cafe.put("picET", Objects.requireNonNull(picET.getText()).toString());
                cafe.put("priceET", Objects.requireNonNull(priceET.getText()).toString());
                cafe.put("locationET", Objects.requireNonNull(locationET.getText()).toString());
                cafe.put("ratingET", Objects.requireNonNull(ratingET.getText()).toString());

                db.collection("users").document(App.cafe.getId()).set(cafe).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditCafeActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditCafeActivity.this, "Error while saving users", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
