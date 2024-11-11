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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class AddCafeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EditText nameET = findViewById(R.id.editTextName);
        EditText descET = findViewById(R.id.editTextDescription);
        EditText picET = findViewById(R.id.editTextPhotoUrl);
        EditText priceET = findViewById(R.id.editTextPriceListUrl);
        EditText locationET = findViewById(R.id.editTextLocation);
        EditText ratingET = findViewById(R.id.editTextRating);
        Button save_btn = findViewById(R.id.buttonSave);

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

                db.collection("cafes").add(cafe).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddCafeActivity.this, "Cafe Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCafeActivity.this, "There was an error while adding cafe", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
