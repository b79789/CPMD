package com.brianstacks.cpmdproject1.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.brianstacks.cpmdproject1.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TextViewFragment extends Fragment {
    public static final String TAG = "TextViewFragment.TAG";
     TextView titleTextView;
     TextView userTextView;
     TextView favColorTextView;
     TextView phoneTextView;
     Button loginButton;
     Button refreshButt;



    private ParseUser currentUser;

    public static TextViewFragment newInstance() {
        return new TextViewFragment();
    }

    public TextViewFragment() {
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
        return inflater.inflate(R.layout.fragment_text_view, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        offlineUpdate();
        titleTextView = (TextView) getActivity().findViewById(R.id.profile_title);
        userTextView = (TextView) getActivity().findViewById(R.id.profile_name);
        favColorTextView = (TextView) getActivity().findViewById(R.id.favColorText);
        phoneTextView = (TextView) getActivity().findViewById(R.id.phoneNumberTextView);
        Button logoutButton = (Button) getActivity().findViewById(R.id.logout_button);
        loginButton = (Button) getActivity().findViewById(R.id.login_button);
        Button editData = (Button) getActivity().findViewById(R.id.editData);
        loginButton.setVisibility(View.INVISIBLE);
        refreshButt = (Button) getActivity().findViewById(R.id.refreshButton);
        titleTextView.setText("UserName");
        refreshButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()){
                    updatingDataText();
                }else {
                    Toast.makeText(getActivity(),"Network isn't available ",Toast.LENGTH_LONG).show();
                    offlineUpdate();
                }

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager mgr = getFragmentManager();
                FragmentTransaction trans = mgr.beginTransaction();
                LogInFragment logInFragment = new LogInFragment();
                trans.replace(R.id.frag1, logInFragment, LogInFragment.TAG).addToBackStack(LogInFragment.TAG).commit();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    // User clicked to log out.
                    ParseUser.logOut();
                    currentUser = null;
                    showProfileLoggedOut();
                }
            }
        });
        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager mgr = getFragmentManager();
                FragmentTransaction trans = mgr.beginTransaction();
                EnterDataFragment enterDataFragment = new EnterDataFragment();
                trans.replace(R.id.frag1, enterDataFragment, EnterDataFragment.TAG).addToBackStack(EnterDataFragment.TAG).commit();
            }
        });
    }

    public void offlineUpdate() {
        // Syncing Local Changes
        ParseQuery<ParseObject> localQueryObjects = ParseQuery.getQuery("UserObjects");
        localQueryObjects.fromLocalDatastore();
        localQueryObjects.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject != null) {
                    favColorTextView.setText( parseObject.get("favColor").toString());
                    phoneTextView.setText(String.valueOf(parseObject.getInt("phoneNum")));

                } else {
                    Toast.makeText(getActivity(), "Data Not Available ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    protected boolean isOnline(){
        ConnectivityManager connectivityManager =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            showProfileLoggedIn();
        } else {
            showProfileLoggedOut();
        }
    }


    public void showProfileLoggedIn() {
        titleTextView.setText("UserName");
        userTextView.setText(currentUser.getUsername());
        updatingDataText();
        loginButton.setText("Logout");
    }

    public void updatingDataText() {
        // Syncing Local Changes
        ParseQuery<ParseObject> localQueryObjects = ParseQuery
                .getQuery("UserObjects");
        localQueryObjects.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userObjects, ParseException e) {
                if (userObjects!= null){
                    for (ParseObject user : userObjects) {
                        favColorTextView.setText( user.get("favColor").toString());
                        phoneTextView.setText(String.valueOf(user.getInt("phoneNum")));
                    }
                }else {
                    Toast.makeText(getActivity(),"Network isn't available ",Toast.LENGTH_LONG).show();

                }

            }
        });
    }


    public void showProfileLoggedOut() {
        FragmentManager mgr = getFragmentManager();
        FragmentTransaction trans = mgr.beginTransaction();
        LogInFragment logInFragment = new LogInFragment();
        trans.replace(R.id.frag1, logInFragment, LogInFragment.TAG).addToBackStack(LogInFragment.TAG).commit();

    }


}
