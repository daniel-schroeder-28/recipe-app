package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewUserActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        Button button = findViewById(R.id.buttonCreateAccount);
        button.setOnClickListener(this::createAccount);

        TextView textView = findViewById(R.id.textViewAlreadyHaveAccount);
        textView.setOnClickListener((this::alreadyHaveAccount));
    }

    private void alreadyHaveAccount(View v) {
        Intent intent = new Intent(this, LoginScreenActivity.class);
        startActivity(intent);
    }

    private void createAccount(View v) {
        findViewById(R.id.textViewPasswordsDoNotMatch).setAlpha(0);
        findViewById(R.id.textViewMissingField).setAlpha(0);
        findViewById(R.id.textViewRandomError).setAlpha(0);
        findViewById(R.id.textViewUserAlreadyExists).setAlpha(0);
        String user = ((EditText) findViewById(R.id.editTextUserName)).getText().toString();
        String pass = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
        String reenterPass = ((EditText) findViewById(R.id.editTextReenterPassword)).getText().toString();

        if (user.length() == 0 || pass.length() == 0 || reenterPass.length() == 0) {
            findViewById(R.id.textViewMissingField).setAlpha(1);
            System.exit(0);
        } else if (!pass.equals(reenterPass)) {
            findViewById(R.id.textViewPasswordsDoNotMatch).setAlpha(1);
            System.exit(0);
        } else {
            db.collection("users")
                .document(user)
                .get()
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object doc) {
                        findViewById(R.id.textViewUserAlreadyExists).setAlpha(1);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Map<String, String> passwordMap = new HashMap<>();
                        passwordMap.put("password", pass);
                        db.collection("users")
                            .document(user)
                            .set(passwordMap)
                            .addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object doc) {
                                    Intent intent = new Intent(NewUserActivity.this, WelcomeScreenActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(ex -> findViewById(R.id.textViewRandomError).setAlpha(1));
                    }
                });
        }
    }
}
