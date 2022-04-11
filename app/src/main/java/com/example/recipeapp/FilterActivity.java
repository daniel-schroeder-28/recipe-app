package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    private static String USERNAME = "";
    private static String LAST_SEARCH = "";
    public static ArrayList<String> FAVORITE_RECIPES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Intent intent = getIntent();
        USERNAME = intent.getStringExtra("username");
        FAVORITE_RECIPES = intent.getStringArrayListExtra("favorite_recipes");
        LAST_SEARCH = intent.getStringExtra("last_search");

        NavigationBarView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.filter);
        navBar.setOnItemSelectedListener(this::navigate);
    }

    /**
     * Function to navigate between pages passing relevent data between them
     * @param menuItem
     * @return
     */
    private boolean navigate(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case (R.id.welcome):
                Intent intentWelcome = new Intent(this, WelcomeScreenActivity.class);
                intentWelcome.putExtra("username", USERNAME);
                intentWelcome.putExtra("favorite_recipes", FAVORITE_RECIPES);
                intentWelcome.putExtra("last_search", LAST_SEARCH);
                startActivity(intentWelcome);
                break;
            case (R.id.search):
                Intent intentSearch = new Intent(this, SearchActivity.class);
                intentSearch.putExtra("username", USERNAME);
                intentSearch.putExtra("favorite_recipes", FAVORITE_RECIPES);
                intentSearch.putExtra("immediate_search", LAST_SEARCH);
                startActivity(intentSearch);
                break;
            case (R.id.filter):
                return false;
        }
        return true;
    }
}