package com.myapp.scorenumber;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Team_registration extends AppCompatActivity {

    private Button teamregBtn,addmem,addteamname;
    private ImageView imgclose;
    private EditText name, email , monbileno,dob,teamname;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private String gender;
    String docid;
    private RadioGroup gendergroup;
    private RadioButton rbmale,rbfemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_registration);

        teamregBtn = (Button) findViewById(R.id.teamregbtn);
        teamname = findViewById(R.id.TeamName);
        addteamname = findViewById(R.id.btnteamname);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        addteamname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    
                if(teamname.getText().toString().trim().isEmpty()){
                    Toast.makeText(Team_registration.this,"Enter Team Name",Toast.LENGTH_SHORT).show();
                }else {

                    Map<String,Object> team = new HashMap<>();
                    team.put("teamname",teamname.getText().toString().trim());
                    team.put("uui",mAuth.getUid());

                    db.collection("team_name")
                            .add(team)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Team_registration.this,"Successful",Toast.LENGTH_SHORT).show();
                                     docid = documentReference.getId();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Team_registration.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        teamregBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Team_registration.this);
                dialog.setContentView(R.layout.register_popup);
                imgclose = (ImageView) dialog.findViewById(R.id.btclose);
                name = (EditText)dialog.findViewById(R.id.edtregname);
                email = dialog.findViewById(R.id.edtregEmail);
                monbileno = dialog.findViewById(R.id.edtregnum);
                dob = dialog.findViewById(R.id.edtregdob);
                addmem = dialog.findViewById(R.id.btnregadd);

                //this is comment
                rbmale = (RadioButton)dialog.findViewById(R.id.regrbmale);
                rbfemale = (RadioButton)dialog.findViewById(R.id.regrbfemale);
                gendergroup = (RadioGroup)dialog.findViewById(R.id.tmregregradioGroup);

                gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.regrbfemale:
                                gender = rbfemale.getText().toString();
                                break;
                            case R.id.regrbmale:
                                gender = rbmale.getText().toString();
                                break;
                        }
                    }
                });
                
                addmem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int mobilenumber = 0;
                        final String strname = name.getText().toString().trim();
                        final String stremail = email.getText().toString().trim();
                        final String strdob = dob.getText().toString().trim();
                        mobilenumber =  Integer.parseInt(monbileno.getText().toString().trim()) ;

                        if (strname.isEmpty() || strdob.isEmpty() || stremail.isEmpty() || mobilenumber==0){
                            Toast.makeText(Team_registration.this,"Enter all data",Toast.LENGTH_SHORT).show();
                        }else {

                            Map<String,Object> teamdata = new HashMap<>();
                            teamdata.put("Name",strname);
                            teamdata.put("email",stremail);
                            teamdata.put("dob",strdob);
                            teamdata.put("Phone no.",mobilenumber);

                            db.collection("team_name").document(docid).collection(teamname.getText().toString().trim())
                                    .add(teamdata)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            Toast.makeText(Team_registration.this,"Done",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });

                imgclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

    }
}