package com.example.skin_save;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private TextView haveAccount;
    private FirebaseAuth auth;
    private EditText name, email, password, confirmpass;
    private Button b;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        haveAccount = findViewById(R.id.haveAccount);
        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        b = findViewById(R.id.signup_button);
        confirmpass = findViewById(R.id.signup_Confirmpassword);

        auth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String em = email.getText().toString();
                String pass = password.getText().toString();
                String cpass = confirmpass.getText().toString();

                if (n.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter the name", Toast.LENGTH_SHORT).show();
                } else if (em.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter the email", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter the password", Toast.LENGTH_SHORT).show();
                } else if (cpass.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter the confirm password", Toast.LENGTH_SHORT).show();
                } else {
                    if (!emailChecker(em)) {
                        Toast.makeText(SignupActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                    } else if (!isPasswordValid(pass)) {
                        Toast.makeText(SignupActivity.this, "Password should be at least 8 characters long and contain at least 1 uppercase letter", Toast.LENGTH_SHORT).show();
                    } else if (!pass.equals(cpass)) {
                        Toast.makeText(SignupActivity.this, "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
                    } else {
                        createUser(em, pass, n);
                        password.getText().clear();
                        confirmpass.getText().clear();
                        name.getText().clear();
                    }
                }
            }
        });
    }

    boolean emailChecker(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-zA-Z0-9]).{8,}$");
        return pattern.matcher(password).matches();
    }

    void createUser(String email, String password, String name) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                User user = new User(name, email);
                if (task.isSuccessful()) {
                    mRef.child("users").push().setValue(user);
                    startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                    Toast.makeText(SignupActivity.this, "User created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
