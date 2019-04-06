package com.example.instgram_app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.instgram_app.Tabs.TabAddapter;
import com.parse.ParseUser;

public class Home extends AppCompatActivity {
    private TextView Messeage;
    private Button Logout;
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAddapter tabAddapter;

    public Home() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        setTitle("Home");
//        Messeage  =findViewById(R.id.Message);
//        Messeage.setText("Wellcome "+ParseUser.getCurrentUser().getUsername());
          Logout = findViewById( R.id.Logout );
          Logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog WaitingDialog =new ProgressDialog( Home.this );
                WaitingDialog.setMessage("logout");
                WaitingDialog.show();

                ParseUser.logOut();
                finish();
                //Messeage.setText("");
                WaitingDialog.dismiss();
            }
        } );

        toolbar = findViewById( R.id.toolbar);
        setSupportActionBar( toolbar );

        ViewPager viewPager = findViewById( R.id.viewPager );

        tabAddapter = new TabAddapter(getSupportFragmentManager());
        viewPager.setAdapter( tabAddapter );

        tabLayout = findViewById( R.id.tabLayout );
        tabLayout.setupWithViewPager( viewPager ,true);

    }
}
