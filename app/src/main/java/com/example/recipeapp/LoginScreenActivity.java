package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginScreenActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<String> FAVORITE_RECIPES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Button button = findViewById(R.id.buttonSignIn);
        button.setOnClickListener(this::login);

        TextView textView = findViewById(R.id.newUserTextView);
        textView.setOnClickListener((this::newUser));
    }

    public void login(View v) {
        findViewById(R.id.blankUserOrPassTextView).setAlpha(0);
        findViewById(R.id.incorrectUserOrPassTextView).setAlpha(0);
        String user = ((EditText) findViewById(R.id.editTextTextPersonName)).getText().toString();
        String pass = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();

       if (user.length() > 0 && pass.length() > 0) {
           DocumentReference docRef = db.collection("users").document(user);
           docRef.get().addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   if (document.exists() && pass.equals(document.getData().get("password"))) {
                       FAVORITE_RECIPES = new ArrayList<>(Arrays.asList(document.getData().get("favorite_recipes").toString().split(",")));
                       Intent intent = new Intent(LoginScreenActivity.this, WelcomeScreenActivity.class);
                       intent.putExtra("username", user);
                       intent.putExtra("favorite_recipes", FAVORITE_RECIPES);
                       startActivity(intent);
                   } else {
                       findViewById(R.id.incorrectUserOrPassTextView).setAlpha(1);
                   }
               } else {
                   findViewById(R.id.incorrectUserOrPassTextView).setAlpha(1);
               }
           });
       } else {
           findViewById(R.id.blankUserOrPassTextView).setAlpha(1);
       }
    }

    public void newUser(View v) {
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
    }
}