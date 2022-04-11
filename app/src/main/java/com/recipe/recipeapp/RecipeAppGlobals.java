package com.recipe.recipeapp;

import android.app.Application;

import java.util.ArrayList;

public class RecipeAppGlobals extends Application {
    private static String USERNAME = "";
    private static String LAST_SEARCH = "";
    private static ArrayList<String> FAVORITE_RECIPES = new ArrayList<>();

    public static String getUsername() {
        return USERNAME;
    }

    public static void setUsername(String username) {
        RecipeAppGlobals.USERNAME = username;
    }

    public static String getLastSearch() {
        return LAST_SEARCH;
    }

    public static void setLastSearch(String lastSearch) {
        LAST_SEARCH = lastSearch;
    }

    public static ArrayList<String> getFavoriteRecipes() {
        return FAVORITE_RECIPES;
    }

    public static void setFavoriteRecipes(ArrayList<String> favoriteRecipes) {
        FAVORITE_RECIPES = favoriteRecipes;
    }

    public static void addToFavoriteRecipes(String recipeName) {
        FAVORITE_RECIPES.add(recipeName);
    }

    public static void removeFromFavoriteRecipes(String recipeName) {
        FAVORITE_RECIPES.remove(recipeName);
    }
}
