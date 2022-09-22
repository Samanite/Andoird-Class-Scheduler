package com.example.c196mobileapplication.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196mobileapplication.Entity.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY assessId ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE classId = :id")
    List<Assessment> getAllAssessmentsByClassId(int id);

    @Query("SELECT * FROM assessments WHERE assessId = :assessId")
    Assessment getAssessmentById(int assessId);

    @Query("DELETE FROM assessments WHERE classId = :classId")
    void deleteAssociatedAssessments(int classId);

}
