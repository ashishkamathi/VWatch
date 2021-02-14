package com.example.vwatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

public class SignUp extends AppCompatActivity {
    EditText password, email, name, username1;
    FirebaseAuth fAuth;
    TextView login;
    Button signup;
    FirebaseFirestore fStore;
    String userID;
    ArrayList<String> shows = new ArrayList<String>();
    String current="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        password = findViewById(R.id.editTextPassword);
        email = findViewById(R.id.editTextTextEmailAddress);
        signup = findViewById(R.id.signupbtn);
        name = findViewById(R.id.editTextTextPersonName);
        username1 = findViewById(R.id.EditTextUsername);
        login = findViewById(R.id.textView2);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uemail = email.getText().toString().trim();
                String upassword = password.getText().toString().trim();
                String uname = name.getText().toString().trim();
                String username = username1.getText().toString().trim();


                if (TextUtils.isEmpty(uemail)) {
                    email.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(upassword)) {
                    password.setError("Password is Required.");
                    return;
                }

                if (upassword.length() < 6) {
                    password.setError("Password Must be >= 6 Characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(uemail, upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            userID = fAuth.getCurrentUser().getUid();

                            fStore.collection("users")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                boolean flag = false;

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    String temp = document.get("username").toString();
                                                    Log.v("test",document.getData().toString());

                                                    if (temp.contains(username)) {
                                                        flag = true;

                                                    } else {

                                                    }
                                                }
                                                if (flag == false) {

                                                    DocumentReference documentReference = fStore.collection("users").document(userID);
                                                    Map<String, Object> user = new HashMap<>();
                                                    user.put("username", username);
                                                    documentReference.set(user);
                                                    DocumentReference documentReference1 = fStore.collection("users").document(username);
                                                    Map<String, Object> user1 = new HashMap<>();
                                                    user1.put("fName", uname);
                                                    user1.put("email", uemail);
                                                    user1.put("username", username+1);
                                                    user1.put("showslist", shows);
                                                    user1.put("current watch", current);

                                                    documentReference1.set(user1);
                                                    Toast.makeText(SignUp.this, "User Created.", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                } else {
                                                    username1.setError("Username already exists");

                                                }
                                            } else {


                                            }
                                        }
                                    });


                        } else {
                            Toast.makeText(SignUp.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                });


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }
}