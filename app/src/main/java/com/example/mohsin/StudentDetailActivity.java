package com.example.mohsin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDetailActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Student>students;
    private ReportAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        listView=findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("Department");

        Intent intent=getIntent();
        String department=intent.getStringExtra("department");
        Query query = database.getReference("Student")
                .orderByChild("department")
                .equalTo(department);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                students=new ArrayList<>();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Student student=dataSnapshot1.getValue(Student.class);
                    students.add(student);
                }
                adapter=new ReportAdapter(StudentDetailActivity.this,students);
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
