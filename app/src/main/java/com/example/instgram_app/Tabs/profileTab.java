package com.example.instgram_app.Tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instgram_app.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileTab extends Fragment {
    EditText Age ;
    Button update;


    public profileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate( R.layout.fragment_profile_tab, container, false );
        Age = view.findViewById( R.id.Agefiled );
        update = view.findViewById( R.id.updateAge );
        final ParseUser user = ParseUser.getCurrentUser();
        if(user.get("age")==null){
            Age.setText( "" );
        }else{
            Age.setText( user.get("age").toString());
        }
        update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.put( "age",Age.getText().toString() );
                user.saveInBackground( new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText( getContext(),"Update successfully",Toast.LENGTH_LONG ).show();
                        }else {
                            Toast.makeText( getContext(),"Filed to Update",Toast.LENGTH_LONG ).show();

                        }

                    }
                } );
            }
        } );
        return  view;
    }

}
