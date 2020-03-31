package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import android.app.ProgressDialog;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class SocialMediaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        setTitle("Social Media");
        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        //Below line initialize tabAdater class which we created

        tabAdapter = new TabAdapter(getSupportFragmentManager());//here we get tab that ewe created in fragments
        viewPager.setAdapter(tabAdapter);// Get it into viewPager

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.postImageItem)
        {
            if (android.os.Build.VERSION.SDK_INT >= 23 &&
                    ActivityCompat.checkSelfPermission(getBaseContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]
                                {Manifest.permission.READ_EXTERNAL_STORAGE},
                        3000);

            } else {
                captureImage();
            }

        }
        else if(item.getItemId()==R.id.logoutUserItem)
        {
            ParseUser.getCurrentUser().logOut();;
            finish();
            Intent intent=new Intent(SocialMediaActivity.this,MainActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 4000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 3000) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {

                captureImage();
            }

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==4000&&resultCode==RESULT_OK && data!=null)
        {
            try
            {
                Uri capturedImage=data.getData();
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),capturedImage);
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

                byte[]bytes=byteArrayOutputStream.toByteArray();
                ParseFile parseFile =new ParseFile("img.png",bytes);
                ParseObject parseObject=new ParseObject("Photo");
                parseObject.put("picture",parseFile);
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());


                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Loading...");
                dialog.show();

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                        {
                            FancyToast.makeText(SocialMediaActivity.this, "Picture Uploded", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        }
                        else
                        {
                            FancyToast.makeText(SocialMediaActivity.this,"Unknown "+e.getMessage(), Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                        }
                    }
                });

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
