package com.brianstacks.cpmdproject1;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.brianstacks.cpmdproject1.fragments.LogInFragment;
import com.brianstacks.cpmdproject1.fragments.TextViewFragment;
import com.parse.Parse;
import com.parse.ParseUser;



public class MainActivity extends Activity{

    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        ParseUser.enableRevocableSessionInBackground();
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            FragmentManager mgr = getFragmentManager();
            FragmentTransaction trans = mgr.beginTransaction();
            TextViewFragment textViewFragment = new TextViewFragment();
            trans.replace(R.id.frag1, textViewFragment, TextViewFragment.TAG).addToBackStack(TextViewFragment.TAG);
            trans.commit();
        } else {
            FragmentManager mgr = getFragmentManager();
            FragmentTransaction trans = mgr.beginTransaction();
            LogInFragment initFragment = new LogInFragment();
            trans.replace(R.id.frag1, initFragment, LogInFragment.TAG).addToBackStack(LogInFragment.TAG);
            trans.commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean isOnline(){
        ConnectivityManager connectivityManager =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }
}
