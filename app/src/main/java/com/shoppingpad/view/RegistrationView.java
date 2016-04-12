package com.shoppingpad.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shoppingpad.R;
import com.shoppingpad.databinding.Temp1Binding;
import com.shoppingpad.util.VerifyNumberFormat;
import com.shoppingpad.viewmodel.RegistrationViewModelHandler;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bridgelabz3 on 21/3/16.
 * Purpose:
 *
 * 1.Initial flow of program starts from this class
 * 2.This class display UI.
 * 3.It takes data from ViewModel and shows components to user.
 * 4.It contains components and methods required by user for interaction.
 */
public class RegistrationView extends AppCompatActivity {

    TextView mMessage1, mMessage2, mMessage3;
    EditText mPhoneNumber, mCountryCode, mNameEditText;
    Spinner mSpinner;
    Button mRegistration, mVerify, mNext;
    String mPhoneNo, mImagePath;
    boolean mVerificationResult;
    ImageView mProfilePic;
    Context mContext;
    RegistrationViewModelHandler mRegistrationVMHandler;
    VerifyNumberFormat mVerifyNumberFormat;
    Bitmap mBitmap;
    private byte[] mEncodedImage;


    //Returns instance of this class...
    Context getContext() {
        mContext = this;
        return mContext;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create binding object...
        final Temp1Binding temp1Binding = DataBindingUtil.setContentView(this,
                R.layout.temp1);

        //Set registration layout...
        setContentView(R.layout.registration);
        mVerificationResult = false;

        //Code to find contents displayed on registration screen...
        mRegistration = (Button) findViewById(R.id.Registration);
        mVerify = (Button) findViewById(R.id.verify);
        mPhoneNumber = (EditText) findViewById(R.id.phone_number);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mCountryCode = (EditText) findViewById(R.id.countryCode);
        mMessage1 = (TextView) findViewById(R.id.Message1);
        mMessage2 = (TextView) findViewById(R.id.Message2);
        mNext = (Button) findViewById(R.id.next);

        //Message set to EditText...
        mMessage1.setText("ShoppingPad app will send a one time SMS message to " +
                "verify your phone number.");
        mMessage2.setText("Please confirm your country code and enter mobile " +
                "number.");

        //Click event of number verification...
        mRegistration.setOnClickListener(new View.OnClickListener() {
            String nm, no;

            @Override
            public void onClick(View v) {
                mVerifyNumberFormat = new VerifyNumberFormat();
                //Get mobile number from EditText...
                no = String.valueOf(mPhoneNumber.getText());
                mPhoneNo = no;

                //Get result of number format
                mVerificationResult = mVerifyNumberFormat.verifyNumberFormat(no);

                //If format is correct then...
                if (mVerificationResult) {
                    mRegistrationVMHandler = new RegistrationViewModelHandler();
                    //Set set Otp layout...
                    setContentView(R.layout.received_otp);
                    EditText textV1 = (EditText) findViewById(R.id.OTP);

                    mRegistrationVMHandler.setOtp(textV1, no, getContext());
                    mVerify = (Button) findViewById(R.id.verify);

                    //On click event of verify OTP...
                    mVerify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //Set layout of Insert name...
                            setContentView(R.layout.name_insert);
                            mNameEditText = (EditText) findViewById(R.id.name);
                            mMessage3 = (TextView) findViewById(R.id.Message3);
                            mMessage3.setText("Please provide your name and optional" +
                                    " profile photo");
                            mNext = (Button) findViewById(R.id.next);

                            //Set profile picture...
                            mProfilePic = (ImageView) findViewById(R.id.profilePic);
                            mProfilePic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onRegImageBtnClicked(v);
                                }
                            });

                            //On click event of next button to proceed...
                            mNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    nm = String.valueOf(mNameEditText.getText());

                                    //Pass user name and mobile number to data binding...
                                    User user = new User(nm, no);
                                    temp1Binding.setUser(user);

                                    //Get encoded image...
                                    mEncodedImage = getStringImage(mImagePath);

                                    //Call async task...
                                    new RegistrationAsync().execute(user.
                                                    getmMobileNo(), user.getmUserName(),
                                            String.valueOf(mEncodedImage));
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    //This method returns image file into bytes format...
    public byte[] getStringImage(String mImagePath){

        //Prepare file to be converted...
        File file = new File(mImagePath);
        byte[] bytes = new byte[1024*8];
        try {
            InputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int bytesRead =0;

            //Read all bytes until inputStream gets -1
            while ((bytesRead = inputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, bytesRead);
                Log.e("reading", "" + bytes);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    //Code to crop and set image to image view...
    public void onRegImageBtnClicked(View view) {
        //Code to select only images from gallery...
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            mImagePath = getPath(selectedImageUri);
            mBitmap = BitmapFactory.decodeFile(mImagePath);
            mProfilePic.setImageBitmap(mBitmap);
        }
    }

    //Returns actual path of an image...
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //AsyncTask call to get response and print that response from server...
    class RegistrationAsync extends AsyncTask<String, String, String> {

        String mResponse;

        @Override
        protected String doInBackground(String... params) {
            //Call method and get response from server...
            mResponse = mRegistrationVMHandler.setUserData(params[0], params[1],
                    params[2]);
            return mResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(RegistrationView.this, "" + mResponse, Toast.LENGTH_SHORT)
                    .show();
            //Comment to start next activity...
            startActivity(new Intent(RegistrationView.this,
                    ContentListView.class));
        }
    }
}