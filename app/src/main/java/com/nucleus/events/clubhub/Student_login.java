package com.nucleus.events.clubhub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Student_login extends AppCompatActivity {
    EditText login_email, login_password;
    Button login_button;
    TextView forget_passoword;
    FirebaseAuth mauth;
    String semail, spassoword;
    ProgressDialog progressDialog;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.btn_login);
        forget_passoword = findViewById(R.id.forget_pasword);

        mauth = FirebaseAuth.getInstance();
      //  String str = mauth.getCurrentUser().getUid();


        progressDialog = new ProgressDialog(this);

        TextView signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student_login.this, Student_register.class));
            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                semail = login_email.getText().toString();
                spassoword = login_password.getText().toString();
                if (TextUtils.isEmpty(semail) || TextUtils.isEmpty(spassoword)) {
                    Toast.makeText(Student_login.this, "Email and password is required", Toast.LENGTH_SHORT).show();
                } else {
                    Login();
                }

            }
        });

        forget_passoword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student_login.this,ForgetPassword.class));

            }
        });

    }






    private void Login() {

        mauth.signInWithEmailAndPassword(semail, spassoword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.setTitle("Logging In");
                progressDialog.setMessage("Please wait ...");
//                progressDialog.show();
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    String string = mauth.getCurrentUser().getUid();
                    if(string.equals("hu7KFsplYYQyKgPLHcQYTjFFl5r1"))
                    {

                        Intent intent = new Intent(Student_login.this, Admin_Dashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intent = new Intent(Student_login.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    String str = task.getException().toString();
                    Toast.makeText(Student_login.this, str, Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}
