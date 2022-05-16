package com.example.jmessagingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jmessagingapp.data.repository.UserRepository;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtName, txtEmail, txtPassword, txtConfirmPassword;
    private Button btnRegister;
    private RadioGroup radioGender;

    private void init(){
        txtName = findViewById(R.id.txt_name);
        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
        txtConfirmPassword = findViewById(R.id.txt_confirm_password);
        radioGender = findViewById(R.id.radio_gender);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();
            }
        });
    }

    private void validateInput(){
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();
        int genderId = radioGender.getCheckedRadioButtonId();

        Snackbar snackbar = Snackbar.make(findViewById(R.id.register_view), "", Snackbar.LENGTH_SHORT);
        String message = "";

        if(name.isEmpty()){
            message = "Name must be filled!";
        }else if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            message = "Email must be filled with correct email format!";
        }else if(password.isEmpty() || confirmPassword.isEmpty()){
            message = "Password and Confirm Password must be Filled!";
        }else if(!password.equals(confirmPassword)){
            message = "Password and Confirm Password must be the same!";
        }else if(genderId == -1){
            message = "Gender must be chosen!";
        }else{
            String gender = ((RadioButton)findViewById(genderId)).getText().toString();
            UserRepository.insertUser(name, email, password, gender);
            message = "Register Success!";
            snackbar.setAction("Login", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                }
            });
        }
        snackbar.setText(message);
        snackbar.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }
}