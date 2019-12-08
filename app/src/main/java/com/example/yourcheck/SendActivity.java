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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SendActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewUsernameSend;
    EditText editTextRecipientEmailSend;
    Button buttonSendSend, buttonBackHomeSend;
    String email;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        textViewUsernameSend = findViewById(R.id.textViewUsernameSend);
        editTextRecipientEmailSend = findViewById(R.id.editTextRecipientEmailSend);
        buttonSendSend = findViewById(R.id.buttonSendSend);
        buttonBackHomeSend = findViewById(R.id.buttonBackHomeSend);

        buttonSendSend.setOnClickListener(this);
        buttonBackHomeSend.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        textViewUsernameSend.setText("Hello, " + currentUser.getEmail());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonSendSend:
                email = editTextRecipientEmailSend.getText().toString();

                if (email.equals(""))
                    Toast.makeText(this, "Please enter recipient's email", Toast.LENGTH_SHORT).show();
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference("allchecks");
                    myRef.orderByChild("userID").equalTo(currentUser.getEmail().toString()).addChildEventListener(new ChildEventListener() {

                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Check foundCheck = dataSnapshot.getValue(Check.class);
                            String userID = foundCheck.userID;
                            String firstName = foundCheck.firstName;
                            String lastName = foundCheck.lastName;
                            String phoneNumber = foundCheck.phoneNumber;
                            String DOB = foundCheck.DOB;
                            String SSN = foundCheck.SSN;
                            String checkPackage = foundCheck.checkPackage;
                            String nationalStatus = foundCheck.nationalStatus;
                            String countyStatus = foundCheck.countyStatus;
                            String watchListStatus = foundCheck.watchListStatus;
                            String extensiveStatus = foundCheck.extensiveStatus;
                            List<String> sharedWith = foundCheck.sharedWith;

                            sharedWith.add(email);

                            String editKey = dataSnapshot.getKey();
                            Check addShareCheck = new Check(userID, firstName, lastName, phoneNumber, DOB, SSN, checkPackage, nationalStatus, countyStatus, watchListStatus, extensiveStatus, sharedWith);
                            myRef.child(editKey).setValue(addShareCheck);
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
                    Intent homeIntent = new Intent(this, HomeActivity.class);
                    startActivity(homeIntent);
                    Toast.makeText(SendActivity.this, "Verification sent!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.buttonBackHomeSend:
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
