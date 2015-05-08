package com.brianstacks.cpmdproject1.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.brianstacks.cpmdproject1.R;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class EnterDataFragment extends Fragment {
    public static final String TAG = "EnterDataFragment.TAG";

    Button saveButton;
    EditText colorEnter;
    EditText numEnter;


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
        Button deleteButton = (Button)getActivity().findViewById(R.id.deleteUserInfo);
        colorEnter.clearFocus();
        numEnter.clearFocus();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = (int)Double.parseDouble(numEnter.getText().toString().trim());
                ParseObject privateNote = new ParseObject("UserObjects");
                privateNote.put("favColor", colorEnter.getText().toString());
                privateNote.put("phoneNum",x);
                privateNote.setACL(new ParseACL(ParseUser.getCurrentUser()));
                privateNote.saveInBackground();
                FragmentManager mgr = getFragmentManager();
                FragmentTransaction trans = mgr.beginTransaction();
                TextViewFragment textViewFragment =  new TextViewFragment();
                trans.replace(R.id.frag1, textViewFragment, TextViewFragment.TAG).addToBackStack(TextViewFragment.TAG).commit();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = ParseUser.getCurrentUser();
                user.deleteInBackground();
                FragmentManager mgr = getFragmentManager();
                FragmentTransaction trans = mgr.beginTransaction();
                InitFragment initFragment =  new InitFragment();
                trans.replace(R.id.frag1, initFragment, InitFragment.TAG).commit();

            }
        });
    }

}
