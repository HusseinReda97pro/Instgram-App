package com.example.instgram_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class signup extends AppCompatActivity {
    private EditText FullName, Email, Password;
    private Button Signup;
    private TextView SignupError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup );
//        if(ParseUser.getCurrentUser() != null){
//            Intent HomeIntent = new Intent( signup.this, Home.class );
//            startActivity( HomeIntent );
//
//        }
        setTitle( "Signup" );
        FullName = findViewById( R.id.FullNameForSignup );
        Email = findViewById( R.id.E_mailForSignUp );
        Password = findViewById( R.id.PasswordForSignup );
        Signup = findViewById( R.id.SignupButton );
        SignupError = findViewById( R.id.SignupError );
      //  Password.setOnKeyListener( new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//
//
//                }
//                return false;
//            }
//        } );


        Signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FullName.getText().toString().equals( "" )) {
                    SignupError.setText( "User Name Needed" );
                } else if (Email.getText().toString().equals( "" )) {
                    SignupError.setText( "Email Needed" );

                } else if (Password.getText().toString().equals( "" )) {
                    SignupError.setText( "Password Needed" );

                } else {
                    ParseUser user = new ParseUser();
                    user.setUsername( FullName.getText().toString() );
                    user.setEmail( Email.getText().toString() );
                    user.setPassword( Password.getText().toString() );
                    final ProgressDialog WaitingDialog = new ProgressDialog( signup.this );
                    WaitingDialog.setMessage( "wait for Signing up" );
                    WaitingDialog.show();


                    user.signUpInBackground( new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText( signup.this, "Signup sucessful", Toast.LENGTH_SHORT ).show();
                                Intent loginIntent = new Intent( signup.this, login.class );
                                startActivity( loginIntent );


                            } else {
                                Toast.makeText( signup.this, "Error", Toast.LENGTH_SHORT ).show();
                            }
                            WaitingDialog.dismiss();
                        }
                    } );
                }


            }
        } );
    }

    public void HideKeybord(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService( INPUT_METHOD_SERVICE );
            inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0 );
        } catch (Exception e) {
        }


    }



}
