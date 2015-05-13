package com.brianstacks.cpmdproject1.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class EnterDataFragment extends Fragment {
    public static final String TAG = "EnterDataFragment.TAG";

    Button saveButton;
    EditText colorEnter;
    EditText numEnter;
    List<MyTask> tasks;


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
        tasks=new ArrayList<>();
        colorEnter = (EditText)getActivity().findViewById(R.id.favColor);
        numEnter =(EditText)getActivity().findViewById(R.id.phoneNum);
        saveButton = (Button) getActivity().findViewById(R.id.saveUserInfo);
        Button deleteButton = (Button)getActivity().findViewById(R.id.deleteUserInfo);
        colorEnter.clearFocus();
        numEnter.clearFocus();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()){
                    requestData();
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
                }else {
                    Toast.makeText(getActivity(),"Network isn't avalaible",Toast.LENGTH_LONG).show();
                }

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

    private void requestData() {
        MyTask myTask = new MyTask();
        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "param1", "param3", "param3");
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

    private class MyTask extends AsyncTask<String, Integer, String>
    {
        protected void onPreExecute (){
            Log.d("PreExceute", "On pre Exceute......");
        }

        protected String doInBackground(String...arg0) {
            Log.d("DoINBackGround","On doInBackground...");

            for(int i=0; i<10; i++){
                Integer in = i;
                publishProgress(i);
            }
            return "You are at PostExecute";
        }

        protected void onProgressUpdate(String...a){
            Log.d("Progress update number",  a[0]);
        }

        protected void onPostExecute(String result) {
            Log.d("",result);
        }
    }

}
