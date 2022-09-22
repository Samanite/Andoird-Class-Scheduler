package com.example.c196mobileapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196mobileapplication.Entity.Term;
import com.example.c196mobileapplication.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItemView;

        private TermViewHolder(View itemView){
            super(itemView);
            termItemView =itemView.findViewById(R.id.recycleListTextView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final Term current = mTerms.get(position);
                Intent intent = new Intent(context, ClassesActivity.class);
                intent.putExtra("id", current.getTermId());
                intent.putExtra("title", current.getTermTitle());
                intent.putExtra("startDate", current.getStartDate());
                intent.putExtra("endDate", current.getEndDate());

                context.startActivity(intent);
            });

            itemView.findViewById(R.id.viewEditButton).setOnClickListener(v -> {
                int termPosition = getAdapterPosition();
                final Term current = mTerms.get(termPosition);
                Intent intent = new Intent(context, AddEditTerm.class);
                intent.putExtra("id", current.getTermId());
                intent.putExtra("title", current.getTermTitle());
                intent.putExtra("startDate", current.getStartDate());
                intent.putExtra("endDate", current.getEndDate());

                context.startActivity(intent);
            });
        }
    }
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mLayoutInflater;
    public TermAdapter(Context context){
        mLayoutInflater=LayoutInflater.from(context);
        this.context=context;
    }


    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms != null){
            Term current = mTerms.get(position);
            String title = current.getTermTitle();
            holder.termItemView.setText(title);

        }
        else{
            holder.termItemView.setText("No Terms Added");
        }
    }

    public void setTerms(List<Term> terms){
        mTerms=terms;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mTerms != null){
        return mTerms.size();}
        else{
            return 0;
        }
    }
}
