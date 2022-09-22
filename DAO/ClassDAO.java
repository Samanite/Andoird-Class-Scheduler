package com.example.c196mobileapplication.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196mobileapplication.Entity.Classes;

import java.util.List;

@Dao
public interface ClassDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Classes classInfo);

    @Update
    void update(Classes classInfo);

    @Delete
    void delete(Classes classInfo);

    @Query("SELECT * FROM classes ORDER BY classId ASC")
    List<Classes> getAllClasses();

    @Query("SELECT * FROM classes WHERE termId = :termId")
    List<Classes> getAllClassesByTermId(int termId);

    @Query("SELECT * FROM classes WHERE classId = :classId")
    Classes getClassById(int classId);

    @Query("DELETE FROM classes WHERE termId = :termId")
    void deleteAssociatedClasses(int termId);
}
