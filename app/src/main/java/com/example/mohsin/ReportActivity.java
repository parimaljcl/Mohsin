package com.example.mohsin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button showBTN;
    private RadioButton studentRB,teacherRB;
    private ListView listView;
    private ArrayList<String>depertments;
    private ArrayList<Student>students;
    private ReportAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        showBTN=findViewById(R.id.btnShow);
        spinner=findViewById(R.id.spDpartment);
        studentRB=findViewById(R.id.rdoStudent);
        teacherRB=findViewById(R.id.rdoTeacher);
        listView=findViewById(R.id.listView);

        depertments=new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("Department");
        getData();

        showBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String department=spinner.getSelectedItem().toString();
                if(studentRB.isChecked()){
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
                            adapter=new ReportAdapter(ReportActivity.this,students);
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("TAG", "Failed to read value.", error.toException());
                        }
                    });
                }

                if(teacherRB.isChecked()){
                    Query query = database.getReference("Teacher")
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
                            adapter=new ReportAdapter(ReportActivity.this,students);
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
        });
    }


    private void getData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Department department=dataSnapshot1.getValue(Department.class);
                    //Toast.makeText(ReportActivity.this, "Data:"+department.getName(), Toast.LENGTH_SHORT).show();
                    depertments.add(department.getName());
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(ReportActivity.this,R.layout.support_simple_spinner_dropdown_item,depertments);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
}
