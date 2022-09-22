package com.example.c196mobileapplication.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "classes")
public class Classes {
    @PrimaryKey(autoGenerate = true)
    private int classId;

    private String classTitle;
    private String classStartDate;
    private String classEndDate;
    private String classStatus;
    private String classInstructor;
    private String instructorPhone;
    private String instructorEmail;
    private String classNote;
    private int termId;



    public Classes(int classId, String classTitle, String classStartDate,
                   String classEndDate, String classStatus, String classInstructor,
                   String instructorPhone, String instructorEmail,
                   String classNote, int termId) {
        this.classId = classId;
        this.classTitle = classTitle;
        this.classStartDate = classStartDate;
        this.classEndDate = classEndDate;
        this.classStatus = classStatus;
        this.classInstructor = classInstructor;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.classNote = classNote;
        this.termId = termId;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getClassStartDate() {
        return classStartDate;
    }

    public void setClassStartDate(String classStartDate) {
        this.classStartDate = classStartDate;
    }

    public String getClassEndDate() {
        return classEndDate;
    }

    public void setClassEndDate(String classEndDate) {
        this.classEndDate = classEndDate;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public String getClassInstructor() {
        return classInstructor;
    }

    public void setClassInstructor(String classInstructor) {
        this.classInstructor = classInstructor;
    }

    public String getClassNote() {
        return classNote;
    }

    public void setClassNote(String classNote) {
        this.classNote = classNote;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }


}
