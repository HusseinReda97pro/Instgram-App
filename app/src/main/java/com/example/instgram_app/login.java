package com.example.instgram_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class login extends AppCompatActivity {
    private EditText FullName,Password;
    private Button LoginButton;
    private TextView LoginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        FullName = findViewById(R.id.LoginUserName);
        Password = findViewById(R.id.LoginPassword);
        LoginError = findViewById(R.id.LoginError);

        LoginButton = findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog WaitingDialog =new ProgressDialog( login.this );
                WaitingDialog.setMessage("wait for Login in");
                WaitingDialog.show();

                ParseUser.logInInBackground( FullName.getText().toString(),
                        Password.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(e==null&&user != null){
                                    Intent HomeIntent = new Intent( login.this,HomeActivity.class);
                                    startActivity(HomeIntent);
                                    LoginError.setText("");

                                }else if (user == null){
                                    LoginError.setText("No such This user name Signup First");
                                }else{
                                    Toast.makeText( login.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT ).show();
                                    LoginError.setText("user name or password is wrong");

                                }
                                WaitingDialog.dismiss();

                            }
                        } );
            }
        } );

    }
    public void goToSignupActivity(View v){
        Intent signupIntent = new Intent( login.this,signup.class);
        startActivity(signupIntent);

    }

}
