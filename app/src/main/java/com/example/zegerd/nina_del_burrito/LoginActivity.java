package com.example.zegerd.nina_del_burrito;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // FireBae Auth
    private FirebaseAuth mAuth;

    // UI items from layout
    private EditText inputEmail, inputPass;
    private Button btnLogin, btnReset, btnRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get FireBase auth instance
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        // I do not know what this does, but in the tutorial does it.
        setContentView(R.layout.activity_login);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Init variables
        inputEmail = (EditText) findViewById(R.id.txt_user);
        inputPass = (EditText) findViewById(R.id.txt_pass);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_forgot_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        // Tutorial init this again
        mAuth = FirebaseAuth.getInstance();

        // Button Click listeners

        // TODO SignUp/register functionality

        // TODO Reset password functionality

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Init user input text
                String email = inputEmail.getText().toString();
                final String password = inputPass.getText().toString();

                // Input validation
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Show progress bar
                progressBar.setVisibility(View.VISIBLE);

                // Authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user.
                                // If sign in succeeds the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener

                                // Change progress bar visibility
                                progressBar.setVisibility(View.GONE);

                                // Sign in logic
                                if (!task.isSuccessful()) {
                                    // There was an error
                                    if (password.length() < 6 ){
                                        inputPass.setError( getString(R.string.minimum_password) );
                                    } else {
                                        Log.w("tag", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    //finish();
                                }
                            }
                        });
            }
        });
    }
}
