package com.example.c196mobileapplication.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196mobileapplication.Entity.Login;

import java.util.List;

@Dao
public interface LoginDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Login loginInfo);

    @Update
    void update(Login loginInfo);

    @Delete
    void delete(Login loginInfo);

    @Query("SELECT * FROM login ORDER BY loginId ASC")
    List<Login> getAllLogins();
}
