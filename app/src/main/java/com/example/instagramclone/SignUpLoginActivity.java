package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity  {

    private EditText edtUserNameSignUp,edtUserNameLogin,edtPasswordSignUp,edtPasswordLogin;
    private Button btnSignUp,btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        edtUserNameSignUp=findViewById(R.id.edtUserNameSignUp);
        edtPasswordSignUp=findViewById(R.id.edtPasswordSignUp);
        btnSignUp=findViewById(R.id.btnSignUp);

        edtUserNameLogin=findViewById(R.id.edtUserNameLogin);
        edtPasswordLogin=findViewById(R.id.edtPasswordLogin);
        btnLogin=findViewById(R.id.btnLogin);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ParseUser appUser=new ParseUser();
                appUser.setUsername(edtUserNameSignUp.getText().toString());
                appUser.setPassword(edtPasswordSignUp.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                        {
                            FancyToast.makeText(SignUpLoginActivity.this,appUser.get("username")+" is signedup successfuly ", FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();

                            Intent intent=new Intent(SignUpLoginActivity.this,WelcomeActivity.class);
                            startActivity(intent);


                        }
                        else
                        {
                            FancyToast.makeText(SignUpLoginActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                        }
                    }
                });

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(edtUserNameLogin.getText().toString(), edtPasswordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e==null)
                        {
                            FancyToast.makeText(SignUpLoginActivity.this,user.get("username")+" is logged in successfuly ", FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();

                            Intent intent=new Intent(SignUpLoginActivity.this,WelcomeActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            FancyToast.makeText(SignUpLoginActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                        }
                    }
                });
            }
        });
    }


}
