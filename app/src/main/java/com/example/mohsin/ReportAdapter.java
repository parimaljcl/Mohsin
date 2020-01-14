package com.example.mohsin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;


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
        TextView semister = convertView.findViewById(R.id.txtSemister);
        TextView name = convertView.findViewById(R.id.txtName);
        TextView phone = convertView.findViewById(R.id.txtPhone);

        semister.setText(students.get(position).getSemister()+"("+students.get(position).getSection()+")");
        name.setText(students.get(position).getName());
        phone.setText(students.get(position).getPhone());
        return convertView;
    }
}
