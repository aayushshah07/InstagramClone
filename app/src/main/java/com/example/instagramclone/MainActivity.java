package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


        //UI components
        private EditText edtEnterEmail,edtEnterUserName,edtEnterPassword;
        private Button btnSignUp,btnLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");

        edtEnterEmail=findViewById(R.id.edtEnterEmail);
        edtEnterUserName=findViewById(R.id.edtEnterUserName);
        edtEnterPassword=findViewById(R.id.edtEnterPassword);

        edtEnterPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER&& event.getAction()==KeyEvent.ACTION_DOWN)
                {
                    onClick(btnSignUp);
                }
                    return false;
            }
        });

        btnSignUp=findViewById(R.id.btnSignUp);
        btnLogin=findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(MainActivity.this);
        btnLogin.setOnClickListener(MainActivity.this);

        

        if(ParseUser.getCurrentUser()!=null)
        {
            //ParseUser.getCurrentUser().logOut();
            transitionTOSocialMediaActvity();
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSignUp:
                if (edtEnterEmail.getText().toString().equals("") || edtEnterUserName.getText().toString().equals("") || edtEnterPassword.getText().toString().equals("")) {
                    FancyToast.makeText(MainActivity.this, "Email,UserName,Password is required ",
                            Toast.LENGTH_SHORT,
                            FancyToast.INFO, true).show();

                } else {


                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEnterEmail.getText().toString());
                    appUser.setUsername(edtEnterUserName.getText().toString());
                    appUser.setPassword(edtEnterPassword.getText().toString());

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(MainActivity.this, appUser.getUsername() + " is signed up successfully ",
                                        Toast.LENGTH_LONG,
                                        FancyToast.SUCCESS, true).show();
                                transitionTOSocialMediaActvity();
                            } else {
                                FancyToast.makeText(MainActivity.this, "There is something error" + e.getMessage(),
                                        Toast.LENGTH_LONG,
                                        FancyToast.ERROR, true).show();

                            }
                        }

                    });
                }
                    break;
                    case R.id.btnLogin:

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);


                        break;
        }
    }
    public void rootLayoutTapped(View view)
    {
        try
        {
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    private void transitionTOSocialMediaActvity()
    {
        Intent intent=new Intent(MainActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }

}
