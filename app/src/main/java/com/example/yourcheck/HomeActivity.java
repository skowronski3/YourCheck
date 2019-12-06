package com.example.yourcheck;

import androidx.annotation.NonNull;
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

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewUsernameHome;
    Button buttonRequestCheckHome, buttonCheckHistoryHome, buttonRunCheckHome, buttonViewCheckHome, buttonSendVerificationHome;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        textViewUsernameHome = findViewById(R.id.textViewUsernameHome);
        buttonRequestCheckHome = findViewById(R.id.buttonRequestCheckHome);
        buttonCheckHistoryHome = findViewById(R.id.buttonCheckHistoryHome);
        buttonRunCheckHome = findViewById(R.id.buttonRunCheckHome);
        buttonViewCheckHome = findViewById(R.id.buttonViewCheckHome);
        buttonSendVerificationHome = findViewById(R.id.buttonSendVerificationHome);

        textViewUsernameHome.setText("Hello, " + currentUser.getEmail());
    }

    @Override
    public void onClick(View view) {

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
            Toast.makeText(this, "Already in Home!", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.itemLogout) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
