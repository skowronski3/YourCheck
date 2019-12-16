package com.example.yourcheck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.KeyStore;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewUsernameResults, textViewUsernameTitleResults;
    ToggleButton toggleButtonNationalCriminalResults, toggleButtonCountyCriminalResults, toggleButtonGlobalWatchResults, toggleButtonExtensiveCriminalResults;
    Button buttonBackHomeResults;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("allchecks");

        textViewUsernameResults = findViewById(R.id.textViewUsernameResults);
        textViewUsernameTitleResults = findViewById(R.id.textViewUsernameTitleResults);

        toggleButtonNationalCriminalResults = findViewById(R.id.toggleButtonNationalCriminalResults);
        toggleButtonCountyCriminalResults = findViewById(R.id.toggleButtonCountyCriminalResults);
        toggleButtonGlobalWatchResults = findViewById(R.id.toggleButtonGlobalWatchResults);
        toggleButtonExtensiveCriminalResults = findViewById(R.id.toggleButtonExtensiveCriminalResults);
        buttonBackHomeResults = findViewById(R.id.buttonBackHomeResults);

        buttonBackHomeResults.setOnClickListener(this);

        textViewUsernameResults.setText("Hello, " + currentUser.getEmail());
        textViewUsernameTitleResults.setText(currentUser.getEmail());

        myRef.orderByChild("userID").equalTo(currentUser.getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Check foundCheck = dataSnapshot.getValue(Check.class);
                String nationalStatus = foundCheck.nationalStatus;
                String countyStatus = foundCheck.countyStatus;
                String watchListStatus = foundCheck.watchListStatus;
                String extensiveStatus = foundCheck.extensiveStatus;

                toggleButtonNationalCriminalResults.setEnabled(false);
                toggleButtonCountyCriminalResults.setEnabled(false);
                toggleButtonGlobalWatchResults.setEnabled(false);
                toggleButtonExtensiveCriminalResults.setEnabled(false);

                if (nationalStatus.equals("PASS")) {
                    toggleButtonNationalCriminalResults.setTextOn("PASS");
                    toggleButtonNationalCriminalResults.setChecked(true);
                    toggleButtonNationalCriminalResults.setBackgroundColor(Color.GREEN);
                } else if (nationalStatus.equals("FAIL")) {
                    toggleButtonNationalCriminalResults.setTextOn("FAIL");
                    toggleButtonNationalCriminalResults.setChecked(true);
                    toggleButtonNationalCriminalResults.setBackgroundColor(Color.RED);
                }

                if (countyStatus.equals("PASS")) {
                    toggleButtonCountyCriminalResults.setTextOn("PASS");
                    toggleButtonCountyCriminalResults.setChecked(true);
                    toggleButtonCountyCriminalResults.setBackgroundColor(Color.GREEN);
                } else if (countyStatus.equals("FAIL")) {
                    toggleButtonCountyCriminalResults.setTextOn("FAIL");
                    toggleButtonCountyCriminalResults.setChecked(true);
                    toggleButtonCountyCriminalResults.setBackgroundColor(Color.RED);
                }

                if (watchListStatus.equals("PASS")) {
                    toggleButtonGlobalWatchResults.setTextOn("PASS");
                    toggleButtonGlobalWatchResults.setChecked(true);
                    toggleButtonGlobalWatchResults.setBackgroundColor(Color.GREEN);
                } else if (watchListStatus.equals("FAIL")) {
                    toggleButtonGlobalWatchResults.setTextOn("FAIL");
                    toggleButtonGlobalWatchResults.setChecked(true);
                    toggleButtonGlobalWatchResults.setBackgroundColor(Color.RED);
                }

                if (extensiveStatus.equals("PASS")) {
                    toggleButtonExtensiveCriminalResults.setTextOn("PASS");
                    toggleButtonExtensiveCriminalResults.setChecked(true);
                    toggleButtonExtensiveCriminalResults.setBackgroundColor(Color.GREEN);
                } else if (extensiveStatus.equals("FAIL")) {
                    toggleButtonExtensiveCriminalResults.setTextOn("FAIL");
                    toggleButtonExtensiveCriminalResults.setChecked(true);
                    toggleButtonExtensiveCriminalResults.setBackgroundColor(Color.RED);
                }
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
            case R.id.buttonBackHomeResults:
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
