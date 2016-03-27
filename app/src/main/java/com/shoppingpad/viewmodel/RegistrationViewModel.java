package com.shoppingpad.viewmodel;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by bridgelabz3 on 21/3/16.
 */
public class RegistrationViewModel {
    TextView message1,message2;
    EditText phone_number,code1;
    Spinner spinner;
    Button registration,verify;
    String message;
    String phoneNo;
    int code = 0;
}
