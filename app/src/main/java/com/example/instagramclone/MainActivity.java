package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
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
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSave,btnGetAllData;
    private EditText edtName,edtKickSpeed,edtPunchSpeed,edtKickPower;
    private TextView txtGetData;
    private String allKickBoxers;
    private Button btnTransition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(MainActivity.this);
        edtKickPower=findViewById(R.id.edtKickPower);
        edtKickSpeed=findViewById(R.id.edtKickSpeed);
        edtName=findViewById(R.id.edtName);
        edtPunchSpeed=findViewById(R.id.edtPunchSpeed);
        txtGetData=findViewById(R.id.txtGetData);
        btnGetAllData=findViewById(R.id.btnGetAllData);
        allKickBoxers=" ";
        btnTransition=findViewById(R.id.btnNextActivity);
        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("kickBoxer");
                parseQuery.getInBackground("l3mm89fRf0", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object!=null && e==null)
                        {
                            txtGetData.setText(object.get("name")+" ");
                        }
                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject>parseAll=ParseQuery.getQuery("kickBoxer");
                parseAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override

                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null)
                        {
                            if(objects.size()>0)
                            {
                                for(ParseObject kickBoxer : objects)
                                {
                                        allKickBoxers+=kickBoxer.get("name")+"\n";
                                }
                                FancyToast.makeText(MainActivity.this,allKickBoxers, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();

                            }
                            else
                            {
                                FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                            }
                        }
                    }
                });
            }
        });


        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,SignUpLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        try {
            final ParseObject kickBoxer = new ParseObject("kickBoxer");
            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(edtPunchSpeed.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(edtKickPower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null)
                        FancyToast.makeText(MainActivity.this, kickBoxer.get("name") + " is saved in server  ", FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
                    else
                        FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                }
            });
        }
        catch (Exception e)
        {
            FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

        }
    }



}
