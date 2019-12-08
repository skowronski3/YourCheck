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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmailLogin, editTextPasswordLogin;
    Button buttonLoginLogin, buttonRegisterLogin;
    String email, password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = findViewById(R.id.editTextPasswordLogin);
        buttonLoginLogin = findViewById(R.id.buttonLoginLogin);
        buttonRegisterLogin = findViewById(R.id.buttonRegisterLogin);

        buttonLoginLogin.setOnClickListener(this);
        buttonRegisterLogin.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {

        email = editTextEmailLogin.getText().toString();
        password = editTextPasswordLogin.getText().toString();

        final Intent homeIntent = new Intent(this, HomeActivity.class);
        final Intent registerIntent = new Intent(this,RegisterActivity.class);

        switch (view.getId()) {

            case R.id.buttonLoginLogin:
                if (email.equals("") | password.equals(""))
                    Toast.makeText(this, "Enter credentials.", Toast.LENGTH_SHORT).show();
                else
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //Sign in success, show success message and navigate to the Portal screen
                                        Toast.makeText(LoginActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
                                        startActivity(homeIntent);
                                    } else {
                                        //If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "Log in failed, please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                break;

            case R.id.buttonRegisterLogin:
                startActivity(registerIntent);
                break;
        }
    }
}
