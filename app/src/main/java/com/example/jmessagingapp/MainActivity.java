package com.example.jmessagingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jmessagingapp.data.model.User;
import com.example.jmessagingapp.data.repository.UserRepository;
import com.example.jmessagingapp.interfaces.MainListener;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements MainListener<User> {

    private TextView txtTitle, txtRegister;
    private EditText txtEmail, txtPassword;
    private Button btnLogin;

    private void init(){
        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
        btnLogin = findViewById(R.id.btn_login);
        txtTitle = findViewById(R.id.txt_title);
        txtRegister = findViewById(R.id.txt_register);

        String title = "<font color=#5cffa5>J</font>Messaging";
        txtTitle.setText(Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY));

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();
            }
        });
    }

    private void validateInput(){
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        Snackbar snackbar = Snackbar.make(findViewById(R.id.login_view), "", Snackbar.LENGTH_SHORT);
        String message = "";

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            message = "Email must be filled with correct email format!";
        }else if(password.isEmpty()){
            message = "Password must be Filled!";
        }else{
            UserRepository.login(email, password, this);
            return;
        }
        snackbar.setText(message);
        snackbar.show();
    }

    private void isLoggedIn() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (prefs.contains("id")) {
            UserRepository.getUserById(prefs.getString("id", null), (data, msg) -> {
                UserRepository.CURRENT_USER = data;
                if (data != null) {
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.commit();
                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLoggedIn();
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onFinish(User data, String message) {

        if(data == null){
            Snackbar.make(findViewById(R.id.login_view), message, Snackbar.LENGTH_SHORT).show();
        }else{
//            Snackbar.make(findViewById(R.id.login_view), "login success", Snackbar.LENGTH_SHORT).show();
            UserRepository.CURRENT_USER = data;
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("id", data.getId());
            editor.commit();
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }
}