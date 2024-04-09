package com.example.skin_save;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class forgot extends AppCompatActivity
{
    EditText forgotem;
     Button ok;
     FirebaseAuth auth;
    String fe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        forgotem = findViewById(R.id.forgot_email);
        ok = findViewById(R.id.forgot_ok);
        auth = FirebaseAuth.getInstance();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fe = forgotem.getText().toString();
                if (fe.isEmpty()) {
                    forgotem.setError("Enter email");
                } else {
                    forgetpass();
                }

            }
        });
    }

            private void forgetpass() {
                auth.sendPasswordResetEmail(fe).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgot.this, "Email sent", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(forgot.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(forgot.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(forgot.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }