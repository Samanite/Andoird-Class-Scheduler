package com.example.c196mobileapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196mobileapplication.Database.DataRepository;
import com.example.c196mobileapplication.Entity.Assessment;
import com.example.c196mobileapplication.Entity.Classes;
import com.example.c196mobileapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AssessmentsActivity extends AppCompatActivity {

    TextView classInfo;

    DataRepository repo = new DataRepository(getApplication());
    int classId;
    String title = null;
    String instructorInfo;
    String startDate;
    String endDate;
    String status;
    String note;
    int termId;
    String titleText;
    private List<Assessment> assessments;
    AssessmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButtonAssess);

        classInfo = findViewById(R.id.assessmentClassTitleText);
        classId = getIntent().getIntExtra("id", -1);
        if(classId != -1) {
            Classes classHolder = repo.getClassById(classId);
            title = classHolder.getClassTitle();
            instructorInfo = classHolder.getClassInstructor();
            startDate = classHolder.getClassStartDate();
            endDate = classHolder.getClassEndDate();
            status = classHolder.getClassStatus();
            note = classHolder.getClassNote();
            termId = classHolder.getTermId();
        }

        if(title !=null){
        titleText = title + " Assessments";}
        else{titleText = "Error";}
        classInfo.setText(titleText);


        RecyclerView recyclerView = findViewById(R.id.recyclerView3);
        assessments = repo.getAllAssessmentsByClassId(classId);
        adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(AssessmentsActivity.this, ClassesActivity.class);
            intent.putExtra("id", termId);
            startActivity(intent);
        });
    }

    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu_option, menu);
        MenuItem searchItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return true;
    }


    public void filter(String text){
        List<Assessment> filteredAssessments = new ArrayList<Assessment>();
        for(Assessment item: assessments){
            if(item.getAssessTitle().toLowerCase().contains(text.toLowerCase())){
                filteredAssessments.add(item);
            }
        }
        if(filteredAssessments.isEmpty()){
            Toast.makeText(this, "Nothing found...", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.setAssessments(filteredAssessments);
        }
    }

    public void toAddAssessmentPage(View view) {
        Intent intent = new Intent(AssessmentsActivity.this, AddEditAssessments.class);
        intent.putExtra("classId", classId);
        startActivity(intent);
    }

}