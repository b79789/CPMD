package com.brianstacks.cpmdproject1.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brianstacks.cpmdproject1.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFragment extends Fragment {
    public static final String TAG = "SignUpFragment.TAG";


    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance(String param1, String param2) {

        return new LogInFragment();
    }

    public LogInFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

        final EditText emailText = (EditText)getActivity().findViewById(R.id.editText2SU);
        final EditText passText = (EditText)getActivity().findViewById(R.id.editText2SU);
        Button loginButt =  (Button)getActivity().findViewById(R.id.button);
        loginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(emailText.getText().toString(), passText.getText().toString(), new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // Hooray! The user is logged in.
                            FragmentManager mgr = getFragmentManager();
                            FragmentTransaction trans = mgr.beginTransaction();
                            TextViewFragment textViewFragment =  new TextViewFragment();
                            trans.replace(R.id.frag1, textViewFragment, TextViewFragment.TAG).addToBackStack(TextViewFragment.TAG).commit();
                        } else {
                            // Signup failed. Look at the ParseException to see what happened.
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();


                        }
                    }
                });
            }
        });

    }

}
