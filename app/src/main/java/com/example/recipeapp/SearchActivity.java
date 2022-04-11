package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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

    /**
     * Function to navigate between pages passing relevent data between them
     * @param menuItem
     * @return
     */
    public boolean navigate(MenuItem menuItem) {
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

    /**
     * Function that searches all recipes - used by main button on search page
     * @param v
     */
    public void recipeSearch(View v) {
        switchView();
        randomSearch("");
    }

    /**
     * Switches between initial search screen and search screen with recipe on it
     */
    private void switchView() {
        findViewById(R.id.buttonSearch).setAlpha(0);
        findViewById(R.id.buttonSearch).setClickable(false);
        findViewById(R.id.scrollViewRecipe).setAlpha(1);
        findViewById(R.id.buttonNewRecipeSearch).setAlpha(1);
    }

    /**
     * Searches all recipes excluding the one we are currently looking at
     * @param v
     */
    public void newRecipeSearch(View v) {
        TextView title = findViewById(R.id.textViewRecipeTitle);
        randomSearch(title.getText().toString());
    }

    /**
     * Split out from other search functions to keep code in one place
     * Does the search through the database to get all documents excluding currentRecipe
     * @param currentRecipe - the recipe we are currently looking at
     */
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

                        String recipeName = allValidRecipes.get((int) Math.floor(Math.random() * allValidRecipes.size()));
                        TextView title = findViewById(R.id.textViewRecipeTitle);
                        title.setText(recipeName);

                        favoriteVsUnfavorite(recipeName);
                        searchAndFillViews(recipeName);
                    }
                });
    }

    /**
     * Adds current recipe to favorites and changes buttons from favorite to unfavorite
     * @param v
     */
    public void addFavorite(View v) {
        TextView title = findViewById(R.id.textViewRecipeTitle);
        FAVORITE_RECIPES.add(title.getText().toString());

        favoriteVsUnfavorite(title.getText().toString());

        Map<String, String> favoritesMap = new HashMap<>();
        favoritesMap.put("favorite_recipes", String.join(",", FAVORITE_RECIPES));

        db.collection("users").document(USERNAME).set(favoritesMap, SetOptions.merge());
    }

    /**
     * Removes current recipe from favorites and changes buttons from unfavorite to favorite
     * @param v
     */
    public void removeFavorite(View v) {
        TextView title = findViewById(R.id.textViewRecipeTitle);
        FAVORITE_RECIPES.remove(title.getText().toString());

        favoriteVsUnfavorite(title.getText().toString());

        Map<String, String> favoritesMap = new HashMap<>();
        favoritesMap.put("favorite_recipes", String.join(",", FAVORITE_RECIPES));

        db.collection("users").document(USERNAME).set(favoritesMap, SetOptions.merge());
    }

    /**
     * Immediately searches for a recipe upon landing on search page instead of needing to click search
     * @param recipeName - the recipe to search for
     */
    private void immediateSearch(String recipeName) {
        switchView();
        findSpecificRecipe(recipeName);
    }

    /**
     * Searches for a specific recipe rather than randomly searching
     * @param recipeName - the recipe to search for
     */
    private void findSpecificRecipe(String recipeName) {
        TextView title = findViewById(R.id.textViewRecipeTitle);
        title.setText(recipeName);

        favoriteVsUnfavorite(recipeName);
        searchAndFillViews(recipeName);
    }

    /**
     * Split out from other functions to keep code in one place
     * finds the recipe in the db and then fills in the screen with the recipe
     * @param recipeName
     */
    private void searchAndFillViews(String recipeName) {
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

    /**
     * Swithes between favorite or unfavorite based on in the recipe is favorited
     * @param recipeName - the recipe to check if its favorited
     */
    private void favoriteVsUnfavorite(String recipeName) {
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
    }
}