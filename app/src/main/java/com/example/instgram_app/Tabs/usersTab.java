package com.example.instgram_app.Tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.instgram_app.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class usersTab extends Fragment {
    ListView usersList;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;

    public usersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_users_tab, container, false );
        usersList = view.findViewById( R.id.usersList );
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter( getContext(),android.R.layout.simple_list_item_1,arrayList);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo( "username",ParseUser.getCurrentUser().getUsername() );
        query.findInBackground( new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e==null){
                    if(users.size()>0){
                        for (ParseUser user:users){
                            arrayList.add( user.getUsername() );
                        }
                        usersList.setAdapter( arrayAdapter );
                    }
                }
            }
        } );

        return view;
    }

}
