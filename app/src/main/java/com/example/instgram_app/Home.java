package com.example.instgram_app;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instgram_app.Tabs.TabAddapter;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class Home extends AppCompatActivity {
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
        toolbar = findViewById( R.id.toolbar);
        setSupportActionBar( toolbar );
        ViewPager viewPager = findViewById( R.id.viewPager );
        tabAddapter = new TabAddapter(getSupportFragmentManager());
        viewPager.setAdapter( tabAddapter );
        tabLayout = findViewById( R.id.tabLayout );
        tabLayout.setupWithViewPager( viewPager ,true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.capture_img){
            if(android.os.Build.VERSION.SDK_INT >=23 && checkSelfPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},    1000);
            }else {
                getImage();
            }

        }else if(item.getItemId() == R.id.Logout){
            ParseUser.getCurrentUser().logOut();
            finish();
            Intent HomeIntent = new Intent( Home.this,login.class);
            startActivity(HomeIntent);


        }
        return super.onOptionsItemSelected( item );

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if(requestCode ==1000){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getImage();
            }
        }
    }
    private void getImage() {
        //Toast.makeText( getContext(),"hi hi capten",Toast.LENGTH_LONG ).show();
        Intent intent=new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        startActivityForResult(intent,2000);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode ==2000){
            if(resultCode == Activity.RESULT_OK &&data != null){
                try {
                    Uri selectedImage =data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] bytes =byteArrayOutputStream.toByteArray();
                    ParseFile parseFile = new ParseFile( "img.png",bytes );
                    ParseObject parseObject = new ParseObject( "photo" );
                    parseObject.put( "picture",parseFile );
                    parseObject.put( "username", ParseUser.getCurrentUser().getUsername() );
                    final ProgressDialog dialog = new ProgressDialog(Home.this);
                    dialog.setMessage( "Loading..." );
                    dialog.show();
                    parseObject.saveInBackground( new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                Toast.makeText( Home.this,"image Posted successfully", Toast.LENGTH_LONG ).show();

                            }else {
                                Toast.makeText( Home.this,"something wrong", Toast.LENGTH_LONG ).show();
                            }
                            dialog.dismiss();
                        }
                    } );
                }catch (Exception e){

                }
            }
        }
    }
}
