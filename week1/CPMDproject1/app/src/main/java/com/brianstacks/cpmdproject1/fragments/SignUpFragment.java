package com.brianstacks.cpmdproject1.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.brianstacks.cpmdproject1.LogInInfo;
import com.brianstacks.cpmdproject1.R;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpFragment extends Fragment {
    public static final String TAG = "SignUpFragment.TAG";
    OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

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
        Button signUp = (Button)getActivity().findViewById(R.id.button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInInfo logInInfo = new LogInInfo();
                EditText e1 = (EditText)getActivity().findViewById(R.id.editText);
                EditText e2 = (EditText)getActivity().findViewById(R.id.editText2);
                EditText e3 = (EditText)getActivity().findViewById(R.id.editText3);
                EditText e4 = (EditText)getActivity().findViewById(R.id.editText4);

                EditText e5 = (EditText)getActivity().findViewById(R.id.editText5);

                if (e1.getText().toString().equals("")){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Must enter Name");
                    builder1.setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }else if (e2.getText().toString().equals("")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Must enter Email");
                    builder1.setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }else if (e3.getText().toString().equals("")){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Must enter Password");
                    builder1.setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }else if (e4.getText().toString().equals("")){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Must enter favorite color");
                    builder1.setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }else if (e5.getText().toString().equals("")){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Must enter phone number");
                    builder1.setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }else {
                    ParseUser user = new ParseUser();
                    user.setUsername(e1.getText().toString());
                    user.setPassword(e3.getText().toString());
                    user.setEmail(e2.getText().toString());

                    // other fields can be set just like with ParseObject
                    user.put("favColor",e4.getText().toString());
                    user.put("phone", e5.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                // Hooray! Let them use the app now.
                                FragmentManager mgr = getFragmentManager();
                                FragmentTransaction trans = mgr.beginTransaction();
                                TextViewFragment textViewFragment =  TextViewFragment.newInstance("","");
                                trans.replace(R.id.frag1, textViewFragment, TextViewFragment.TAG).addToBackStack(TextViewFragment.TAG).commit();
                            } else {
                                // Sign up didn't succeed. Look at the ParseException
                                // to figure out what went wrong
                                Log.v("error*",e.toString());
                            }
                        }
                    });


                    mListener.onFragmentInteraction(logInInfo);


                }
            }
        });



    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(LogInInfo data);
    }

}