package com.brianstacks.cpmdproject1.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.brianstacks.cpmdproject1.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.List;


public class TextViewFragment extends Fragment {
    public static final String TAG = "TextViewFragment.TAG";
    private TextView titleTextView;
    private TextView userTextView;
    private TextView favColorTextView;
    private TextView phoneTextView;
    private Button loginButton;


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
        titleTextView = (TextView) getActivity().findViewById(R.id.profile_title);
        userTextView = (TextView) getActivity().findViewById(R.id.profile_name);
        favColorTextView = (TextView) getActivity().findViewById(R.id.favColorText);
        phoneTextView = (TextView) getActivity().findViewById(R.id.phoneNumberTextView);
        Button logoutButton = (Button) getActivity().findViewById(R.id.logout_button);
        loginButton = (Button) getActivity().findViewById(R.id.login_button);
        Button editData = (Button) getActivity().findViewById(R.id.editData);
        loginButton.setVisibility(View.INVISIBLE);
        titleTextView.setText("UserName");
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
        // Syncing Local Changes
        ParseQuery<ParseObject> localQueryObjects = ParseQuery
                .getQuery("UserObjects");
        localQueryObjects.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userObjects, ParseException e) {
                for (ParseObject user : userObjects) {
                    favColorTextView.setText( user.get("favColor").toString());
                    phoneTextView.setText(String.valueOf(user.getInt("phoneNum")));
                }
            }
        });
        loginButton.setText("Logout");
    }


    public void showProfileLoggedOut() {
        FragmentManager mgr = getFragmentManager();
        FragmentTransaction trans = mgr.beginTransaction();
        LogInFragment logInFragment = new LogInFragment();
        trans.replace(R.id.frag1, logInFragment, LogInFragment.TAG).addToBackStack(LogInFragment.TAG).commit();

    }


}
