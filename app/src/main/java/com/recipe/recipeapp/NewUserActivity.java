package com.recipe.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewUserActivity extends AppCompatActivity {

    public static String USERNAME = "";
    public static ArrayList<String> FAVORITE_RECIPES = new ArrayList<>();
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

    /**
     * function to go back to login page
     * @param v - View
     */
    public void alreadyHaveAccount(View v) {
        Intent intent = new Intent(this, LoginScreenActivity.class);
        startActivity(intent);
    }

    /**
     * function to create account
     * @param v - View
     */
    public void createAccount(View v) {
        findViewById(R.id.textViewPasswordsDoNotMatch).setAlpha(0);
        findViewById(R.id.textViewMissingField).setAlpha(0);
        findViewById(R.id.textViewRandomError).setAlpha(0);
        findViewById(R.id.textViewUserAlreadyExists).setAlpha(0);
        String user = ((EditText) findViewById(R.id.editTextUserName)).getText().toString();
        String pass = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
        String reenterPass = ((EditText) findViewById(R.id.editTextReenterPassword)).getText().toString();

        if (user.length() == 0 || pass.length() == 0 || reenterPass.length() == 0) {
            findViewById(R.id.textViewMissingField).setAlpha(1);
            return;
        } else if (!pass.equals(reenterPass)) {
            findViewById(R.id.textViewPasswordsDoNotMatch).setAlpha(1);
            return;
        } else {
            DocumentReference docRef = db.collection("users").document(user);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        findViewById(R.id.textViewUserAlreadyExists).setAlpha(1);
                    } else {
                        Map<String, String> fieldsMap = new HashMap<>();
                        fieldsMap.put("password", pass);
                        fieldsMap.put("favorite_recipes", "");
                        docRef.set(fieldsMap).addOnSuccessListener(documentReference -> {
                            Intent intent = new Intent(NewUserActivity.this, WelcomeScreenActivity.class);
                            intent.putExtra("username", user);
                            intent.putExtra("favorite_recipes", FAVORITE_RECIPES);
                            startActivity(intent);
                        })
                        .addOnFailureListener(ex -> findViewById(R.id.textViewRandomError).setAlpha(1));
                    }
                } else {
                    findViewById(R.id.textViewRandomError).setAlpha(1);
                }
            });
        }
    }
}
