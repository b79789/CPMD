package com.brianstacks.cpmdproject1.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brianstacks.cpmdproject1.R;
import com.parse.ParseUser;

public class EnterDataFragment extends Fragment {
    public static final String TAG = "EnterDataFragment.TAG";

    private Button saveButton;
    EditText colorEnter;
    EditText numEnter;

    public static EnterDataFragment newInstance(String param1, String param2) {
        EnterDataFragment fragment = new EnterDataFragment();
        return fragment;
    }

    public EnterDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_data, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        colorEnter = (EditText)getActivity().findViewById(R.id.favColor);
        numEnter =(EditText)getActivity().findViewById(R.id.phoneNum);
        saveButton = (Button) getActivity().findViewById(R.id.saveUserInfo);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"hit",Toast.LENGTH_SHORT).show();
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.put("favColor",colorEnter.getText().toString());
                currentUser.put("phone",numEnter.getText().toString());
                FragmentManager mgr = getFragmentManager();
                FragmentTransaction trans = mgr.beginTransaction();
                TextViewFragment textViewFragment =  new TextViewFragment();
                trans.replace(R.id.frag1, textViewFragment, TextViewFragment.TAG).addToBackStack(TextViewFragment.TAG).commit();
            }
        });
    }

}
