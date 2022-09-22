package com.example.c196mobileapplication.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196mobileapplication.DAO.AssessmentDAO;
import com.example.c196mobileapplication.DAO.ClassDAO;
import com.example.c196mobileapplication.DAO.LoginDAO;
import com.example.c196mobileapplication.DAO.TermDAO;
import com.example.c196mobileapplication.Entity.Assessment;
import com.example.c196mobileapplication.Entity.Classes;
import com.example.c196mobileapplication.Entity.Login;
import com.example.c196mobileapplication.Entity.Term;

@Database(entities = {Term.class, Classes.class, Assessment.class, Login.class}, version=5,
exportSchema = false)
public abstract class AppDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract ClassDAO classDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract LoginDAO loginDAO();

    private static volatile AppDatabaseBuilder INSTANCE;

    static AppDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabaseBuilder.class,
                            "myAppDatabase.db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
    }
