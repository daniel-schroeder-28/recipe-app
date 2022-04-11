package com.example.recipeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    interface ListItemClickListener {
        void onListItemClick(int position);
    }

    private ArrayList<String> recipes;
    final private ListItemClickListener itemOnClickListener;

    // Pass in the contact array into the constructor
    public FavoritesAdapter(ArrayList<String> recipeList, ListItemClickListener onClickListener) {
        recipes = recipeList;
        itemOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.favorites, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, itemOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {
        String recipe = recipes.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.textViewRecipeName;
        textView.setText(recipe);
        Button button = holder.buttonView;
        button.setText("View Recipe");
        button.setEnabled(true);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewRecipeName;
        public Button buttonView;
        private WeakReference<ListItemClickListener> clickListenerRef;

        public ViewHolder(View v, ListItemClickListener clickListener) {
            super(v);

            clickListenerRef = new WeakReference<>(clickListener);
            textViewRecipeName = v.findViewById(R.id.textViewRecipeName);
            buttonView = v.findViewById(R.id.buttonView);

            buttonView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListenerRef.get().onListItemClick(getAdapterPosition());
        }
    }
}
