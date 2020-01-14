package com.example.mohsin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class TeacherDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Teacher> teachers;
    private TeacherAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);


        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("Department");

        Intent intent=getIntent();
        String department=intent.getStringExtra("department");
        Query query = database.getReference("Teacher")
                .orderByChild("department")
                .equalTo(department);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teachers=new ArrayList<>();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Teacher teacher=dataSnapshot1.getValue(Teacher.class);
                    teachers.add(teacher);
                }
                adapter=new TeacherAdapter(TeacherDetailActivity.this,teachers);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }
}
