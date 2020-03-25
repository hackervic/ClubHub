package com.nucleus.events.clubhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.steelkiwi.library.SlidingSquareLoaderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Student_register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] CATAGORYSPINNER = {"Select an catagory...", "Student", "Clubleader", "Society"};

    private Spinner spinner;
    private Button register;
    private EditText name, email, password;
    private TextView login;
    private SlidingSquareLoaderView squareView;
    ProgressDialog progressDialog;
    SweetAlertDialog pDialog, pdialog1;

    private FirebaseAuth mauth, mauth1, mauth2;
    private DatabaseReference firebaseDatabase, firebaseDatabase1, firebasedatabase2;
    RelativeLayout relativeLayout;
    String uid, ema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);


        spinner = findViewById(R.id.catagory_spinner);
        register = findViewById(R.id.btn_register);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        relativeLayout = findViewById(R.id.rootlayout);
        mauth = FirebaseAuth.getInstance();
        mauth1 = FirebaseAuth.getInstance();
        mauth2 = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        squareView = findViewById(R.id.view);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pdialog1 = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);

        final List<String> catlist = new ArrayList<>(Arrays.asList(CATAGORYSPINNER));

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> sadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catlist) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };
        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sadapter);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });


    }

    private void CreateAccount() {

        final String em = email.getText().toString();
        final String nm = name.getText().toString();
        final String psw = password.getText().toString();
        final String scat = spinner.getSelectedItem().toString();
        if (TextUtils.isEmpty(nm)) {
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "Name is required", Snackbar.LENGTH_LONG);

            snackbar.show();
        } else if (TextUtils.isEmpty(em)) {
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "Email is required", Snackbar.LENGTH_LONG);

            snackbar.show();
        } else if (TextUtils.isEmpty(psw)) {
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "Password is required", Snackbar.LENGTH_LONG);

            snackbar.show();
        } else if (TextUtils.isEmpty(scat)) {
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "Select your catagory", Snackbar.LENGTH_LONG);

            snackbar.show();
        } else if (scat.equals("Student")) {
            squareView.show();
            mauth1.createUserWithEmailAndPassword(em,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        squareView.hide();
                        pDialog.setTitleText("Conratulations");
                        pDialog.setContentText("Authenticated Success");

                        uid = mauth1.getCurrentUser().getUid();
                        ema = mauth1.getCurrentUser().getEmail();
                        firebaseDatabase1 = FirebaseDatabase.getInstance().getReference().child("students").child(uid);
                        HashMap<String, Object> hashMap= new HashMap<>();
                        hashMap.put("name", nm);
                        hashMap.put("leaderemail", ema);
                        hashMap.put("leaderpassword", psw);
                        hashMap.put("catagory", scat);
                        hashMap.put("contact", "none");
                        hashMap.put("collage", "none");
                        hashMap.put("enrollmentno", "none");
                        hashMap.put("semester", "none");
                        hashMap.put("course", "none");
                        hashMap.put("branch", "none");
                        hashMap.put("year", "none");
                        hashMap.put("imageurl", "none");
                        firebaseDatabase1.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    pDialog.setTitleText("Conratulations");
                                    pDialog.setContentText("Authenticated Success");
                                    pDialog.show();
                                    pDialog.getButton(1).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            pDialog.dismiss();
                                            Intent intent = new Intent(Student_register.this,Student_complete_profile.class);
                                            intent.putExtra("uname",nm);
                                            intent.putExtra("uemail",em);
                                            startActivity(intent);
                                            finish();

                                        }
                                    });
                                } else {
                                    String msg = task.getException().getMessage();
                                    Toast.makeText(Student_register.this, "check internet connection" + msg, Toast.LENGTH_SHORT).show();
                                    squareView.hide();
                                    pdialog1.setTitleText("Oops.....");
                                    pdialog1.setContentText("Something went wrong!");
                                    pdialog1.show();
                                }
                            }
                        });


                    } else {
                        squareView.hide();
                        String s = task.getException().toString();
                        Toast.makeText(Student_register.this, s, Toast.LENGTH_SHORT).show();
                    }
                }
            });
       
       
       
       
       
        } else if (scat.equals("Clubleader")) {
            squareView.show();
            mauth1.createUserWithEmailAndPassword(em, psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        squareView.hide();
                        pDialog.setTitleText("Conratulations");
                        pDialog.setContentText("Authenticated Success");

                        uid = mauth1.getCurrentUser().getUid();
                        ema = mauth1.getCurrentUser().getEmail();
                        firebaseDatabase1 = FirebaseDatabase.getInstance().getReference().child("students").child(uid);
                        HashMap<String, Object> hashMap2 = new HashMap<>();
                        hashMap2.put("name", nm);
                        hashMap2.put("leaderemail", ema);
                        hashMap2.put("leaderpassword", psw);
                        hashMap2.put("catagory", scat);
                        hashMap2.put("contact", "none");
                        hashMap2.put("collage", "none");
                        hashMap2.put("enrollmentno", "none");
                        hashMap2.put("semester", "none");
                        hashMap2.put("course", "none");
                        hashMap2.put("branch", "none");
                        hashMap2.put("year", "none");
                        hashMap2.put("imageurl", "none");
                        firebaseDatabase1.updateChildren(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    pDialog.setTitleText("Conratulations");
                                    pDialog.setContentText("Authenticated Success");
                                    pDialog.show();
                                    pDialog.getButton(1).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            pDialog.dismiss();
                                            Intent intent = new Intent(Student_register.this, Club_register.class);
                                            intent.putExtra("uname",nm);
                                            intent.putExtra("uemail",em);
                                            startActivity(intent);
                                            finish();

                                        }
                                    });
                                } else {
                                    String msg = task.getException().getMessage();
                                    Toast.makeText(Student_register.this, "check internet connection" + msg, Toast.LENGTH_SHORT).show();
                                    squareView.hide();
                                    pdialog1.setTitleText("Oops.....");
                                    pdialog1.setContentText("Something went wrong!");
                                    pdialog1.show();
                                }
                            }
                        });


                    } else {
                        squareView.hide();
                        String s = task.getException().toString();
                        Toast.makeText(Student_register.this, s, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
