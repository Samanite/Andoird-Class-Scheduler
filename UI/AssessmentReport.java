package com.example.c196mobileapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196mobileapplication.Database.DataRepository;
import com.example.c196mobileapplication.Entity.Assessment;
import com.example.c196mobileapplication.Entity.Classes;
import com.example.c196mobileapplication.Entity.Term;
import com.example.c196mobileapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AssessmentReport extends AppCompatActivity {

    TextView info;
    int termId;
    DataRepository repo;
    Date todaysDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_report);

        repo = new DataRepository(getApplication());

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButtonAssessmentReport);
        /**
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(AssessmentReport.this, ClassesActivity.class);
            intent.putExtra("id", termId);
            startActivity(intent);
        }); **/

        todaysDateTime = Calendar.getInstance().getTime();

        info = findViewById(R.id.assessmentTextViewReport);
        termId = getIntent().getIntExtra("termId", -1);
        int assessCount = 0;

        if(termId != -1){
            String report = "\n      Assessment Report for Term: " + termId + "     " + todaysDateTime + "\n\n";
            List<Assessment> allTermAssess = new ArrayList<Assessment>();
            List<Classes> classList = repo.getAllClassesByTermId(termId);

            for(int i=0; i<classList.size(); i++){
                int classId = classList.get(i).getClassId();
                List<Assessment> tempList = repo.getAllAssessmentsByClassId(classId);
                for(int x = 0; x < tempList.size(); x++){
                    allTermAssess.add(tempList.get(x));
                    assessCount++;
                }
            }

            for(int i =0; i<allTermAssess.size(); i++){
                Assessment temp = allTermAssess.get(i);
                String tempString = temp.toString();
                report = report + tempString;
            }

            if(assessCount == 0){
                String tempString = "\n      No assessments added this term \n";
                report = report + tempString;
            }

            info.setText(report);

        }

    }

    public void backToClassesActivityFromReport(View view){
        Intent intent = new Intent(AssessmentReport.this, ClassesActivity.class);
        intent.putExtra("id", termId);
        startActivity(intent);
    }

}
