package com.example.yourcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewUsernameRequest;
    RadioButton radioButtonBasicRequest, radioButtonStandardReqeust, radioButtonAdvancedRequest;
    EditText editTextSubjectEmailRequest;
    Button buttonRequestCheckRequest, buttonBackHomeRequest;
    String subjectEmail;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        textViewUsernameRequest = findViewById(R.id.textViewUsernameRequest);
        radioButtonBasicRequest = findViewById(R.id.radioButtonBasicRequest);
        radioButtonStandardReqeust = findViewById(R.id.radioButtonStandardRequest);
        radioButtonAdvancedRequest = findViewById(R.id.radioButtonAdvancedRequest);
        editTextSubjectEmailRequest = findViewById(R.id.editTextSubjectEmailRequest);
        buttonRequestCheckRequest = findViewById(R.id.buttonRequestCheckRequest);
        buttonBackHomeRequest = findViewById(R.id.buttonBackHomeRequest);


        buttonRequestCheckRequest.setOnClickListener(this);
        buttonBackHomeRequest.setOnClickListener(this);

        textViewUsernameRequest.setText("Hello, " + currentUser.getEmail());
    }

    @Override
    public void onClick(View view) {

        final Intent homeIntent = new Intent(this, HomeActivity.class);

        switch (view.getId()) {

            case R.id.buttonRequestCheckRequest:
                subjectEmail = editTextSubjectEmailRequest.getText().toString();
                if (!radioButtonBasicRequest.isChecked() & !radioButtonStandardReqeust.isChecked() & !radioButtonAdvancedRequest.isChecked())
                    Toast.makeText(this, "Please select a check package.", Toast.LENGTH_SHORT).show();
                else if (subjectEmail.equals(""))
                    Toast.makeText(this, "Please enter subject's email.", Toast.LENGTH_SHORT).show();
                else {
                    startActivity(homeIntent);
                    Toast.makeText(this, "Request sent!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.buttonBackHomeRequest:
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
