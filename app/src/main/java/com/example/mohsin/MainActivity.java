package com.example.mohsin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button departmentBTN,studentBTN,teacherBTN,showBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        departmentBTN=findViewById(R.id.btnDepartment);
        studentBTN=findViewById(R.id.btnStudent);
        teacherBTN=findViewById(R.id.btnTeacher);
        showBTN=findViewById(R.id.btnShow);

        departmentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DepartmentActivity.class);
                startActivity(intent);

            }
        });



        showBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ReportActivity.class);
                startActivity(intent);
            }
        });


        studentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,StudentActivity.class);
                startActivity(intent);
            }
        });

        teacherBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,TeacherActivity.class);
                startActivity(intent);
            }
        });

    }
}
