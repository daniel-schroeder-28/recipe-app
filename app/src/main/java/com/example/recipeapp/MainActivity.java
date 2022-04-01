package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static String host = "recipesapp.cntre7imdws6.us-east-1.rds.amazonaws.com";
    private static String port = "8080";
    private static String Classes = "org.postgresql.Driver";
    private static String database = "RecipeApp";
    private static String dbUsername = "postgresAdmin";
    private static String dbPassword = "5KBIlZPPj5LhBOclvdDx";
    private static String url = "jdbc:postgresql://"+host+":"+port+"/"+database;

    private Connection connection = null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button_signIn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login(v);
            }
        });
    }

    public void login(View v) {
        /**String user = ((EditText) findViewById(R.id.editTextTextPersonName)).getText().toString();
        String pass = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();
        String query = "SELECT * FROM Users WHERE username = '" + user + "' AND pass = '" + pass + "';";
        System.out.println(query);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                Intent intent = new Intent(this, HomeScreenActivity.class);
                startActivity(intent);
            } else {
                findViewById(R.id.textView3).setAlpha(1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        Map<String, Object> user = new HashMap<>();
        user.put("user_id", "1");
        user.put("name", "Daniel");
        user.put("pass", "Test");


// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}