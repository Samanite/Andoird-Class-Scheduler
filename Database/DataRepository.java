package com.example.c196mobileapplication.Database;

import android.app.Application;

import com.example.c196mobileapplication.DAO.AssessmentDAO;
import com.example.c196mobileapplication.DAO.ClassDAO;
import com.example.c196mobileapplication.DAO.LoginDAO;
import com.example.c196mobileapplication.DAO.TermDAO;
import com.example.c196mobileapplication.Entity.Assessment;
import com.example.c196mobileapplication.Entity.Classes;
import com.example.c196mobileapplication.Entity.Login;
import com.example.c196mobileapplication.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//**Example insert**
//Repository repo = new Repository(getApplication());
//Term term = new Term(add stuff);
//repo.insert(term);

public class DataRepository {
    private final TermDAO mTermDAO;
    private final ClassDAO mClassDao;
    private final AssessmentDAO mAssessmentDAO;
    private final LoginDAO mLoginDAO;

    List<Term> mAllTerms;
    List<Classes> mAllClasses;
    List<Classes> mAllFilteredClasses;
    List<Assessment> mAllAssessments;
    List<Assessment> mAllFilteredAssessments;
    List<Login> mAllLogins;

    Assessment mAssessById;
    Classes mClassById;
    Term mTermById;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(
            NUMBER_OF_THREADS);
    public DataRepository(Application application){
        AppDatabaseBuilder db = AppDatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mClassDao = db.classDAO();
        mAssessmentDAO = db.assessmentDAO();
        mLoginDAO = db.loginDAO();
    }

    public void deleteAssociatedClasses(int termId){
        databaseExecutor.execute(()->{
            mClassDao.deleteAssociatedClasses(termId);
        });
        try{
        Thread.sleep(1000);
    }catch(InterruptedException e){
        e.printStackTrace();
    }
    }

    public void deleteAssociatedAssessments(int classId){
        databaseExecutor.execute(()->{
            mAssessmentDAO.deleteAssociatedAssessments(classId);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }


    public Term getTermById(int termId){
        databaseExecutor.execute(()-> mTermById = mTermDAO.getTermById(termId));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mTermById;
    }

    public Classes getClassById(int classId){
        databaseExecutor.execute(()-> mClassById = mClassDao.getClassById(classId));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mClassById;
    }
    public Assessment getAssessById(int assessId){
        databaseExecutor.execute(()-> mAssessById = mAssessmentDAO.getAssessmentById(assessId));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAssessById;
    }

    public List<Login> getAllLogins(){
        databaseExecutor.execute(()->mAllLogins = mLoginDAO.getAllLogins());
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllLogins;
    }

    public List<Term> getAllTerms(){
        databaseExecutor.execute(()-> mAllTerms = mTermDAO.getAllTerms());
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<Classes> getAllClasses(){
        databaseExecutor.execute(()-> mAllClasses = mClassDao.getAllClasses());
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllClasses;
    }

    public List<Classes> getAllClassesByTermId(int termId){
        databaseExecutor.execute(()-> mAllFilteredClasses = mClassDao.getAllClassesByTermId(termId));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllFilteredClasses;
    }

    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()-> mAllAssessments = mAssessmentDAO.getAllAssessments());
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public List<Assessment> getAllAssessmentsByClassId(int classId){
        databaseExecutor.execute(()-> mAllFilteredAssessments = mAssessmentDAO.getAllAssessmentsByClassId(classId));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllFilteredAssessments;
    }

    public void insert(Login login){
        databaseExecutor.execute(()-> mLoginDAO.insert(login));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void insert(Term term){
        databaseExecutor.execute(()-> mTermDAO.insert(term));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void insert(Classes classInfo){
        databaseExecutor.execute(()-> mClassDao.insert(classInfo));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void insert(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.insert(assessment));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update (Login login){
        databaseExecutor.execute(()->mLoginDAO.update(login));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.update(assessment));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Classes classInfo){
        databaseExecutor.execute(()-> mClassDao.update(classInfo));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Term term){
        databaseExecutor.execute(()-> mTermDAO.update(term));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Login login){
        databaseExecutor.execute(()-> mLoginDAO.delete(login));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Term term){
        databaseExecutor.execute(()-> mTermDAO.delete(term));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Classes classInfo){
        databaseExecutor.execute(()-> mClassDao.delete(classInfo));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.delete(assessment));
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

}
