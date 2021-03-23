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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
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

        name = new ArrayList<>();
        name.add("Ind");
        name.add("Eng");
        name.add("Aus");
        name.add("Can");

        adapter = new ArrayAdapter<>(Creatematch.this,R.layout.support_simple_spinner_dropdown_item,name);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView2.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }
}