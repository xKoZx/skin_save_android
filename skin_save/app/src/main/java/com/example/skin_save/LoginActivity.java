package com.example.skin_save;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText loginemail, loginPassword;
    private Button loginButton;
    public TextView fp, haveacc;
    FirebaseAuth ath;

    private DatabaseReference mRef;
    String le, lp;
    String adminEmail = "admin@gmail.com";
    String adminPassword = "12345678";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginemail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        fp = findViewById(R.id.login_forgot);
        ath = FirebaseAuth.getInstance();
        haveacc = findViewById(R.id.haveaccount);
        haveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });
        fp.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(LoginActivity.this, forgot.class)));
            }
        }));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                le = loginemail.getText().toString();
                lp = loginPassword.getText().toString();
                if (!le.isEmpty() && emailChecker(le)) {
                    if (!le.isEmpty()) {
                        if (le.equals(adminEmail) && lp.equals(adminPassword)) {
                            // Redirect to AdminActivity
                            Toast.makeText(LoginActivity.this, "Admin Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                            finish();
                           // loadAdminFragment();
                        } else {
                            // Regular user login
                            LoginUser(le, lp);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Enter a valid password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
//    private void loadAdminFragment() {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(android.R.id.content, new admin());
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }


    private void LoginUser(String le, String lp) {
        ath.signInWithEmailAndPassword(le, lp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Account Not Found", Toast.LENGTH_SHORT).show();
                    loginPassword.setError("Invalid Email/Password");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }



    boolean emailChecker(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
