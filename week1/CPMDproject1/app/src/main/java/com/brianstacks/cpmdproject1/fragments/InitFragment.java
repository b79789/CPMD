package com.brianstacks.cpmdproject1.fragments;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.brianstacks.cpmdproject1.LogInInfo;
import com.brianstacks.cpmdproject1.R;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InitFragment extends Fragment {

    public static InitFragment newInstance() {

        return new InitFragment();
    }

    public InitFragment() {
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
        return inflater.inflate(R.layout.fragment_init, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);





        Button signUp =(Button)getActivity().findViewById(R.id.signUpButton);
        Button login =(Button)getActivity().findViewById(R.id.logInButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.enableRevocableSessionInBackground();

                FragmentManager mgr = getFragmentManager();
                FragmentTransaction trans = mgr.beginTransaction();
                SignUpFragment signUpFragment = new SignUpFragment();
                trans.replace(R.id.frag1, signUpFragment, SignUpFragment.TAG).addToBackStack(SignUpFragment.TAG).commit();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager mgr = getFragmentManager();
                FragmentTransaction trans = mgr.beginTransaction();
                LogInFragment logInFragment = new LogInFragment();
                trans.replace(R.id.frag1, logInFragment, LogInFragment.TAG).addToBackStack(LogInFragment.TAG).commit();
            }
        });

    }

}
