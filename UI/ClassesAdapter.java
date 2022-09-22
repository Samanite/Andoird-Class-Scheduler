package com.example.c196mobileapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196mobileapplication.Entity.Classes;
import com.example.c196mobileapplication.R;

import java.util.List;

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ClassesViewHolder> {

    class ClassesViewHolder extends RecyclerView.ViewHolder{

        private final TextView classItemView;

        private ClassesViewHolder(View itemView){
            super(itemView);

            classItemView =itemView.findViewById(R.id.classListTextView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final Classes current = mClasses.get(position);
                Intent intent = new Intent(context, AssessmentsActivity.class);
                intent.putExtra("id", current.getClassId());
                //intent.putExtra("termId", current.getTermId());

                context.startActivity(intent);
            });

            itemView.findViewById(R.id.viewEditButton2).setOnClickListener(v -> {
                int classPosition = getAdapterPosition();
                final Classes current = mClasses.get(classPosition);
                Intent intent = new Intent(context, AddEditClasses.class);
                intent.putExtra("id", current.getClassId());
                intent.putExtra("title", current.getClassTitle());
                intent.putExtra("instructorInfo", current.getClassInstructor());
                intent.putExtra("instructorPhone", current.getInstructorPhone());
                intent.putExtra("instructorEmail", current.getInstructorEmail());
                intent.putExtra("startDate", current.getClassStartDate());
                intent.putExtra("endDate", current.getClassEndDate());
                intent.putExtra("status", current.getClassStatus());
                intent.putExtra("note", current.getClassNote());
                intent.putExtra("termId", current.getTermId());

                context.startActivity(intent);
            });
        }

    }
    private List<Classes> mClasses;
    private final Context context;
    private final LayoutInflater mLayoutInflater;
    public ClassesAdapter(Context context){
        mLayoutInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public ClassesAdapter.ClassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mLayoutInflater.inflate(R.layout.class_list_item, parent, false);
            return new ClassesAdapter.ClassesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassesAdapter.ClassesViewHolder holder, int position) {
            if(mClasses != null){
                Classes current = mClasses.get(position);
                String title = current.getClassTitle();
                holder.classItemView.setText(title);
            }
            else{
                holder.classItemView.setText("No Classes Added");
            }
    }
        public void setClasses(List<Classes> classes){
            mClasses=classes;
            notifyDataSetChanged();}

    @Override
    public int getItemCount() {
                    if (mClasses != null) {
                        return mClasses.size();
                    } else {
                        return 0;
                    }
                }
    }

