package com.example.c196mobileapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196mobileapplication.Database.DataRepository;
import com.example.c196mobileapplication.Entity.Classes;
import com.example.c196mobileapplication.Entity.Term;
import com.example.c196mobileapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditTerm extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    Button dateStartButton;
    Button dateEndButton;

    private final String myDateFormat = "MM-dd-yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.US);

    private int id;
    private String title;
    private String start;
    private String end;
    DataRepository repository= new DataRepository(getApplication());
    EditText termTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_term);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        initDatePicker();
        initDatePicker2();
        dateStartButton=findViewById(R.id.editTermStartDate);
        dateEndButton = findViewById(R.id.editTermEndDate);
        dateStartButton.setText(todaysDate());
        dateEndButton.setText(todaysDate());

        termTitle = findViewById(R.id.editTextTerm);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");

        if(id != -1){
            termTitle.setText(title);
            dateStartButton.setText(start);
            dateEndButton.setText(end);
        }

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(AddEditTerm.this, TermsActivity.class);
            startActivity(intent);
        });
    }

    private String todaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month +1;

        return makeDateString(day, month, year);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return month + "-" + dayOfMonth + "-" + year;
    }

    private void initDatePicker2() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month +1;
            String date = makeDateString(dayOfMonth,month,year);
            dateEndButton.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog2 = new DatePickerDialog(this, style,dateSetListener,year,month,day);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month +1;
            String date = makeDateString(dayOfMonth, month, year);
            dateStartButton.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style,dateSetListener,year,month,day);
    }


    public void saveTerm(View view) {
        if(TextUtils.isEmpty(termTitle.getText().toString())){
            termTitle.setError("*Required");
        }else {
            Date date1=null;
            Date date2=null;
            try {
                date1 = sdf.parse(dateStartButton.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                date2 = sdf.parse(dateEndButton.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long startTime = date1.getTime();
            Long endTime = date2.getTime();

            if(endTime>startTime) {
                Intent intent = new Intent(AddEditTerm.this, TermsActivity.class);
                Term term;
                if (id == -1) {
                    if (!repository.getAllTerms().isEmpty()) {
                        int newId = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermId() + 1;
                        term = new Term(newId, termTitle.getText().toString(), dateStartButton.getText().toString(),
                                dateEndButton.getText().toString());
                        repository.insert(term);
                    } else {
                        int newId = 1;
                        term = new Term(newId, termTitle.getText().toString(), dateStartButton.getText().toString(),
                                dateEndButton.getText().toString());
                        repository.insert(term);
                    }

                } else {
                    term = new Term(id, termTitle.getText().toString(), dateStartButton.getText().toString(),
                            dateEndButton.getText().toString());
                    repository.update(term);
                }
                startActivity(intent);
            }else{
                Toast.makeText(this, "End date must be later than start date", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openTermStartDate (View view){datePickerDialog.show();}
    public void openTermEndDate (View view){datePickerDialog2.show();}


    public void cancelSaveTerm(View view) {
        Intent intent = new Intent(AddEditTerm.this, TermsActivity.class);
        startActivity(intent);
    }

    public void deleteTerm(View view) {
        Intent intent = new Intent(AddEditTerm.this, TermsActivity.class);
        if(id== -1){
            Toast.makeText(this, "No Term To Delete", Toast.LENGTH_SHORT).show();
        }
        else{
            Term term = repository.getTermById(id);
            List<Classes> associatedClasses = repository.getAllClassesByTermId(id);
            int size = associatedClasses.size();
            if(size >0){
                Toast.makeText(this, "Cannot delete a Term with Courses", Toast.LENGTH_SHORT).show();
            }else{
                repository.delete(term);
                startActivity(intent);
            }
        }

    }


}