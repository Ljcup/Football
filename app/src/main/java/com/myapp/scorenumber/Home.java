package com.myapp.scorenumber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    private EditText etName,etEmail,etDOB;
    private Button btcontinue;
    private String name,email,dob,gender;
    private RadioGroup rbgroup;
    private RadioButton rbFemale,rbMale;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        etName = (EditText)findViewById(R.id.etPersonName);
        etEmail = (EditText)findViewById(R.id.etEmailAddress);
        etDOB = (EditText)findViewById(R.id.etDate);

        rbgroup =(RadioGroup)findViewById(R.id.radioGroup);
        rbMale =(RadioButton)findViewById(R.id.rbmale);
        rbFemale =(RadioButton)findViewById(R.id.rbfemale);

        btcontinue = (Button)findViewById(R.id.btcontinue);

        FirebaseFirestore db = FirebaseFirestore.getInstance();



        rbgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbfemale:
                        gender = (String) rbFemale.getText();

                        break;
                    case R.id.rbmale:
                        gender = (String)rbMale.getText();

                        break;
                }
            }
        });

        btcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                dob = etDOB.getText().toString().trim();

                if(name.isEmpty() || email.isEmpty() || dob.isEmpty()){
                    Toast.makeText(Home.this,"Enter all data",Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, String> user = new HashMap<>();
                    user.put("Name",name);
                    user.put("Email",email);
                    user.put("Gender",gender);
                    user.put("DOB",dob);

                    db.collection("player_user")
                            .document(mAuth.getUid())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Home.this,"Successful",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Home.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

    }

}