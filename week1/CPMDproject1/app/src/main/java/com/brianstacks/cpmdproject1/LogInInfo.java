package com.brianstacks.cpmdproject1;

import java.io.Serializable;

/**
 * Created by Brian Stacks
 * on 5/4/15
 * for FullSail.edu.
 */
public class LogInInfo implements Serializable {

    private static final long serialVersionUID = 8733333333330552888L;
    private String mName;
    private String mEmail;
    private String mPassword;

    public LogInInfo(){
        mName="";
        mEmail="";
        mPassword="";
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getEmail() {
        return mEmail;
    }
    public void  setEmail(String email) {
        mEmail= email;
    }
    public String getPassword() {
        return mPassword;
    }
    public void setPassword(String password) {
        mPassword=password;
    }

}
