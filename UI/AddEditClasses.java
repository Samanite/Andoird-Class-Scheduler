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
import com.example.c196mobileapplication.Entity.Term;
import com.example.c196mobileapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditClasses extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    Button dateStartButton;
    Button dateEndButton;

    Spinner spinner;
    private String classStatus;

    int termId;
    String termTitle;
    String termStart;
    String termEnd;
    Date termStartDate;
    Date termEndDate;

    DataRepository repository = new DataRepository(getApplication());

    private int id;
    private String title;
    private String instructorInfo;
    private String instructorPhone;
    private String instructorEmail;
    private String startDate;
    private String endDate;
    private String status;
    private String note;

    private final String myDateFormat = "MM-dd-yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.US);

    EditText editTitle;
    EditText editInfo;
    EditText editPhone;
    EditText editEmail;
    EditText editNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_classes);

        spinner = findViewById(R.id.spinnerCourseStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.course_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButtonEditClasses);

        initDatePicker();
        initDatePicker2();
        dateStartButton=findViewById(R.id.editClassStartDate);
        dateEndButton = findViewById(R.id.editClassEndDate);
        dateStartButton.setText(todaysDate());
        dateEndButton.setText(todaysDate());

        editTitle = findViewById(R.id.editTextClassTitle);
        editInfo = findViewById(R.id.editTextInstructorName);
        editPhone = findViewById(R.id.editTextInstructorPhone);
        editEmail = findViewById(R.id.editTextInstructorEmail);
        editNote = findViewById(R.id.editTextClassNote);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        instructorInfo = getIntent().getStringExtra("instructorInfo");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        status = getIntent().getStringExtra("status");
        note = getIntent().getStringExtra("note");

        if(id != -1){

            int index = 0;
            if(status.equalsIgnoreCase("In progress")){
                index =1;
            }
            if(status.equalsIgnoreCase("Completed")){
                index =2;
            }
            if(status.equalsIgnoreCase("Dropped")){
                index =3;
            }
            if(status.equalsIgnoreCase("Plan to take")){
                index =4;
            }
            spinner.setSelection(index);
        }

        termId = getIntent().getIntExtra("termId", -1);
        if(termId !=-1){
            Term term = repository.getTermById(termId);
            termTitle = term.getTermTitle();
            termStart = term.getStartDate();
            termEnd = term.getEndDate();
            try {
                termStartDate = sdf.parse(term.getStartDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                termEndDate = sdf.parse(term.getEndDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if(id != -1){
            editTitle.setText(title);
            dateStartButton.setText(startDate);
            dateEndButton.setText(endDate);
            editInfo.setText(instructorInfo);
            editPhone.setText(instructorPhone);
            editEmail.setText(instructorEmail);
            editNote.setText(note);
        }
        if(id == -1){
            Term temp = repository.getTermById(termId);
            dateStartButton.setText(temp.getStartDate());
            dateEndButton.setText(temp.getEndDate());
        }

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(AddEditClasses.this, ClassesActivity.class);
            intent.putExtra("id", termId);
            intent.putExtra("title", termTitle);
            intent.putExtra("startDate", termStart);
            intent.putExtra("endDate", termEnd);
            startActivity(intent);
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       classStatus = parent.getItemAtPosition(position).toString();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.class_menu_options, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(id != -1) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;
                case R.id.shareClassNote:
                    if(!editNote.getText().toString().isEmpty()){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
                    sendIntent.putExtra(Intent.EXTRA_TITLE, title + " class note");
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);}
                    return true;
                case R.id.notifyClass:
                    Date sDate = null;
                    String startClassDate = dateStartButton.getText().toString();
                    try {
                        sDate = sdf.parse(startClassDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Long trigger = sDate.getTime();
                    Intent intent = new Intent(AddEditClasses.this, MyReceiver.class);
                    intent.putExtra("key", "Course \"" + id + " " + title + "\" starts today");
                    PendingIntent sender = PendingIntent.getBroadcast(AddEditClasses.this,
                            MainActivity.numAlert++,intent,PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                    Toast.makeText(this,"Course Start Notification Created", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.notifyClassEnd:
                    Date eDate = null;
                    String endClassDate = dateEndButton.getText().toString();
                    try {
                        eDate = sdf.parse(endClassDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Long trigger2 = eDate.getTime();
                    Intent intent2 = new Intent(AddEditClasses.this, MyReceiver.class);
                    intent2.putExtra("key", "Course \"" + id + " " + title + "\" ends today");
                    PendingIntent sender2 = PendingIntent.getBroadcast(AddEditClasses.this,
                            MainActivity.numAlert++,intent2,PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                    Toast.makeText(this,"Course End Notification Created", Toast.LENGTH_SHORT).show();
                    return true;
            }
        }else{
            Toast.makeText(this, "First Create Course", Toast.LENGTH_SHORT).show();
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

    private String makeDateString(int dayOfMonth, int month, int year) {
        return month + "-" + dayOfMonth + "-" + year;
    }

    public void saveClass (View view){
        int error =0;
        int dateError =0;
        int dateBoundsError =0;
        if(TextUtils.isEmpty(editTitle.getText().toString())){
            editTitle.setError("*Required");
            error++;
        }
        if(TextUtils.isEmpty(editInfo.getText().toString())){
            editInfo.setError("*Required");
            error++;
        }
        if(TextUtils.isEmpty(editPhone.getText().toString())){
            editPhone.setError("*Required");
            error++;
        }
        if(TextUtils.isEmpty(editEmail.getText().toString())){
            editEmail.setError("*Required");
            error++;
        }
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

        if(startTime>=endTime){
            dateError++;
            error++;
        }
        Long termStartTime = termStartDate.getTime();
        Long termEndTime = termEndDate.getTime();
        if(!(termStartTime<=startTime && termEndTime >=endTime)){
            error++;
            dateBoundsError++;
        }

        if (spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select A Class Status", Toast.LENGTH_SHORT).show();
            error++;
        }

        if(error ==0){
        Intent intent = new Intent(AddEditClasses.this, ClassesActivity.class);
        Classes classes;
        if(id == -1){
            if(!repository.getAllClasses().isEmpty()){
            int newId = repository.getAllClasses().get(repository.getAllClasses().size() -1).getClassId() +1;
            classes = new Classes(newId, editTitle.getText().toString(), dateStartButton.getText().toString(),
                    dateEndButton.getText().toString(),spinner.getSelectedItem().toString(), editInfo.getText().toString(),
                    editPhone.getText().toString(), editEmail.getText().toString(),
                    editNote.getText().toString(),termId);
            repository.insert(classes);}
            else{
                int newId = 1;
                classes = new Classes(newId, editTitle.getText().toString(), dateStartButton.getText().toString(),
                        dateEndButton.getText().toString(),spinner.getSelectedItem().toString(), editInfo.getText().toString(),
                        editPhone.getText().toString(), editEmail.getText().toString(),
                        editNote.getText().toString(),termId);
                repository.insert(classes);
                }
        }
        else{
            classes = new Classes(id, editTitle.getText().toString(), dateStartButton.getText().toString(),
                    dateEndButton.getText().toString(),spinner.getSelectedItem().toString(), editInfo.getText().toString(),
                    editPhone.getText().toString(), editEmail.getText().toString(),
                    editNote.getText().toString(),termId);
            repository.update(classes);
        }
        intent.putExtra("id", termId);
        intent.putExtra("title", termTitle);
        intent.putExtra("startDate", termStart);
        intent.putExtra("endDate", termEnd);
        startActivity(intent);}
        else{
            if(dateError>0) {
                Toast.makeText(this, "End date must be later than start date", Toast.LENGTH_SHORT).show();
            }
            else if(dateBoundsError>0){
                Toast.makeText(this,"Dates are not within term", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cancelSaveClass(View view){
        Intent intent = new Intent(AddEditClasses.this, ClassesActivity.class);
        intent.putExtra("id", termId);
        intent.putExtra("title", termTitle);
        intent.putExtra("startDate", termStart);
        intent.putExtra("endDate", termEnd);
        startActivity(intent);
    }

    public void deleteClass(View view){
        Intent intent = new Intent(AddEditClasses.this, ClassesActivity.class);
        if(id == -1){
            Toast.makeText(this, "No Class To Delete", Toast.LENGTH_SHORT).show();
        }
        else{

            Classes classes = repository.getClassById(id);
            List<Assessment> associatedAssessments = repository.getAllAssessmentsByClassId(id);
            int size = associatedAssessments.size();

            if(size>0){
                Toast.makeText(this, "Cannot Delete Course With Assessments", Toast.LENGTH_SHORT).show();
            } else{
            repository.delete(classes);
                intent.putExtra("id", termId);
                intent.putExtra("title", termTitle);
                intent.putExtra("startDate", termStart);
                intent.putExtra("endDate", termEnd);
            startActivity(intent);
            }
        }

    }


    public void openStartDate (View view){datePickerDialog.show();}
    public void openEndDate (View view){datePickerDialog2.show();}

}