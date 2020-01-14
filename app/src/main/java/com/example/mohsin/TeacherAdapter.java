package com.example.mohsin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {
    private Context context;
    private List<Teacher>teachers;

    public TeacherAdapter(Context context, List<Teacher> teachers) {
        this.context = context;
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.teacher_item,parent,false);
        return new TeacherViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher=teachers.get(position);
        Picasso.with(context).load(teacher.getImageUrl()).fit().centerCrop().into(holder.imageView);
        holder.nameTV.setText(teacher.getName());
        holder.designationTV.setText(teacher.getDesignation());
        holder.emailTV.setText(teacher.getEmail());
        holder.phoneTV.setText(teacher.getPhone());
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nameTV,designationTV,emailTV,phoneTV;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView);
            nameTV=itemView.findViewById(R.id.txtName);
            designationTV=itemView.findViewById(R.id.txtDesignation);
            emailTV=itemView.findViewById(R.id.txtEmail);
            phoneTV=itemView.findViewById(R.id.txtPhone);
        }
    }
}
