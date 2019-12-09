package com.example.yourcheck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewUsernameHistory, textViewDisplayHistory;
    Button buttonBackHomeHistory;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        textViewUsernameHistory = findViewById(R.id.textViewUsernameHistory);
        textViewDisplayHistory = findViewById(R.id.textViewDisplayHistory);
        buttonBackHomeHistory = findViewById(R.id.buttonBackHomeHistory);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        textViewUsernameHistory.setText("Hello, " + currentUser.getEmail());

        buttonBackHomeHistory.setOnClickListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("allchecks");
        myRef.orderByChild("sharedWith").equalTo(currentUser.getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Check foundCheck = dataSnapshot.getValue(Check.class);
                textViewDisplayHistory.append(foundCheck.userID);
                //Toast.makeText(HistoryActivity.this, foundCheck.userID, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonBackHomeHistory:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemHome) {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        } else if (item.getItemId() == R.id.itemLogout) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
