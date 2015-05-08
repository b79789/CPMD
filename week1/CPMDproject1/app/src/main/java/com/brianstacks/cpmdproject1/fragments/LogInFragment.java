package com.brianstacks.cpmdproject1.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.brianstacks.cpmdproject1.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LogInFragment extends Fragment {
    public static final String TAG = "SignUpFragment.TAG";
    EditText emailText;
    EditText passText;
    CheckBox rememberMeCbx;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    Boolean saveLogin;

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

        emailText = (EditText)getActivity().findViewById(R.id.editText2SU);
        passText = (EditText)getActivity().findViewById(R.id.editText3SU);
        rememberMeCbx = (CheckBox)getActivity().findViewById(R.id.saveLoginCheckBox);
        Button loginButt =  (Button)getActivity().findViewById(R.id.button);
        Button createnew = (Button)getActivity().findViewById(R.id.createNew);
        loginPreferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            emailText.setText(loginPreferences.getString("username", ""));
            rememberMeCbx.setChecked(true);
        }
        loginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(emailText.getText().toString(), passText.getText().toString(), new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            if (rememberMeCbx.isChecked()) {
                                loginPrefsEditor.putBoolean("saveLogin", true);
                                loginPrefsEditor.putString("username", emailText.getText().toString());
                                loginPrefsEditor.apply();

                            } else {
                                loginPrefsEditor.clear();
                                loginPrefsEditor.apply();

                            }
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
        createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager mgr = getFragmentManager();
                FragmentTransaction trans = mgr.beginTransaction();
                SignUpFragment signUpFragment = new SignUpFragment();
                trans.replace(R.id.frag1, signUpFragment, SignUpFragment.TAG).addToBackStack(SignUpFragment.TAG).commit();
            }
        });

    }



}
