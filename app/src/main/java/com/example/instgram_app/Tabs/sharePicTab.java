package com.example.instgram_app.Tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instgram_app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class sharePicTab extends Fragment {


    public sharePicTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_share_pic_tab, container, false );
    }

}
