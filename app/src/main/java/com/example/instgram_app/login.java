package com.example.instgram_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
                               try{
                                   if(e==null&&user != null){
                                       goToHomeActivity();
                                       LoginError.setText("");

                                   }else if (user == null){
                                       LoginError.setText("No such This user name Signup First");
                                   }else{
                                       Toast.makeText( login.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT ).show();
                                       LoginError.setText("user name or password is wrong");

                                   }
                               }catch(Exception e1){
                                   Toast.makeText( login.this,"something wrong",Toast.LENGTH_SHORT ).show();



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
    public void goToHomeActivity(){
        Intent HomeIntent = new Intent( login.this, Home.class);
        startActivity(HomeIntent);
        finish();


    }
    public void HideKeybord(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService( INPUT_METHOD_SERVICE );
            inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0 );
        } catch (Exception e) {
        }


    }

}
