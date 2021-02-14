package com.example.vwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class searchuser extends AppCompatActivity {
    FirebaseFirestore fStore;
    TextView fullName,current;
    FirebaseAuth fAuth;
    String userID;
    String username1;
    ListView list;
    ArrayAdapter<String> adapter;
    String currentwatch,liststring;
    ArrayList<String> shows = new ArrayList<String>();

    public void doc (String username1){
        DocumentReference documentReference = fStore.collection("users").document(username1);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    Log.d("Test","IN Doc ");
                    fullName.setText(documentSnapshot.getString("fName"));
                    Log.d("Test","Out Doc ");

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
        DocumentReference documentReference1 = fStore.collection("users").document(username1);
        documentReference1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    Log.d("Test","IN Doc ");

                    current.setText("Currently watching:"+documentSnapshot.getString("current watch"));
                    Log.d("Test","Out Doc ");

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });


        DocumentReference documentReference3 = fStore.collection("users").document(username1);
        documentReference3.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){


                    shows=(ArrayList<String>)documentSnapshot.get("showslist");
                    list = (ListView) findViewById(R.id.list);
                    adapter = new ArrayAdapter<String>(searchuser.this,android.R.layout.simple_list_item_activated_1,shows);
                    list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.d("Shows",shows.toString());



                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });






    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fullName=findViewById(R.id.textView3);
        current=findViewById(R.id.textView6);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        username1=MainActivity.username;



        fStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean flag = true;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String temp = document.get("username").toString();
                                Log.v("test",document.getData().toString());

                                if (temp.contains(username1)) {
                                    flag = false;

                                } else {

                                }
                            }
                            if (flag == false) {
                                doc(username1);
                                Toast.makeText(searchuser.this, "User Found", Toast.LENGTH_SHORT).show();
                            } else {

                                MainActivity.usersearch.setError("User not found");
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            }

                        } else {


                        }
                    }
                });





    }
}