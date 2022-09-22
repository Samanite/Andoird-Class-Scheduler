package com.example.c196mobileapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196mobileapplication.Database.DataRepository;
import com.example.c196mobileapplication.Entity.Login;
import com.example.c196mobileapplication.R;

import java.util.List;

public class LoginEdit extends AppCompatActivity {

    private Login loginHolder;
    private List<Login> loginList;
    DataRepository repo = new DataRepository(getApplication());

    EditText userEditName;
    EditText userEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_login);

        userEditName = findViewById(R.id.editUserName);
        userEditPassword = findViewById(R.id.editPassword);

        loginList = repo.getAllLogins();
        if(loginList.size() == 1){
            loginHolder = loginList.get(0);
            userEditName.setText(loginHolder.getUserName());
            userEditPassword.setText(loginHolder.getPassword());
        }
        if(loginList.size()>1){
            Toast.makeText(this, "Error: login list too long", Toast.LENGTH_SHORT).show();
        }

    }

    public void saveLoginInfo(View view){
        int err = 0;

        if(TextUtils.isEmpty(userEditName.getText())){
            userEditName.setError("*Required");
            err++;
        }
        if(TextUtils.isEmpty(userEditPassword.getText())){
            userEditPassword.setError("*Required");
            err++;
        }
        if(err == 0) {
            loginHolder.setUserName(userEditName.getText().toString());
            loginHolder.setPassword(userEditPassword.getText().toString());

            repo.update(loginHolder);

            Intent intent = new Intent(LoginEdit.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Missing Information", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteLoginInfo(View view){
        repo.delete(loginHolder);
        Intent intent = new Intent(LoginEdit.this, MainActivity.class);
        startActivity(intent);
    }

    public void backToMainLogin(View view){
        Intent intent = new Intent(LoginEdit.this, MainActivity.class);
        startActivity(intent);
    }
}
