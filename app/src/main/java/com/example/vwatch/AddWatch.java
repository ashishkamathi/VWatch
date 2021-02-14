package com.example.vwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class AddWatch extends AppCompatActivity {
    EditText mov,cur;
    Button add,update;
    FirebaseFirestore fStore;

    FirebaseAuth fAuth;
    String userID;
    String username1;
    ArrayList<String> shows = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_watch);
        mov=findViewById(R.id.addmov);
        cur=findViewById(R.id.currentwatch);
        add=findViewById(R.id.AddBtn);
        update=findViewById(R.id.button3);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        userID = fAuth.getCurrentUser().getUid();



        DocumentReference documentReference1 = fStore.collection("users").document(userID);
        documentReference1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    username1= documentSnapshot.getString("username");
                    Log.d("Username",username1);


                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = cur.getText().toString().trim();
                fStore
                        .collection("users")
                        .document(username1)
                        .update("current watch",temp);
                Toast.makeText(AddWatch.this, "Added", Toast.LENGTH_SHORT).show();
            }


        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = mov.getText().toString().trim();


                fStore
                        .collection("users")
                        .document(username1)
                        .update("showslist", FieldValue.arrayUnion(temp));

                Toast.makeText(AddWatch.this, "Added", Toast.LENGTH_SHORT).show();




            }
        });


    }

}