package com.example.vwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {
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
                    adapter = new ArrayAdapter<String>(Profile.this,android.R.layout.simple_list_item_activated_1,shows);
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
        userID = fAuth.getCurrentUser().getUid();
        Log.d("UID",userID);


        DocumentReference documentReference1 = fStore.collection("users").document(userID);
        documentReference1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                   username1= documentSnapshot.getString("username");
                   Log.d("Username",username1);
                   doc(username1);

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }

        });












    }
}