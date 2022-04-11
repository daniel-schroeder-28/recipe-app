package com.recipe.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationBarView;

public class WelcomeScreenActivity extends AppCompatActivity implements FavoritesAdapter.ListItemClickListener {


    public static RecyclerView recyclerView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Intent intent = getIntent();
        RecipeAppGlobals.setUsername(intent.getStringExtra("username"));
        RecipeAppGlobals.setFavoriteRecipes(intent.getStringArrayListExtra("favorite_recipes"));
        RecipeAppGlobals.setLastSearch(intent.getStringExtra("last_search"));

        TextView textView = findViewById(R.id.textViewWelcomeUser);
        if (RecipeAppGlobals.getUsername() != null) {
            textView.setText("Welcome " + RecipeAppGlobals.getUsername() + "!");
        } else {
            textView.setText("Welcome!");
        }

        recyclerView = findViewById(R.id.recyclerViewFavoriteRecipes);
        if (RecipeAppGlobals.getFavoriteRecipes().size() > 0 && !RecipeAppGlobals.getFavoriteRecipes().get(0).equals("")) {
            FavoritesAdapter adapter = new FavoritesAdapter(RecipeAppGlobals.getFavoriteRecipes(), this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        NavigationBarView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.welcome);
        navBar.setOnItemSelectedListener(this::navigate);
    }

    /**
     * Function to navigate between pages passing relevant data between them
     * @param menuItem - menuItem clicked on
     * @return - true or false
     */
    private boolean navigate(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case (R.id.welcome):
                return false;
            case (R.id.search):
                Intent intentSearch = new Intent(this, SearchActivity.class);
                intentSearch.putExtra("username", RecipeAppGlobals.getUsername());
                intentSearch.putExtra("favorite_recipes", RecipeAppGlobals.getFavoriteRecipes());
                intentSearch.putExtra("last_search", RecipeAppGlobals.getLastSearch());
                startActivity(intentSearch);
                break;
            case (R.id.filter):
                Intent intentFilter = new Intent(this, FilterActivity.class);
                intentFilter.putExtra("username", RecipeAppGlobals.getUsername());
                intentFilter.putExtra("favorite_recipes", RecipeAppGlobals.getFavoriteRecipes());
                intentFilter.putExtra("last_search", RecipeAppGlobals.getLastSearch());
                startActivity(intentFilter);
                break;
        }
        return true;
    }

    /**
     * Function to view specific recipe from favorites list
     * @param position - which position in list is clicked
     */
    private void viewRecipe(int position) {
        TextView textView = recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.textViewRecipeName);

        Intent intentSearch = new Intent(this, SearchActivity.class);
        intentSearch.putExtra("username", RecipeAppGlobals.getUsername());
        intentSearch.putExtra("favorite_recipes", RecipeAppGlobals.getFavoriteRecipes());
        intentSearch.putExtra("last_search", textView.getText());
        startActivity(intentSearch);
    }

    @Override
    public void onListItemClick(int position) {
        viewRecipe(position);
    }
}