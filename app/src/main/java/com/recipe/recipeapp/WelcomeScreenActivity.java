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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class WelcomeScreenActivity extends AppCompatActivity implements FavoritesAdapter.ListItemClickListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static RecyclerView recyclerView;
    FirebaseUser user;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        recyclerView = findViewById(R.id.recyclerViewFavoriteRecipes);

        if (user != null) {
            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        RecipeAppGlobals.setFavoriteRecipes(new ArrayList<>(Arrays.asList(document.getData().get("favorite_recipes").toString().split(","))));
                        if (RecipeAppGlobals.getFavoriteRecipes().size() > 0 && !RecipeAppGlobals.getFavoriteRecipes().get(0).equals("")) {
                            FavoritesAdapter adapter = new FavoritesAdapter(RecipeAppGlobals.getFavoriteRecipes(), this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        }
                    } else {
                        RecipeAppGlobals.setFavoriteRecipes(new ArrayList<>());
                    }
                } else {
                    RecipeAppGlobals.setFavoriteRecipes(new ArrayList<>());
                }
            });
        } else {
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
                intentSearch.putExtra("user", user);
                startActivity(intentSearch);
                break;
            case (R.id.filter):
                Intent intentFilter = new Intent(this, FilterActivity.class);
                intentFilter.putExtra("user", user);
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
        RecipeAppGlobals.setLastSearch(textView.getText().toString());
        Intent intentSearch = new Intent(this, SearchActivity.class);
        startActivity(intentSearch);
    }

    @Override
    public void onListItemClick(int position) {
        viewRecipe(position);
    }
}