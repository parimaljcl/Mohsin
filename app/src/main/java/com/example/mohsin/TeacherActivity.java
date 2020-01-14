package com.example.mohsin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Spinner spinner;
    private EditText nameET, designationET, emailET, phoneET;
    private Button saveBTN,browseBTN;
    private ImageView imageView;
    private List<String> depertments;
    private ArrayList<Student> students;
    private ReportAdapter adapter;
    private ListView listView;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myDatabase;
    private StorageReference storage;


    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        spinner = findViewById(R.id.spDpartment);
        nameET = findViewById(R.id.txtName);
        designationET = findViewById(R.id.txtDesignation);
        emailET = findViewById(R.id.txtEmail);
        phoneET = findViewById(R.id.txtPhone);
        imageView = findViewById(R.id.imageView);
        saveBTN = findViewById(R.id.btnSave);
        browseBTN=findViewById(R.id.btnBrowse);
        listView = findViewById(R.id.listView);

        depertments = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Department");

        storage = FirebaseStorage.getInstance().getReference("images");
        myDatabase = FirebaseDatabase.getInstance().getReference().child("Teacher");
        getData();
       browseBTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               browseImage();
           }
       });

       saveBTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               saveData();
           }
       });

    }

    private void getData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Department department = dataSnapshot1.getValue(Department.class);
                    depertments.add(department.getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TeacherActivity.this, R.layout.support_simple_spinner_dropdown_item, depertments);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void browseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void saveData() {
        if (imageUri != null) {
            final StorageReference reference = storage.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            reference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                }
                            }, 500);

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Toast.makeText(TeacherActivity.this, "Successful...", Toast.LENGTH_SHORT).show();

                                    String teachetId, department, name, designation, email, phone, imageUrl;
                                    teachetId = myDatabase.push().getKey();
                                    name = nameET.getText().toString();
                                    department = spinner.getSelectedItem().toString();
                                    designation = designationET.getText().toString();
                                    email = emailET.getText().toString();
                                    phone = phoneET.getText().toString();
                                    imageUrl = uri.toString();

                                    Teacher teacher = new Teacher(teachetId, name, department, designation, email, phone, imageUrl);
                                    myDatabase.child(teachetId).setValue(teacher);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TeacherActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(TeacherActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            Picasso.with(this).load(imageUri).into(imageView);

        }
    }



    private void show() {
        DatabaseReference myRef = database.getReference("Teacher");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                students = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Student student = dataSnapshot1.getValue(Student.class);
                    students.add(student);
                }
                adapter = new ReportAdapter(TeacherActivity.this, students);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
}
