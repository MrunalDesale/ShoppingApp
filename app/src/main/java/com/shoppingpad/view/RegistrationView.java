package com.shoppingpad.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shoppingpad.R;
import com.shoppingpad.databinding.RegistrationBinding;
import com.shoppingpad.util.VerifyNumberFormat;
import com.shoppingpad.viewmodel.RegistrationViewModelHandler;

/**
 * Created by bridgelabz3 on 21/3/16.
 * Purpose:
 *
 * 1.Initial flow of program starts from this class
 * 2.This class display UI.
 * 3.It takes data from ViewModel and shows components to user.
 * 4.It contains components and methods required by user for interaction.
 */
public class RegistrationView extends AppCompatActivity{

    TextView mMessage1,mMessage2;
    EditText mPhoneNumber,mCountryCode,mNameEditText;
    Spinner mSpinner;
    Button mRegistration,mVerify,mNext;
    String mPhoneNo;
    boolean mVerificationResult;
    Context mContext;

    //Returns instance of this class...
    Context getContext(){
        mContext=this;
        return mContext;
    }

    RegistrationViewModelHandler mRegistrationVMHandler;
    VerifyNumberFormat mVerifyNumberFormat;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create binding object...
        final RegistrationBinding registrationBinding= DataBindingUtil.setContentView(
                this,R.layout.registration);

        //Set registration layout...
        setContentView(R.layout.registration);
        mVerificationResult=false;

        //Code to find contents displayed on registration screen...
        mRegistration=(Button)findViewById(R.id.Registration);
        mVerify= (Button) findViewById(R.id.verify);
        mPhoneNumber=(EditText)findViewById(R.id.phone_number);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mCountryCode=(EditText)findViewById(R.id.countryCode);
        mMessage1=(TextView)findViewById(R.id.Message1);
        mMessage2=(TextView)findViewById(R.id.Message2);
        mNext= (Button) findViewById(R.id.next);

        //Message set to EditText...
        mMessage1.setText("ShoppingPad app will send a one time SMS message to " +
                "verify your phone number.");
        mMessage2.setText("Please confirm your country code and enter mobile " +
                "number.");

        //Click event of number verification...
        mRegistration.setOnClickListener(new View.OnClickListener() {
            String nm,no;
            @Override
            public void onClick(View v) {

                mVerifyNumberFormat=new VerifyNumberFormat();
                //Get mobile number from EditText...
                no = String.valueOf(mPhoneNumber.getText());
                mPhoneNo=no;

                //Get result of number format
                mVerificationResult=mVerifyNumberFormat.verifyNumberFormat(no);

                //If format is correct then...
                if (mVerificationResult) {
                    mRegistrationVMHandler=new RegistrationViewModelHandler();
                    //Set set Otp layout...
                    setContentView(R.layout.received_otp);
                    EditText textV1 = (EditText) findViewById(R.id.OTP);

                    mRegistrationVMHandler.setOtp(textV1,no,getContext());
                    mVerify= (Button) findViewById(R.id.verify);

                    //On click event of verify OTP...
                    mVerify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //Set layout of Insert name...
                            setContentView(R.layout.name_insert);
                            mNameEditText = (EditText) findViewById(R.id.name);
                            mNext= (Button) findViewById(R.id.next);

                            //On click event of next button to proceed...
                            mNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    nm = String.valueOf(mNameEditText.getText());
                                    User user=new User(nm,no);
                                    registrationBinding.setUser(user);
                                    new RegistrationAsync().execute(user.
                                            getmMobileNo(),user.getmUserName());
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    //AsyncTask call to get response and print that response from server...
    class RegistrationAsync extends AsyncTask<String,String,String>{

        String mResponse;
        @Override
        protected String doInBackground(String... params) {

            //Call method and get response from server...
            mResponse= mRegistrationVMHandler.setUserData(params[0], params[1]);
            return mResponse;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            Toast.makeText(RegistrationView.this, ""+mResponse, Toast.LENGTH_SHORT)
                                                                .show();
            //Start next activity...
            startActivity(new Intent(RegistrationView.this,ContentListView.class));
        }
    }
}