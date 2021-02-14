package com.example.vwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
     static String username;
    static EditText usersearch;
    Button button,search;
    FirebaseFirestore db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.profile:
                startActivity(new Intent(getApplicationContext(),Profile.class));
                return true;
            case R.id.watch:
                startActivity(new Intent(getApplicationContext(),AddWatch.class));
                return true;
            default:
                return false;

        }
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search=findViewById(R.id.button2);
        usersearch=findViewById(R.id.usersearchtext);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                username=usersearch.getText().toString();
                startActivity(new Intent(getApplicationContext(),searchuser.class));

            }
        });









    }
}