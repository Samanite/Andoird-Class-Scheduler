package com.example.c196mobileapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196mobileapplication.Entity.Assessment;
import com.example.c196mobileapplication.R;

import java.util.List;


public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView =itemView.findViewById(R.id.assessmentListTextView);
            itemView.setOnClickListener(v -> {
            });

            itemView.findViewById(R.id.editSelectedAssessment).setOnClickListener(v -> {
                int assessPosition = getAdapterPosition();
                final Assessment current = mAssessments.get(assessPosition);
                Intent intent = new Intent(context, AddEditAssessments.class);
                intent.putExtra("id", current.getAssessId());
                intent.putExtra("title", current.getAssessTitle());
                intent.putExtra("type", current.getAssessType());
                intent.putExtra("date", current.getAssessDate());
                intent.putExtra("classId", current.getClassId());

                context.startActivity(intent);
            });
        }
    }


    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mLayoutInflater;
    public AssessmentAdapter(Context context){
        mLayoutInflater=LayoutInflater.from(context);
        this.context=context;
    }


    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments != null){
            Assessment current = mAssessments.get(position);
            String title = current.getAssessTitle();
            holder.assessmentItemView.setText(title);
        }
        else{
            holder.assessmentItemView.setText("No Assessments Added");
        }
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments=assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mAssessments != null){
            return mAssessments.size();}
        else{
            return 0;
        }
    }
}
