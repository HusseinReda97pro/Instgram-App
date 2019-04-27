package com.example.instgram_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class userPosts extends AppCompatActivity {
    private LinearLayout PostsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user_posts );
        PostsList = findViewById( R.id.PostsList );
        Intent ReceviedData = getIntent();
        String userName = ReceviedData.getStringExtra( "userName" );
        setTitle( userName +"'s Posts" );
        ParseQuery<ParseObject> query = new  ParseQuery<ParseObject>("photo");
        query.whereEqualTo( "username",userName );
        query.orderByDescending( "createdAt" );
        final ProgressDialog Load = new ProgressDialog( userPosts.this );
        Load.setMessage("Loading...");
        Load.show();
        query.findInBackground( new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e ==null ) {
                    for(ParseObject post : objects){
                        final TextView Caption = new TextView( userPosts.this );
                        Caption.setText(post.get("image_des")+"");
                        final ParseFile img = (ParseFile)post.get( "picture" );
                        img.getDataInBackground( new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data != null && e == null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray( data,0,data.length );// array of bytes , ofest , length
                                    ImageView postImage = new ImageView( userPosts.this );
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT ); //width , hight
                                    params.setMargins( 5,5,5,5 );
                                    postImage.setLayoutParams( params );
                                    postImage.setScaleType( ImageView.ScaleType.CENTER);
                                    postImage.setImageBitmap( bitmap );
                                    LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT );
                                    des_params.setMargins( 5,5,5,15 );
                                    Caption.setLayoutParams( des_params );
                                    Caption.setGravity( Gravity.CENTER );
                                    Caption.setBackgroundColor( Color.BLUE );
                                    Caption.setTextColor( Color.WHITE );
                                    Caption.setTextSize(30f);

                                    PostsList.addView(postImage);
                                    PostsList.addView( Caption );






                                }
                            }
                        } );
                    }
                }

                Load.dismiss();
            }

        } );



    }

}
