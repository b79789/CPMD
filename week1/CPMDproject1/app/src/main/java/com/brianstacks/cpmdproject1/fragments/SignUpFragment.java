package com.brianstacks.cpmdproject1.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.brianstacks.cpmdproject1.R;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpFragment extends Fragment {
    public static final String TAG = "SignUpFragment.TAG";


    public SignUpFragment() {
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
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        Button signUp = (Button) getActivity().findViewById(R.id.button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e1 = (EditText) getActivity().findViewById(R.id.editText);
                final EditText e2 = (EditText) getActivity().findViewById(R.id.editText2);
                EditText e3 = (EditText) getActivity().findViewById(R.id.editText3);


                if (e1.getText().toString().equals("")) {
                    e1.setError("Must enter Name");
                } else if (e2.getText().length() == 0 && isValid(e2.getText().toString())) {
                    e2.setError("Must enter valid Email");
                } else if (e3.getText().toString().equals("")) {
                    e3.setError("Must enter password");
                } else {
                    ParseUser user = new ParseUser();
                    user.setUsername(e1.getText().toString());
                    user.setPassword(e3.getText().toString());
                    user.setEmail(e2.getText().toString());
                    ParseACL.setDefaultACL(new ParseACL(), true);

                    /*// other fields can be set just like with ParseObject
                    user.put("favColor",e4.getText().toString());
                    user.put("phone", e5.getText().toString());*/

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                hideKeyboard(getActivity());
                                // Hooray! Let them use the app now.
                                FragmentManager mgr = getFragmentManager();
                                FragmentTransaction trans = mgr.beginTransaction();
                                TextViewFragment textViewFragment = TextViewFragment.newInstance();
                                trans.replace(R.id.frag1, textViewFragment, TextViewFragment.TAG).addToBackStack(TextViewFragment.TAG).commit();
                            } else {
                                // Sign up didn't succeed. Look at the ParseException
                                // to figure out what went wrong
                                e2.setError("Must enter valid Email");
                            }
                        }
                    });
                }
            }
        });
    }

    public static boolean isValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
