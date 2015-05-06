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
import android.widget.TextView;
import android.widget.Toast;

import com.brianstacks.cpmdproject1.R;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TextViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextViewFragment extends Fragment {
    private static final int LOGIN_REQUEST = 0;
    public static final String TAG = "TextViewFragment.TAG";

    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private TextView favColorTextView;
    private TextView phoneTextView;

    private Button LogoutButton;
    private Button loginButton;
    private Button editData;



    private ParseUser currentUser;

    // TODO: Rename and change types and number of parameters
    public static TextViewFragment newInstance(String param1, String param2) {
        TextViewFragment fragment = new TextViewFragment();
        return fragment;
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
        titleTextView = (TextView) getActivity().findViewById(R.id.profile_title);
        emailTextView = (TextView) getActivity().findViewById(R.id.profile_email);
        nameTextView = (TextView) getActivity().findViewById(R.id.profile_name);
        favColorTextView = (TextView) getActivity().findViewById(R.id.favColorText);
        phoneTextView = (TextView) getActivity().findViewById(R.id.phoneNumberTextView);
        LogoutButton = (Button) getActivity().findViewById(R.id.logout_button);
        loginButton = (Button) getActivity().findViewById(R.id.login_button);
        editData = (Button) getActivity().findViewById(R.id.editData);
        loginButton.setVisibility(View.INVISIBLE);
        titleTextView.setText(R.string.profile_title_logged_in);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager mgr = getFragmentManager();
                FragmentTransaction trans = mgr.beginTransaction();
                LogInFragment logInFragment = new LogInFragment();
                trans.replace(R.id.frag1, logInFragment, LogInFragment.TAG).addToBackStack(LogInFragment.TAG).commit();
            }
        });
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    // User clicked to log out.
                    ParseUser.logOut();
                    currentUser = null;
                    showProfileLoggedOut();
                } else {
                    Log.v("hit it again","error");
                    // User clicked to log in.
                   /* ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
                            SampleProfileActivity.this);
                    startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);*/
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

    /**
     * Shows the profile of the given user.
     */
    private void showProfileLoggedIn() {
        titleTextView.setText(R.string.profile_title_logged_in);
        emailTextView.setText(currentUser.getUsername());
        String fullName = currentUser.getString("name");
        String favColor = currentUser.getString("favColor");
        String phone = currentUser.getString("phone");
        Log.v("phone",phone);
        if (fullName != null) {
            nameTextView.setText(fullName);
        }else if (favColor != null){
            favColorTextView.setText(favColor);
            phoneTextView.setText(phone);
        }
        loginButton.setText(R.string.profile_login_button_label);

    }

    /**
     * Show a message asking the user to log in, toggle login/logout button text.
     */
    private void showProfileLoggedOut() {
        titleTextView.setText(R.string.profile_title_logged_out+" "+"Please sign in again.");
        emailTextView.setText("");
        nameTextView.setText("");
        LogoutButton.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.VISIBLE);

    }


}
