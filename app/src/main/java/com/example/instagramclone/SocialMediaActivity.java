package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class SocialMediaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        setTitle("Social Meadia");
        toolbar =findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        viewPager=findViewById(R.id.viewPager);
        //Below line initialize tabAdater class which we created

        tabAdapter=new TabAdapter(getSupportFragmentManager());//here we get tab that ewe created in fragments
        viewPager.setAdapter(tabAdapter);// Get it into viewPager

        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
