package com.recipe.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Intent intent = getIntent();
        RecipeAppGlobals.setUsername(intent.getStringExtra("username"));
        RecipeAppGlobals.setFavoriteRecipes(intent.getStringArrayListExtra("favorite_recipes"));
        RecipeAppGlobals.setLastSearch(intent.getStringExtra("last_search"));

        NavigationBarView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.filter);
        navBar.setOnItemSelectedListener(this::navigate);
    }

    /**
     * Function to navigate between pages passing relevant data between them
     * @param menuItem - item clicked
     * @return - true or false
     */
    private boolean navigate(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case (R.id.welcome):
                Intent intentWelcome = new Intent(this, WelcomeScreenActivity.class);
                intentWelcome.putExtra("username", RecipeAppGlobals.getUsername());
                intentWelcome.putExtra("favorite_recipes", RecipeAppGlobals.getFavoriteRecipes());
                intentWelcome.putExtra("last_search", RecipeAppGlobals.getLastSearch());
                startActivity(intentWelcome);
                break;
            case (R.id.search):
                Intent intentSearch = new Intent(this, SearchActivity.class);
                intentSearch.putExtra("username", RecipeAppGlobals.getUsername());
                intentSearch.putExtra("favorite_recipes", RecipeAppGlobals.getFavoriteRecipes());
                intentSearch.putExtra("last_search", RecipeAppGlobals.getLastSearch());
                startActivity(intentSearch);
                break;
            case (R.id.filter):
                return false;
        }
        return true;
    }
}