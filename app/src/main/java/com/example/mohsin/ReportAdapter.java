package com.example.mohsin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ReportAdapter extends ArrayAdapter<Student> {
    private Context context;
    private ArrayList<Student>students;

    public ReportAdapter(@NonNull Context context, ArrayList<Student>students) {
        super(context, R.layout.row_item,students);
        this.context=context;
        this.students=students;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_item, parent, false);
        TextView department = convertView.findViewById(R.id.txtDepartment);
        TextView name = convertView.findViewById(R.id.txtName);

        department.setText(students.get(position).getDepartment());
        name.setText(students.get(position).getName());
        return convertView;
    }
}
