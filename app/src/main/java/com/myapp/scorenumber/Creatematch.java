package com.myapp.scorenumber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Creatematch extends AppCompatActivity {

    private Button creatematch;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView,autoCompleteTextView2;

    ArrayList<String> name;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creatematch);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Match");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        creatematch = (Button)findViewById(R.id.btcreatematch);
        textInputLayout = findViewById(R.id.inputlayout);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference subjectsRef = rootRef.collection("UsersData");
        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextView.setAdapter(adapter);
        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("name");
                        subjects.add(subject);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextView2.setAdapter(adapter);
        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("name");
                        subjects.add(subject);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView2.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }
}