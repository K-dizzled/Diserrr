package com.diserauto.disertest;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    EditText mEdit;
    Button sButton;
    RecyclerView mRec;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<String> user = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mEdit = findViewById(R.id.enter_messsge);
        sButton = findViewById(R.id.send_button);

        mRec = findViewById(R.id.messages);

        mRec.setLayoutManager(new LinearLayoutManager(this));

        final DataAdapter dataAdapter = new DataAdapter(this, messages);
        mRec.setAdapter(dataAdapter);

        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mss = mEdit.getText().toString();
                if (mss.equals("")){
                    Toast.makeText(getApplicationContext(), "Не молчи",Toast.LENGTH_SHORT);
                    return;
                }
                if (mss.length()==100){
                    Toast.makeText(getApplicationContext(), "Слишком много",Toast.LENGTH_SHORT);
                    return;
                }
                myRef.push().setValue(mss);
                mEdit.setText("");
            }
        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String msg = dataSnapshot.getValue(String.class);
                messages.add(msg);
                dataAdapter.notifyDataSetChanged();
                mRec.smoothScrollToPosition(messages.size());
                //displayUser();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
