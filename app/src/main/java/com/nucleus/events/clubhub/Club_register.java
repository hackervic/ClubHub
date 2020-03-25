package com.nucleus.events.clubhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.steelkiwi.library.SlidingSquareLoaderView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class Club_register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] Branch = {"Select Branch", "CSE", "EE", "EX", "EC", "CIVIL", "MECHENICAL", "Other"};
    String[] sem = {"Select Semester", "I", "II", "III", "IV", "V", "VI", "VII", "VIII"};
    String[] college = {"Select Collage", "GGITS", "GGCT", "GGCE", "Other"};
    String[] courses = {"Select Course", "B.tech/B.E", "B.com", "B.C.A", "B.Sc", "B.Pharma", "M.Tech/M.E", "M.C.A", "M.B.A", "Others"};
    String[] years = {"Select Year", "First Year", "Secound Year", "Third Year", "Fourth Year"};

    TextView done, change_pic;
    EditText name, email, enrollment, contact;
    Spinner branch, semester, collage, course, year;
    CircleImageView user_pic;
    String get_uname, get_uemail, c_uid, iname, ienromment, icontact, ibranch, isem, icollage, icourse, iyear, download_url;
    FirebaseAuth firebaseAuth;
    Uri imageuri;
    final static int Gallery_pick = 1;
    private ProgressDialog loadingBar;
    DatabaseReference databaseReference;
    private SlidingSquareLoaderView squareView;
   // ProgressDialog progressDialog;
    SweetAlertDialog pDialog, pdialog1;
    Button CreateClub;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_register);


        CreateClub = findViewById(R.id.btn_create_club);
        change_pic = findViewById(R.id.tv_change_user_pic);
        name = findViewById(R.id.student_name);
        email = findViewById(R.id.student_email);
        enrollment = findViewById(R.id.enrollment_no);
        contact = findViewById(R.id.student_contact);
        branch = findViewById(R.id.branch_spinner);
        semester = findViewById(R.id.semester_spinner);
        collage = findViewById(R.id.collage_spinner);
        course = findViewById(R.id.course_spinner);
        year = findViewById(R.id.year_spinner);
        user_pic = findViewById(R.id.user_pic);
        firebaseAuth = FirebaseAuth.getInstance();
        c_uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("students").child(c_uid);
        storageReference = FirebaseStorage.getInstance().getReference().child("students Profile picture");

        loadingBar = new ProgressDialog(this);


        get_uname = getIntent().getExtras().getString("uname");
        get_uemail = getIntent().getExtras().getString("uemail");

        name.setText(get_uname);
        email.setText(get_uemail);

        final List<String> branchlist = new ArrayList<>(Arrays.asList(Branch));
        final List<String> collagelist = new ArrayList<>(Arrays.asList(college));
        final List<String> semlist = new ArrayList<>(Arrays.asList(sem));
        final List<String> courselist = new ArrayList<>(Arrays.asList(courses));
        final List<String> yearlist = new ArrayList<>(Arrays.asList(years));

        branch.setOnItemSelectedListener(this);
        semester.setOnItemSelectedListener(this);
        collage.setOnItemSelectedListener(this);
        course.setOnItemSelectedListener(this);
        year.setOnItemSelectedListener(this);


        ArrayAdapter<String> collage_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, collagelist) {
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
        collage_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collage.setAdapter(collage_adapter);

        ArrayAdapter<String> branch_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branchlist) {
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
        branch_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch.setAdapter(branch_adapter);

        ArrayAdapter<String> sem_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semlist) {
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
        sem_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester.setAdapter(sem_adapter);


        ArrayAdapter<String> course_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courselist) {
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
        course_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(course_adapter);


        ArrayAdapter<String> year_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearlist) {
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
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(year_adapter);


        change_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryintent = new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, Gallery_pick);

            }
        });


        CreateClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveLeaderInfo();
            }
        });


    }

    private void SaveLeaderInfo() {
        iname = name.getText().toString();
        ienromment = enrollment.getText().toString();
        icontact = contact.getText().toString();
        ibranch = branch.getSelectedItem().toString();
        icollage = collage.getSelectedItem().toString();
        isem = semester.getSelectedItem().toString();
        icourse = course.getSelectedItem().toString();
        icourse = course.getSelectedItem().toString();
        iyear = year.getSelectedItem().toString();

        if (TextUtils.isEmpty(iname) || TextUtils.isEmpty(ienromment) || TextUtils.isEmpty(icontact)) {
            Toast.makeText(this, "All the entries are required", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap2.put("clubleader", get_uname);
            hashMap2.put("leaderemail", get_uemail);
            hashMap2.put("contact", icontact);
            hashMap2.put("collage", icollage);
            hashMap2.put("enrollmentno", ienromment);
            hashMap2.put("semester", isem);
            hashMap2.put("course", icourse);
            hashMap2.put("branch", ibranch);
            hashMap2.put("uid", c_uid);

            hashMap2.put("year", iyear);
            hashMap2.put("imageurl", download_url);
            databaseReference.updateChildren(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loadingBar.setTitle("Registering Leader");
                    loadingBar.setMessage("Once You are Registered You Can Make Club ");

                    if (task.isSuccessful()) {
                        loadingBar.dismiss();
                        Intent intent = new Intent(Club_register.this,Student_login.class);
                        startActivity(intent);

                    } else {
                        String msg = task.getException().getMessage();
                        Toast.makeText(Club_register.this, "check internet connection" + msg, Toast.LENGTH_SHORT).show();
                        //squareView.hide();
                        pdialog1.setTitleText("Oops.....");
                        pdialog1.setContentText("Something went wrong!");
                        pdialog1.show();
                    }

                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {

        if (requestCode == Gallery_pick && resultCode == RESULT_OK && data != null) {
            imageuri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .setBorderLineColor(Color.BLACK)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resulturi = result.getUri();
                File file_path = new File(Objects.requireNonNull(resulturi.getPath()));
                Bitmap thumb_bitmap = null;
                try {

                    thumb_bitmap = new Compressor(this)
                            .setMaxHeight(200)
                            .setMaxWidth(200)
                            .setQuality(50)
                            .compressToBitmap(file_path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();
                user_pic.setImageURI(resulturi);

                final StorageReference filepath = storageReference.child(c_uid + ".jpg");
                filepath.putFile(resulturi).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Club_register.this, "profile image stored successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Task<Uri> result = task.getResult().getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    download_url = uri.toString();
                                  /*  databaseReference.child("imageurl").setValue(download_url)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Intent intent = new Intent(Student_complete_profile.this, HomeActivity.class);
                                                        // startActivity(intent);
                                                        Toast.makeText(Student_complete_profile.this, "Image stored to firebase storagr succesfully", Toast.LENGTH_SHORT).show();

                                                        loadingBar.dismiss();
                                                    } else {
                                                        Toast.makeText(Student_complete_profile.this, "Error Occured", Toast.LENGTH_SHORT).show();
                                                        loadingBar.dismiss();
                                                    }

                                                }
                                            });
*/
                                }
                            });


                        }

                    }
                });


            }

        } else {
            Toast.makeText(this, "Crop image doesnt support", Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
