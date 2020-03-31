package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPost extends AppCompatActivity {

        private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);
        linearLayout=findViewById(R.id.linearLayout);
        Intent receivedIntentObject=getIntent();
        final String receivedUserName=receivedIntentObject.getStringExtra("username");
        FancyToast.makeText(this,receivedUserName, Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

        setTitle(receivedUserName+"'s posts");


        ParseQuery <ParseObject> parseQuery=new ParseQuery<ParseObject>("Photos");
        parseQuery.whereEqualTo("username",receivedUserName);
        parseQuery.orderByDescending("createdAt");

        ///////Dialog dialog=new Dialog(this);
//        dialog.setTitle("Loading ... ");
//        dialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(objects.size()>0 && e==null)
                {
                    for(ParseObject post : objects){
                        final TextView postDescription=new TextView(UsersPost.this);
                        postDescription.setText(post.get("image_des")+"");

                        ParseFile postPicture=(ParseFile) post.get("picture");

                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if(data!=null && e==null)
                                {
                                    Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageView=new ImageView(UsersPost.this);
                                    LinearLayout.LayoutParams imageView_params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageView_params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageView_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                                    des_params.setMargins(5,5,5,5);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setTextSize(24f);


                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDescription);


                                }
                                else
                                {
                                    FancyToast.makeText(UsersPost.this,receivedUserName+" doesn't have any post ",Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
                                    finish();
                                }
                            }
                        });
                    }
                }

            }
        });
    }
}
