package com.example.instgram_app.Tabs;


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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instgram_app.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class sharePicTab extends Fragment {
    private ImageView img;
    private EditText  caption ;
    private Button   post ;
    private   Bitmap receiveImageBitmap;

    public sharePicTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_share_pic_tab, container, false );
        img = view.findViewById(R.id.imageView);
        caption =view.findViewById(R.id.imagecaption);
        post = view.findViewById(R.id.imagepost);
        img.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // first we check if  android version of user devices is grater than 23 'marshmelo' or higher
                //because this versions need  allow Permission for danger Permissions
                //and then check if we didn't have READ_EXTERNAL_STORAGE Permission, we  request it
                if(android.os.Build.VERSION.SDK_INT >=23 && ActivityCompat.checkSelfPermission( getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},    1000);
                }else {
                    getImage();
                }

            }
        } );
        post.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( receiveImageBitmap != null){
                    if(caption.getText().toString().equals("")){
                        Toast.makeText( getContext(),"You must Enter Caption", Toast.LENGTH_LONG ).show();
                    }else{
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receiveImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes =byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile( "img.png",bytes );
                        ParseObject parseObject = new ParseObject( "photo" );
                        parseObject.put( "picture",parseFile );
                        parseObject.put( "image_des",caption.getText().toString() );
                        parseObject.put( "username", ParseUser.getCurrentUser().getUsername() );
                        final ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setMessage( "Loading..." );
                        dialog.show();
                        parseObject.saveInBackground( new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    Toast.makeText( getContext(),"image Posted successfully", Toast.LENGTH_LONG ).show();

                                }else {
                                    Toast.makeText( getContext(),"something wrong", Toast.LENGTH_LONG ).show();
                                }
                                dialog.dismiss();
                            }
                        } );
                    }
                }else{
                    Toast.makeText( getContext(),"No Image Selected", Toast.LENGTH_LONG ).show();
                }

            }
        } );
        return  view;
    }

    private void getImage() {
        //Toast.makeText( getContext(),"hi hi capten",Toast.LENGTH_LONG ).show();
        Intent intent=new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        startActivityForResult(intent,2000);
    }
    // to check the Result of Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if(requestCode ==1000){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getImage();
            }
        }
    }
    // to check Result of pick image Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode ==2000){
            if(resultCode == Activity.RESULT_OK){
                try {
                    Uri selectedImage =data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePath,null,null,null);
                    cursor.moveToFirst();
                    int index = cursor.getColumnIndex(filePath[0]);
                    String ImagePath = cursor.getString(index);
                    cursor.close();
                    receiveImageBitmap = BitmapFactory.decodeFile( ImagePath );
                    img.setImageBitmap( receiveImageBitmap );
                }catch (Exception e){

                }
            }
        }
    }
}
