package com.recipe.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    public FirebaseUser user;
    public CheckBox milk;
    public CheckBox eggs;
    public CheckBox fish;
    public CheckBox shellfish;
    public CheckBox treeNuts;
    public CheckBox peanuts;
    public CheckBox wheat;
    public CheckBox soy;
    public CheckBox tomato;
    public CheckBox meat;
    public CheckBox poultry;
    public CheckBox mushroom;
    public CheckBox onion;
    public CheckBox tofu;
    public CheckBox cilantro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        NavigationBarView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.filter);
        navBar.setOnItemSelectedListener(this::navigate);

        milk = findViewById(R.id.checkBoxMilk);
        eggs = findViewById(R.id.checkBoxEggs);
        fish = findViewById(R.id.checkBoxFish);
        shellfish = findViewById(R.id.checkBoxShellfish);
        treeNuts = findViewById(R.id.checkBoxTreeNuts);
        peanuts = findViewById(R.id.checkBoxPeanuts);
        wheat = findViewById(R.id.checkBoxWheat);
        soy = findViewById(R.id.checkBoxSoy);
        tomato = findViewById(R.id.checkBoxTomato);
        meat = findViewById(R.id.checkBoxMeat);
        poultry = findViewById(R.id.checkBoxPoultry);
        mushroom = findViewById(R.id.checkBoxMushroom);
        onion = findViewById(R.id.checkBoxOnion);
        tofu = findViewById(R.id.checkBoxTofu);
        cilantro = findViewById(R.id.checkBoxCilantro);

        loadFilters();
    }

    /**
     * Function to navigate between pages passing relevant data between them
     * @param menuItem - item clicked
     * @return - true or false
     */
    private boolean navigate(MenuItem menuItem) {
        setFilters();
        switch (menuItem.getItemId()) {
            case (R.id.welcome):
                Intent intentWelcome = new Intent(this, WelcomeScreenActivity.class);
                intentWelcome.putExtra("user",user);
                startActivity(intentWelcome);
                break;
            case (R.id.search):
                Intent intentSearch = new Intent(this, SearchActivity.class);
                intentSearch.putExtra("user",user);
                startActivity(intentSearch);
                break;
            case (R.id.filter):
                return false;
        }
        return true;
    }

    private void loadFilters() {
        ArrayList<String> filters = RecipeAppGlobals.getFilters();

        for (String filter : filters) {
            switch (filter) {
                case "milk":
                    milk.setChecked(true);
                    break;
                case "eggs":
                    eggs.setChecked(true);
                    break;
                case "fish":
                    fish.setChecked(true);
                    break;
                case "shellfish":
                    shellfish.setChecked(true);
                    break;
                case "treeNuts":
                    treeNuts.setChecked(true);
                    break;
                case "peanuts":
                    peanuts.setChecked(true);
                    break;
                case "wheat":
                    wheat.setChecked(true);
                    break;
                case "soy":
                    soy.setChecked(true);
                    break;
                case "tomato":
                    tomato.setChecked(true);
                    break;
                case "meat":
                    meat.setChecked(true);
                    break;
                case "poultry":
                    poultry.setChecked(true);
                    break;
                case "mushroom":
                    mushroom.setChecked(true);
                    break;
                case "onion":
                    onion.setChecked(true);
                    break;
                case "tofu":
                    tofu.setChecked(true);
                    break;
                case "cilantro":
                    cilantro.setChecked(true);
                    break;
            }
        }
    }

    private void setFilters() {
        ArrayList<String> filters = new ArrayList<>();

        if (milk.isChecked()) {
            filters.add("milk");
        }
        if (eggs.isChecked()) {
            filters.add("eggs");
        }
        if (fish.isChecked()) {
            filters.add("fish");
        }
        if (shellfish.isChecked()) {
            filters.add("shellfish");
        }
        if (treeNuts.isChecked()) {
            filters.add("treeNuts");
        }
        if (peanuts.isChecked()) {
            filters.add("peanuts");
        }
        if (wheat.isChecked()) {
            filters.add("wheat");
        }
        if (soy.isChecked()) {
            filters.add("soy");
        }
        if (tomato.isChecked()) {
            filters.add("tomato");
        }
        if (meat.isChecked()) {
            filters.add("meat");
        }
        if (poultry.isChecked()) {
            filters.add("poultry");
        }
        if (mushroom.isChecked()) {
            filters.add("mushroom");
        }
        if (onion.isChecked()) {
            filters.add("onion");
        }
        if (tofu.isChecked()) {
            filters.add("tofu");
        }
        if (cilantro.isChecked()) {
            filters.add("cilantro");
        }

        RecipeAppGlobals.setFilters(filters);
    }
}