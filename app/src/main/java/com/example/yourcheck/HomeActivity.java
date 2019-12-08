package com.example.yourcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
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

        buttonRequestCheckHome.setOnClickListener(this);
        buttonCheckHistoryHome.setOnClickListener(this);
        buttonRunCheckHome.setOnClickListener(this);
        buttonViewCheckHome.setOnClickListener(this);
        buttonSendVerificationHome.setOnClickListener(this);

        textViewUsernameHome.setText("Hello, " + currentUser.getEmail());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonRequestCheckHome:
                Intent requestIntent = new Intent(this, RequestActivity.class);
                startActivity(requestIntent);
                break;

            case R.id.buttonCheckHistoryHome:
                Intent historyIntent = new Intent(this, HistoryActivity.class);
                startActivity(historyIntent);
                break;

            case R.id.buttonRunCheckHome:
                Intent checkIntent = new Intent(this, CheckActivity.class);
                startActivity(checkIntent);
                break;

            case R.id.buttonViewCheckHome:
                Intent resultsIntent = new Intent(this, ResultsActivity.class);
                startActivity(resultsIntent);
                break;

            case R.id.buttonSendVerificationHome:
                Intent sendIntent = new Intent(this, SendActivity.class);
                startActivity(sendIntent);
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
