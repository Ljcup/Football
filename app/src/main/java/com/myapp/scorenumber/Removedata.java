package com.myapp.scorenumber;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Removedata {

    private ArrayList<Person> mylist;
    private int position;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Removedata(ArrayList<Person> mylist, int position) {
        this.mylist = mylist;
        this.position = position;
    }

    public Person findndelete(){
        Person temp;
        String tempid;

        temp = mylist.get(position);
        tempid = temp.getDocid();
        Log.d("Remove data:",temp+"");
        Log.d("Document Id",temp.getDocid());
        db.collection("table")
                .document(tempid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Deleted",tempid );
                    }
                });
        return temp;
    }
}

