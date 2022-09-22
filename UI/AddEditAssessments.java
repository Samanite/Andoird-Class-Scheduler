package com.example.c196mobileapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c196mobileapplication.Database.DataRepository;
import com.example.c196mobileapplication.Entity.Assessment;
import com.example.c196mobileapplication.Entity.Classes;
import com.example.c196mobileapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditAssessments extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DataRepository repository = new DataRepository(getApplication());
    private DatePickerDialog datePickerDialog;
    private Button dateEndButton;
    private String testType;
    private int id;
    private String title;
    private String testDate;
    private String type;
    private int classId;
    Date classStartDate;
    Date classEndDate;

    private String myDateFormat = "MM-dd-yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.US);
    Spinner spinner;
    EditText editTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessments);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButtonEditAssess);

        initDatePicker();
        dateEndButton = findViewById(R.id.editAssessmentEndDate);
        dateEndButton.setText(todaysDate());

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessment_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        editTitle = findViewById(R.id.editTextAssessment);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        testDate = getIntent().getStringExtra("date");
        classId = getIntent().getIntExtra("classId", -1);

        Classes temp = repository.getClassById(classId);
        try {
            classStartDate = sdf.parse(temp.getClassStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            classEndDate = sdf.parse(temp.getClassEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(id != -1){
            editTitle.setText(title);
            dateEndButton.setText(testDate);
            int index = 0;
            if(type.equalsIgnoreCase("Performance Test")){
                index =1;
            }
            if(type.equalsIgnoreCase("Objective Test")){
                index =2;
            }
            spinner.setSelection(index);
        }

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(AddEditAssessments.this, AssessmentsActivity.class);
            intent.putExtra("id", classId);
            startActivity(intent);
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.assess_menu_options, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (id != -1) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;
                case R.id.notifyAssessment:
                    Date eDate = null;
                    String endClassDate = dateEndButton.getText().toString();
                    try {
                        eDate = sdf.parse(endClassDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Long trigger = eDate.getTime();
                    Intent intent = new Intent(AddEditAssessments.this, MyReceiver.class);
                    intent.putExtra("key", "Assessment \"" + title + "\" is scheduled today");
                    PendingIntent sender = PendingIntent.getBroadcast(AddEditAssessments.this,
                            MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                    Toast.makeText(this, "Assessment Notification Created", Toast.LENGTH_SHORT).show();
                    return true;
            }
        } else {
            Toast.makeText(this, "First Create Assessment", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private String todaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month +1;

        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month +1;
            String date = makeDateString(dayOfMonth, month, year);
            dateEndButton.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style,dateSetListener,year,month,day);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return month + "-" + dayOfMonth + "-" + year;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        testType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void openAssessEndDate (View view){datePickerDialog.show();}

    public void saveAssessment(View view) throws ParseException {
        Intent intent = new Intent(AddEditAssessments.this, AssessmentsActivity.class);
        Assessment assessment;
        int error = 0;
        int dateBoundsError = 0;
        Date assessDate;
        assessDate = sdf.parse((String) dateEndButton.getText());

        Long assessTime = assessDate.getTime();
        Long classStartTime = classStartDate.getTime();
        Long classEndTime = classEndDate.getTime();

        if(assessTime>classEndTime||assessTime<classStartTime){
            error++;
            dateBoundsError++;
        }

        if (TextUtils.isEmpty(editTitle.getText().toString())) {
            editTitle.setError("*Required");
            error++;
        }
        if (spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select An Assessment Type", Toast.LENGTH_SHORT).show();
            error++;
        }
        if (error == 0) {
            if(id == -1) {
                if (!repository.getAllAssessments().isEmpty()) {
                    int newId = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessId() + 1;
                    assessment = new Assessment(newId, editTitle.getText().toString(), spinner.getSelectedItem().toString(),
                            dateEndButton.getText().toString(), classId);
                    repository.insert(assessment);
                } else {
                    int newId = 1;
                    assessment = new Assessment(newId, editTitle.getText().toString(), spinner.getSelectedItem().toString(),
                            dateEndButton.getText().toString(), classId);
                    repository.insert(assessment);
                }

            } else {
                assessment = new Assessment(id, editTitle.getText().toString(), spinner.getSelectedItem().toString(),
                        dateEndButton.getText().toString(), classId);
                repository.update(assessment);
            }
            intent.putExtra("id", classId);
            startActivity(intent);
        }
        else{
            if(dateBoundsError>0){
                Toast.makeText(this,"Assessment not within class dates", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cancelSaveAssessment(View view) {
        Intent intent = new Intent(AddEditAssessments.this, AssessmentsActivity.class);
        intent.putExtra("id", classId);
        startActivity(intent);
    }

    public void deleteAssessment(View view) {
        Intent intent = new Intent(AddEditAssessments.this, AssessmentsActivity.class);
        if(id == -1){
            Toast.makeText(this, "No Assessment To Download", Toast.LENGTH_SHORT).show();
        }
        else{
            Assessment assessment = repository.getAssessById(id);
            repository.delete(assessment);
        }
        intent.putExtra("id", classId);
        startActivity(intent);
    }
}