package com.example.instgram_app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {
    private TextView Messeage;
    private Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        setTitle("Home");
        Messeage  =findViewById(R.id.Message);
        Messeage.setText("Wellcome "+ParseUser.getCurrentUser().getUsername());
        Logout = findViewById( R.id.Logout );
        Logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog WaitingDialog =new ProgressDialog( HomeActivity.this );
                WaitingDialog.setMessage("logout");
                WaitingDialog.show();

                ParseUser.logOut();
                finish();
                Messeage.setText("");
                WaitingDialog.dismiss();
            }
        } );

    }
}
