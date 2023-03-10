package com.example.shopaceecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopaceecommerce.model.signup_response_model;
import com.example.shopaceecommerce.utils.apicontroller;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    TextView gotoLoginPage;
    EditText regemail, regmobile, regpassword;
    Button regsubmit;
    ImageView signpasswordIcon;
    ProgressBar progressBar;

    private boolean passwordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regemail = findViewById(R.id.signupEmail);
        regmobile = findViewById(R.id.signupMobile);
        regpassword = findViewById(R.id.signupPassword);
        regsubmit = findViewById(R.id.signupBtn);
        signpasswordIcon = findViewById(R.id.signUpPasswordIcon);
        progressBar = findViewById(R.id.progressBar);

        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,android.R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);

        gotoLoginPage = findViewById(R.id.gotoLoginPage);
        gotoLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        regsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valemail = regemail.getText().toString();
                String valmobile = regmobile.getText().toString();
                String valpassword = regpassword.getText().toString();

                Boolean check = validateinfo(valemail,valmobile,valpassword);
                if (check == true){
                    userregister(valemail, valmobile, valpassword);
                }else {
                    Toast.makeText(SignUpActivity.this, "Sorry, check information again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signpasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordShowing){
                    passwordShowing = false;
                    regpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    signpasswordIcon.setImageResource(R.drawable.eye);
                }else {
                    passwordShowing = true;
                    regpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    signpasswordIcon.setImageResource(R.drawable.hidden);
                }

                regpassword.setSelection(regpassword.length());
            }
        });
    }

    private Boolean validateinfo(String email, String mobile, String password){
        if (email.length() == 0){
            regemail.requestFocus();
            regemail.setError("Field cannot be empty");
            return false;
        }else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            regemail.requestFocus();
            regemail.setError("Enter valid email");
            return false;
        }else if (mobile.length() == 0){
            regmobile.requestFocus();
            regmobile.setError("Field cannot be empty");
            return false;
        }else if (!mobile.matches("^[0-9]{10}$")){
            regmobile.requestFocus();
            regmobile.setError("Correct format: 1234xxxxxx");
            return false;
        }else if (password.length() <= 5){
            regpassword.requestFocus();
            regpassword.setError("Minimum 6 characters required");
            return false;
        }else {
            return true;
        }
    }

    public void userregister(String email, String mobile, String password){
        String fname = "not applicable";
        String lname = "not applicable";
        String gender = "not applicable";
        String address = "not applicable";
        String dob = "not applicable";

        Call<signup_response_model> call = apicontroller.getInstance().getapi().getregister(fname,lname,gender,email,password,mobile,address,dob);
        call.enqueue(new Callback<signup_response_model>() {
            @Override
            public void onResponse(Call<signup_response_model> call, Response<signup_response_model> response) {
                signup_response_model obj = response.body();
                String result = obj.getMessage().trim();

                progressBar.setVisibility(View.VISIBLE);

                if(result.equals("created")){
                    Toast.makeText(SignUpActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                    regemail.setText("");
                    regmobile.setText("");
                    regpassword.setText("");

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                if(result.equals("exist")){
                    Toast.makeText(SignUpActivity.this, "Already registered. Please login", Toast.LENGTH_SHORT).show();

                    regemail.setText("");
                    regmobile.setText("");
                    regpassword.setText("");

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<signup_response_model> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.INVISIBLE);

                regemail.setText("");
                regmobile.setText("");
                regpassword.setText("");
            }
        });
    }
}