package com.example.recipeapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String USERNAME = "";
    public static ArrayList<String> FAVORITE_RECIPES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        USERNAME = intent.getStringExtra("username");
        FAVORITE_RECIPES = intent.getStringArrayListExtra("favorite_recipes");

        NavigationBarView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.search);
        navBar.setOnItemSelectedListener(this::navigate);

        Button buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this::recipeSearch);

        Button buttonNewSearch = findViewById(R.id.buttonNewRecipeSearch);
        buttonNewSearch.setOnClickListener(this::newRecipeSearch);

        Button buttonFavorite = findViewById(R.id.buttonAddToFavorites);
        buttonFavorite.setOnClickListener(this::addFavorite);

        Button buttonUnfavorite = findViewById(R.id.buttonRemoveFromFavorites);
        buttonUnfavorite.setOnClickListener(this::removeFavorite);

        if (intent.getStringExtra("immediate_search") != null) {
            immediateSearch(intent.getStringExtra("immediate_search"));
        }
    }

    private boolean navigate(MenuItem menuItem) {
        TextView title = findViewById(R.id.textViewRecipeTitle);
        switch (menuItem.getItemId()) {
            case (R.id.welcome):
                Intent intentWelcome = new Intent(this, WelcomeScreenActivity.class);
                intentWelcome.putExtra("username", USERNAME);
                intentWelcome.putExtra("favorite_recipes", FAVORITE_RECIPES);
                intentWelcome.putExtra("last_search", title.getText());
                startActivity(intentWelcome);
                break;
            case (R.id.search):
                return false;
            case (R.id.filter):
                Intent intentFilter = new Intent(this, FilterActivity.class);
                intentFilter.putExtra("username", USERNAME);
                intentFilter.putExtra("favorite_recipes", FAVORITE_RECIPES);
                intentFilter.putExtra("last_search", title.getText());
                startActivity(intentFilter);
                break;
        }
        return true;
    }

    private void recipeSearch(View v) {
        switchView();
        randomSearch("");
    }

    private void switchView() {
        findViewById(R.id.buttonSearch).setAlpha(0);
        findViewById(R.id.buttonSearch).setClickable(false);
        findViewById(R.id.scrollViewRecipe).setAlpha(1);
        findViewById(R.id.buttonNewRecipeSearch).setAlpha(1);
    }

    private void newRecipeSearch(View v) {
        TextView title = findViewById(R.id.textViewRecipeTitle);
        randomSearch(title.getText().toString());
    }

    private void randomSearch(String currentRecipe) {
        ArrayList<String> allValidRecipes = new ArrayList<>();

        CollectionReference collectionRef = db.collection("recipes");
        collectionRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (!currentRecipe.equals(document.getId())) {
                                allValidRecipes.add(document.getId());
                            }
                        }

                        String returnRecipe = allValidRecipes.get((int) Math.floor(Math.random() * allValidRecipes.size()));
                        TextView title = findViewById(R.id.textViewRecipeTitle);
                        title.setText(returnRecipe);

                        if (FAVORITE_RECIPES.contains(returnRecipe)) {
                            findViewById(R.id.buttonRemoveFromFavorites).setAlpha(1);
                            findViewById(R.id.buttonAddToFavorites).setClickable(false);
                            findViewById(R.id.buttonAddToFavorites).setAlpha(0);
                            findViewById(R.id.buttonRemoveFromFavorites).setClickable(true);
                        } else {
                            findViewById(R.id.buttonAddToFavorites).setAlpha(1);
                            findViewById(R.id.buttonRemoveFromFavorites).setClickable(false);
                            findViewById(R.id.buttonRemoveFromFavorites).setAlpha(0);
                            findViewById(R.id.buttonAddToFavorites).setClickable(true);
                        }

                        DocumentReference docRef = db.collection("recipes").document(returnRecipe);
                        docRef.get().addOnCompleteListener(docTask -> {
                            if (docTask.isSuccessful()) {
                                DocumentSnapshot document = docTask.getResult();
                                if (document.exists()) {
                                    TextView recipe = findViewById(R.id.textViewRecipe);
                                    String recipeText = "Prep time: " + document.getData().get("prep_time").toString() + "\n\n";
                                    recipeText += "Cook time: " + document.getData().get("cook_time").toString() + "\n\n\n";
                                    recipeText += "Ingredients:\n" + document.getData().get("measurements").toString().replace(";", "\n\n") + "\n";
                                    recipeText += document.getData().get("steps").toString().replace(";", "\n\n");
                                    recipe.setText(recipeText);
                                }
                            }
                        });
                    }
                });
    }

    private void addFavorite(View v) {
        findViewById(R.id.buttonAddToFavorites).setAlpha(0);
        findViewById(R.id.buttonRemoveFromFavorites).setAlpha(1);
        findViewById(R.id.buttonAddToFavorites).setClickable(false);
        findViewById(R.id.buttonRemoveFromFavorites).setClickable(true);

        TextView title = findViewById(R.id.textViewRecipeTitle);
        FAVORITE_RECIPES.add(title.getText().toString());

        Map<String, String> favoritesMap = new HashMap<>();
        favoritesMap.put("favorite_recipes", String.join(",", FAVORITE_RECIPES));

        db.collection("users").document(USERNAME).set(favoritesMap, SetOptions.merge());
    }

    private void removeFavorite(View v) {
        findViewById(R.id.buttonAddToFavorites).setAlpha(1);
        findViewById(R.id.buttonRemoveFromFavorites).setAlpha(0);
        findViewById(R.id.buttonAddToFavorites).setClickable(true);
        findViewById(R.id.buttonRemoveFromFavorites).setClickable(false);

        TextView title = findViewById(R.id.textViewRecipeTitle);
        FAVORITE_RECIPES.remove(title.getText().toString());

        Map<String, String> favoritesMap = new HashMap<>();
        favoritesMap.put("favorite_recipes", String.join(",", FAVORITE_RECIPES));

        db.collection("users").document(USERNAME).set(favoritesMap, SetOptions.merge());
    }

    private void immediateSearch(String recipeName) {
        switchView();
        specificSearch(recipeName);
    }

    private void specificSearch(String recipeName) {
        if (FAVORITE_RECIPES.contains(recipeName)) {
            findViewById(R.id.buttonRemoveFromFavorites).setAlpha(1);
            findViewById(R.id.buttonAddToFavorites).setClickable(false);
            findViewById(R.id.buttonAddToFavorites).setAlpha(0);
            findViewById(R.id.buttonRemoveFromFavorites).setClickable(true);
        } else {
            findViewById(R.id.buttonAddToFavorites).setAlpha(1);
            findViewById(R.id.buttonRemoveFromFavorites).setClickable(false);
            findViewById(R.id.buttonRemoveFromFavorites).setAlpha(0);
            findViewById(R.id.buttonAddToFavorites).setClickable(true);
        }

        TextView title = findViewById(R.id.textViewRecipeTitle);
        title.setText(recipeName);

        DocumentReference docRef = db.collection("recipes").document(recipeName);
        docRef.get().addOnCompleteListener(docTask -> {
            if (docTask.isSuccessful()) {
                DocumentSnapshot document = docTask.getResult();
                if (document.exists()) {
                    TextView recipe = findViewById(R.id.textViewRecipe);
                    String recipeText = "Prep time: " + document.getData().get("prep_time").toString() + "\n\n";
                    recipeText += "Cook time: " + document.getData().get("cook_time").toString() + "\n\n\n";
                    recipeText += "Ingredients:\n" + document.getData().get("measurements").toString().replace(";", "\n\n") + "\n";
                    recipeText += document.getData().get("steps").toString().replace(";", "\n\n");
                    recipe.setText(recipeText);
                }
            }
        });
    }
}