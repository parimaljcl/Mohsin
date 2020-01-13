package com.example.mohsin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity {
    private Button saveBTN;
    private EditText nameET,codeET;
    private ListView listView;
    private ArrayList<Department>departments;
    private DepartmentAdapter adapter;

    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        saveBTN=findViewById(R.id.btnSave);
        codeET=findViewById(R.id.txtCode);
        nameET=findViewById(R.id.txtName);
        listView=findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance().getReference().child("Department");

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String departmentId,code,name;
                departmentId=database.push().getKey();
                code=codeET.getText().toString();
                name=nameET.getText().toString();

                if(code.equals("")|| name.equals("")){
                    Toast.makeText(DepartmentActivity.this, "Code and Name is required....", Toast.LENGTH_SHORT).show();
                    return;
                }

                else{
                        Department department=new Department(departmentId,code,name);
                        database.child(departmentId).setValue(department);
                    Toast.makeText(DepartmentActivity.this, "Successful...", Toast.LENGTH_SHORT).show();

                }
            }
        });

        show();
    }

    private void show(){
        DatabaseReference myRef = db.getReference("Department");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                departments=new ArrayList<>();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Department department=dataSnapshot1.getValue(Department.class);
                    departments.add(department);
                }
                adapter=new DepartmentAdapter(DepartmentActivity.this,departments);
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
