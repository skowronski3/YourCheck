package com.example.yourcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewUsernameCheck;
    EditText editTextFirstNameCheck, editTextLastNameCheck, editTextPhoneNumberCheck, editTextDOBCheck, editTextSSNCheck;
    Button buttonSubmitCheck, buttonCancelCheck;
    String firstName, lastName, phoneNumber, DOB, SSN, spinnerSelection, checkPackage, nationalStatus, countyStatus, watchListStatus, extensiveStatus;
    List<String> sharedWith;

    Spinner packageSpinner;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        packageSpinner = (Spinner) findViewById(R.id.spinnerPackageCheck);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CheckActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.packages));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        packageSpinner.setAdapter(myAdapter);

        textViewUsernameCheck = findViewById(R.id.textViewUsernameCheck);
        editTextFirstNameCheck = findViewById(R.id.editTextFirstNameCheck);
        editTextLastNameCheck = findViewById(R.id.editTextLastNameCheck);
        editTextPhoneNumberCheck = findViewById(R.id.editTextPhoneNumberCheck);
        editTextDOBCheck = findViewById(R.id.editTextDOBCheck);
        editTextSSNCheck = findViewById(R.id.editTextSSNCheck);
        buttonSubmitCheck = findViewById(R.id.buttonSubmitCheck);
        buttonCancelCheck = findViewById(R.id.buttonCancelCheck);
        sharedWith = new ArrayList<>();

        buttonSubmitCheck.setOnClickListener(this);
        buttonCancelCheck.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        textViewUsernameCheck.setText("Hello, " + currentUser.getEmail());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonSubmitCheck:
                firstName = editTextFirstNameCheck.getText().toString();
                lastName = editTextLastNameCheck.getText().toString();
                phoneNumber = editTextPhoneNumberCheck.getText().toString();
                DOB = editTextDOBCheck.getText().toString();
                SSN = editTextSSNCheck.getText().toString();
                nationalStatus = "UPGRADE";
                countyStatus = "UPGRADE";
                watchListStatus = "UPGRADE";
                extensiveStatus = "UPGRADE";
                sharedWith.add(currentUser.getEmail());


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("allchecks");

                if (firstName.equals("") | lastName.equals("") | phoneNumber.equals("") | phoneNumber.equals("") | DOB.equals("") | SSN.equals(""))
                    Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();

                else {
                    spinnerSelection = packageSpinner.getSelectedItem().toString();

                    if (spinnerSelection.equals("Basic Check ($15)")) {
                        checkPackage = "BASIC";
                        nationalStatus = "FAIL";
                        countyStatus = "PASS";
                    }
                    else if (spinnerSelection.equals("Standard Check ($25)")) {
                        checkPackage = "STANDARD";
                        nationalStatus = "FAIL";
                        countyStatus = "PASS";
                        watchListStatus = "PASS";
                    }
                    else if (spinnerSelection.equals("Advanced Check ($35)")) {
                        checkPackage = "ADVANCED";
                        nationalStatus = "FAIL";
                        countyStatus = "PASS";
                        watchListStatus = "PASS";
                        extensiveStatus = "FAIL";
                    }

                    Check newCheck = new Check(currentUser.getEmail().toString(), firstName, lastName, phoneNumber, DOB, SSN, checkPackage, nationalStatus, countyStatus, watchListStatus, extensiveStatus, sharedWith);
                    myRef.push().setValue(newCheck);
                    Intent resultsIntent = new Intent(this, ResultsActivity.class);
                    startActivity(resultsIntent);
                    Toast.makeText(this, "Check submitted!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.buttonCancelCheck:
                Intent cancelIntent = new Intent(this, HomeActivity.class);
                startActivity(cancelIntent);
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
