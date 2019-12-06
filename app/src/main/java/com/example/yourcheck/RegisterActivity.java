package com.example.yourcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmailRegister, editTextPasswordRegister, editTextVerifyPasswordRegister;
    Button buttonCreateAccountRegister, buttonLoginRegister;
    String email, password, verifyPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmailRegister = findViewById(R.id.editTextEmailRegister);
        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister);
        editTextVerifyPasswordRegister = findViewById(R.id.editTextVerifyPasswordRegister);

        buttonCreateAccountRegister = findViewById(R.id.buttonCreateAccountRegister);
        buttonLoginRegister = findViewById(R.id.buttonLoginRegister);

        buttonCreateAccountRegister.setOnClickListener(this);
        buttonLoginRegister.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {

        email = editTextEmailRegister.getText().toString();
        password = editTextPasswordRegister.getText().toString();
        verifyPassword = editTextVerifyPasswordRegister.getText().toString();

        final Intent loginIntent = new Intent(this,LoginActivity.class);

        switch (view.getId()){

            case R.id.buttonCreateAccountRegister:
                    if (password.equals(verifyPassword)){
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            //Registration success, notify user
                                            Toast.makeText(RegisterActivity.this, "Registration successful! You can now log in with your email and password.", Toast.LENGTH_LONG).show();
                                            startActivity(loginIntent);
                                        } else {
                                            //Registration success, notify user
                                            Toast.makeText(RegisterActivity.this, "Registration failure, please try again.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else
                        Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.buttonLoginRegister:
                startActivity(loginIntent);
                break;
        }
    }
}
