package com.example.c196mobileapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196mobileapplication.Database.DataRepository;
import com.example.c196mobileapplication.Entity.Term;
import com.example.c196mobileapplication.R;

import java.util.List;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        DataRepository repo = new DataRepository(getApplication());
        List<Term> terms = repo.getAllTerms();
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);

    }


    public void toAddTermPage(View view) {
        Intent intent = new Intent(TermsActivity.this, AddEditTerm.class);
        startActivity(intent);
    }

}