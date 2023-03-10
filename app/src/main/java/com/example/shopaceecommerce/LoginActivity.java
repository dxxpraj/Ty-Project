package com.example.shopaceecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopaceecommerce.model.login_response_model;
import com.example.shopaceecommerce.utils.apicontroller;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView gotoSignupPage;
    EditText loginemail, loginpassword;
    Button loginbtn;
    ImageView loginpasswordIcon;
    ProgressBar progressBar;

    private boolean passwordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginemail = findViewById(R.id.loginEmail);
        loginpassword = findViewById(R.id.loginPassword);
        loginbtn = findViewById(R.id.loginBtn);
        loginpasswordIcon = findViewById(R.id.signInPasswordIcon);
        gotoSignupPage = findViewById(R.id.goToSignUpPage);
        progressBar = findViewById(R.id.progressBar);

        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,android.R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);

        verifyuserexistence();

        gotoSignupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processlogin(loginemail.getText().toString(), loginpassword.getText().toString());
            }
        });

        loginpasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordShowing){
                    passwordShowing = false;
                    loginpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    loginpasswordIcon.setImageResource(R.drawable.eye);
                }else {
                    passwordShowing = true;
                    loginpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    loginpasswordIcon.setImageResource(R.drawable.hidden);
                }

                loginpassword.setSelection(loginpassword.length());
            }
        });
    }

    public void processlogin(String email, String password){
        Call<login_response_model> call = apicontroller.getInstance().getapi().getlogin(email, password);
        call.enqueue(new Callback<login_response_model>() {
            @Override
            public void onResponse(Call<login_response_model> call, Response<login_response_model> response) {
                login_response_model obj = response.body();
                String result = obj.getMessage();

                progressBar.setVisibility(View.VISIBLE);

                if(result.equals("exist")){
                    SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", email);
                    editor.putString("password", password);
                    editor.commit();
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

                if(result.equals("not_exist")){
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    loginemail.setText("");
                    loginpassword.setText("");
                }
            }

            @Override
            public void onFailure(Call<login_response_model> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void verifyuserexistence(){
        SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
        if(sp.contains("username"))
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}