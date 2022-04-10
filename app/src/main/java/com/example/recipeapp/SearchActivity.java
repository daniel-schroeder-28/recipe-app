package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationBarView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        NavigationBarView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.search);
        navBar.setOnItemSelectedListener(this::navigate);

        Button button = findViewById(R.id.buttonSearch);
        button.setOnClickListener(this::recipeSearch);
    }

    private boolean navigate(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case (R.id.welcome):
                Intent intentWelcome = new Intent(this, WelcomeScreenActivity.class);
                startActivity(intentWelcome);
                break;
            case (R.id.search):
                return false;
            case (R.id.filter):
                Intent intentFilter = new Intent(this, FilterActivity.class);
                startActivity(intentFilter);
                break;
        }
        return true;
    }

    private void recipeSearch(View v) {
        findViewById(R.id.buttonSearch).setAlpha(0);
        findViewById(R.id.scrollViewRecipe).setAlpha(1);
        findViewById(R.id.buttonNewRecipeSearch).setAlpha(1);
    }
}