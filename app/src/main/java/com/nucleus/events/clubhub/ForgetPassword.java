package com.nucleus.events.clubhub;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private Button sed_link;
    private EditText forget_text;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        firebaseAuth = FirebaseAuth.getInstance();

        sed_link = findViewById(R.id.send_link_btn);
        forget_text = findViewById(R.id.forget_email);

        sed_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String st = forget_text.getText().toString().toLowerCase();
                if(TextUtils.isEmpty(st))
                {
                    Toast.makeText(ForgetPassword.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(st).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ForgetPassword.this, "Email sent to"+" "+st, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ForgetPassword.this, "Try Again Some error occured", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


                }
            }
        });
    }
}
