package com.example.instgram_app.Tabs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instgram_app.R;
import com.example.instgram_app.userPosts;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class usersTab extends Fragment {
    ListView usersList;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    TextView Loading;

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
        usersList.setOnItemClickListener( new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent Post = new Intent( getContext(), userPosts.class );
                Post.putExtra( "userName",arrayList.get( position ) );
                startActivity( Post );
            }
        } );
        usersList.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo( "username",arrayList.get( position ) );
                query.getFirstInBackground( new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null && e == null){
                           final PrettyDialog prettyDialog = new PrettyDialog(getContext());
                            prettyDialog.setTitle(user.getUsername() +"'s Info")
                                        .setMessage("Email : " +"pro.hussein.reda@gmail.com"+"\n"+
                                                    "Age : "+user.get("age")+"\n"
                                        )
                                        .setIcon(R.drawable.person)
                                        .addButton( "Ok",// text
                                                R.color.pdlg_color_white, //text Color
                                                R.color.pdlg_color_green, // Background Color
                                                new PrettyDialogCallback() {
                                                    @Override
                                                    public void onClick() {
                                                        prettyDialog.dismiss();
                                                    }
                                                }
                                        )
                                        .show();
                        }

                    }
                } );
                return true;
            }
        } );

        Loading = view.findViewById( R.id.usersLoading );
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
                        Loading.animate().alpha(0).setDuration( 2000 );
                        usersList.setVisibility( View.VISIBLE);
                    }
                }
            }
        } );

        return view;
    }

}
