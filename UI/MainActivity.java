package com.example.c196mobileapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196mobileapplication.Database.DataRepository;
import com.example.c196mobileapplication.Entity.Login;
import com.example.c196mobileapplication.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static int numAlert;
    private Login loginHolder;
    DataRepository repo;

    EditText userName;
    EditText userPassword;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repo = new DataRepository(getApplication());

        userName = findViewById(R.id.editTextUserName);
        userPassword = findViewById(R.id.editTextPassword);
        message = findViewById(R.id.textView2);
        List<Login> loginList = null;


        if(!repo.getAllLogins().isEmpty()){
        loginList = repo.getAllLogins();}

        if(loginList ==null){
            loginHolder = new Login(1,"User", "Password");
            repo.insert(loginHolder);
        }
        else if(loginList.size() == 1){
            loginHolder = loginList.get(0);
        }
        else if(loginList.size() > 1){
            Toast.makeText(this, "Error: login list too long", Toast.LENGTH_SHORT).show();
        }

        if(loginHolder.getUserName().equals("User") && loginHolder.getPassword().equals("Password")){
            message.setText("Basic \"User\"/\"Password\" is set");
            userName.setText("User");
            userPassword.setText("Password");
        }


    }

    public void onGoClicked(View view) {
        String userNameString = userName.getText().toString();
        String userPasswordString = userPassword.getText().toString();
        boolean nameTrue = false;
        boolean passTrue = false;

        if(userNameString.equals(loginHolder.getUserName())){
            nameTrue = true;
        }
        if(userPasswordString.equals(loginHolder.getPassword())){
            passTrue = true;
        }

        if(nameTrue && passTrue) {

            Intent intent = new Intent(MainActivity.this, TermsActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Username and password do not match", Toast.LENGTH_SHORT).show();
        }

    }

    public void toEditLogin(View view) {
        Intent intent = new Intent(MainActivity.this, LoginEdit.class);
        startActivity(intent);
    }
}