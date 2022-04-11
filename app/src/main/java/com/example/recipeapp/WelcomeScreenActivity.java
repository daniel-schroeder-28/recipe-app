package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class WelcomeScreenActivity extends AppCompatActivity implements FavoritesAdapter.ListItemClickListener {

    public static String USERNAME = "";
    public static String LAST_SEARCH = "";
    public static ArrayList<String> FAVORITE_RECIPES = new ArrayList<>();
    public static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Intent intent = getIntent();
        USERNAME = intent.getStringExtra("username");
        FAVORITE_RECIPES = intent.getStringArrayListExtra("favorite_recipes");
        LAST_SEARCH = intent.getStringExtra("last_search");

        if (USERNAME == null) {
            USERNAME = intent.getStringExtra(NewUserActivity.USERNAME);
        }

        TextView textView = findViewById(R.id.textViewWelcomeUser);
        if (USERNAME != null) {
            textView.setText("Welcome " + USERNAME + "!");
        } else {
            textView.setText("Welcome!");
        }

        recyclerView = findViewById(R.id.recyclerViewFavoriteRecipes);
        if (FAVORITE_RECIPES.size() > 0 && FAVORITE_RECIPES.get(0) != "") {
            FavoritesAdapter adapter = new FavoritesAdapter(FAVORITE_RECIPES, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        NavigationBarView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.welcome);
        navBar.setOnItemSelectedListener(this::navigate);
    }

    private boolean navigate(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case (R.id.welcome):
                return false;
            case (R.id.search):
                Intent intentSearch = new Intent(this, SearchActivity.class);
                intentSearch.putExtra("username", USERNAME);
                intentSearch.putExtra("favorite_recipes", FAVORITE_RECIPES);
                intentSearch.putExtra("immediate_search", LAST_SEARCH);
                startActivity(intentSearch);
                break;
            case (R.id.filter):
                Intent intentFilter = new Intent(this, FilterActivity.class);
                intentFilter.putExtra("username", USERNAME);
                intentFilter.putExtra("favorite_recipes", FAVORITE_RECIPES);
                intentFilter.putExtra("last_search", LAST_SEARCH);
                startActivity(intentFilter);
                break;
        }
        return true;
    }

    private void viewRecipe(int position) {
        TextView textView = recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.textViewRecipeName);

        Intent intentSearch = new Intent(this, SearchActivity.class);
        intentSearch.putExtra("username", USERNAME);
        intentSearch.putExtra("favorite_recipes", FAVORITE_RECIPES);
        intentSearch.putExtra("immediate_search", textView.getText());
        startActivity(intentSearch);
    }

    @Override
    public void onListItemClick(int position) {
        viewRecipe(position);
    }
}