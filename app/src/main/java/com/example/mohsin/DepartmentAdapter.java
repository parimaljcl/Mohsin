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

public class DepartmentAdapter extends ArrayAdapter<Department> {
    private Context context;
    private ArrayList<Department> departments;

    public DepartmentAdapter(@NonNull Context context, ArrayList<Department> departments) {
        super(context, R.layout.department_item,departments);
        this.context=context;
        this.departments=departments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.department_item, parent, false);
        TextView code = convertView.findViewById(R.id.txtCode);
        TextView name = convertView.findViewById(R.id.txtName);

        code.setText(departments.get(position).getCode());
        name.setText(departments.get(position).getName());
        return convertView;
    }
}
