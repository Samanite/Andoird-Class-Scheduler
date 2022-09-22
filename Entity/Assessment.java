package com.example.c196mobileapplication.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessId;

    private String assessTitle;
    private String assessType;
    private String assessDate;
    private int classId;

    public Assessment(int assessId, String assessTitle, String assessType,
                      String assessDate, int classId) {
        this.assessId = assessId;
        this.assessTitle = assessTitle;
        this.assessType = assessType;
        this.assessDate = assessDate;
        this.classId = classId;
    }

    public int getAssessId() {
        return assessId;
    }

    public void setAssessId(int assessId) {
        this.assessId = assessId;
    }

    public String getAssessTitle() {
        return assessTitle;
    }

    public void setAssessTitle(String assessTitle) {
        this.assessTitle = assessTitle;
    }

    public String getAssessType() {
        return assessType;
    }

    public void setAssessType(String assessType) {
        this.assessType = assessType;
    }

    public String getAssessDate() {
        return assessDate;
    }

    public void setAssessDate(String assessDate) {
        this.assessDate = assessDate;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Assessment Id= " + assessId +
                ", Title= '" + assessTitle + '\'' +
                ", Type= '" + assessType + '\'' +
                ", Date= '" + assessDate + '\'' +
                ", class Id=" + classId +
                " \n \n";
    }
}
