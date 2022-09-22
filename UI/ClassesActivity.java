package com.example.c196mobileapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.c196mobileapplication.Database.DataRepository;
import com.example.c196mobileapplication.Entity.Classes;
import com.example.c196mobileapplication.Entity.Term;
import com.example.c196mobileapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ClassesActivity extends AppCompatActivity {

    TextView termInfo;

    int termId;
    String title;
    String start;
    String end;
    String fullInfo;
    DataRepository repository = new DataRepository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButtonClasses);

        termInfo = findViewById(R.id.classTermTitleText);
        termId = getIntent().getIntExtra("id", -1);
        if(termId != -1){
            Term term = repository.getTermById(termId);
            title = term.getTermTitle();
            start = term.getStartDate();
            end = term.getEndDate();

        }

        fullInfo = title + "  " + start + " | " + end;
        if(title != null){
        termInfo.setText(fullInfo);}
        else{termInfo.setText("Error");}

        RecyclerView recyclerView = findViewById(R.id.recyclerView2);

        List<Classes> classes = repository.getAllClassesByTermId(termId);
        final ClassesAdapter adapter = new ClassesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClasses(classes);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(ClassesActivity.this, TermsActivity.class);
            startActivity(intent);
        });
    }

    public void toAddClassPage(View view) {
        Intent intent = new Intent(ClassesActivity.this, AddEditClasses.class);
        intent.putExtra("termId", termId);

        startActivity(intent);
    }

    public void toReportsPage(View view) {
        Intent intent = new Intent(ClassesActivity.this, AssessmentReport.class);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }
}