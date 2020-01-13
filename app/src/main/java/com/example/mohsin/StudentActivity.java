package com.example.mohsin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText nameET;
    private Button saveBTN;
    private List<String> depertments;
    private ArrayList<Student>students;
    private ReportAdapter adapter;
    private ListView listView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        spinner=findViewById(R.id.spDpartment);
        nameET=findViewById(R.id.txtName);
        saveBTN=findViewById(R.id.btnSave);
        listView=findViewById(R.id.listView);

        depertments=new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("Department");
        getData();

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase = FirebaseDatabase.getInstance().getReference().child("Student");
                String stdId,department,name;

                stdId=myDatabase.push().getKey();
                name=nameET.getText().toString();
                department=spinner.getSelectedItem().toString();

                if(name.equals("") || department.equals("")){
                    Toast.makeText(StudentActivity.this, "Department and Name is required....", Toast.LENGTH_SHORT).show();
                    return;
                }

                else{
                    Student student=new Student(stdId,name,department);
                    myDatabase.child(stdId).setValue(student);
                    Toast.makeText(StudentActivity.this, "Successful...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        show();
    }


    private void getData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Department department=dataSnapshot1.getValue(Department.class);
                    depertments.add(department.getName());
                }

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(StudentActivity.this,R.layout.support_simple_spinner_dropdown_item,depertments);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void show(){
        DatabaseReference myRef = database.getReference("Student");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                students=new ArrayList<>();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Student student=dataSnapshot1.getValue(Student.class);
                    students.add(student);
                }
                adapter=new ReportAdapter(StudentActivity.this,students);
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
