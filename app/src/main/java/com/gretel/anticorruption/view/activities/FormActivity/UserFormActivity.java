package com.gretel.anticorruption.view.activities.FormActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.gretel.anticorruption.model.FormData.FormData;
import com.gretel.anticorruption.model.FormData.UserForm;
import com.gretel.anticorruption.R;
import com.hbb20.CountryCodePicker;

import static com.gretel.anticorruption.view.activities.FormActivity.FormActivity.FormType.USER_FORM;

public class UserFormActivity extends FormActivity {

    private CountryCodePicker myCcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        myFormType = USER_FORM;

        myInfo = findViewById(R.id.form_input);
        myCcp = findViewById(R.id.ccp);


        UserForm userForm = new UserForm(getApplicationContext());

        //get Country Code
//        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
//        String countryCodeValue = tm.getNetworkCountryIso();

        Bundle userBundle = getIntent().getExtras();

        Integer i=0;
        if(userBundle.containsKey("index"))
            i = Integer.parseInt(userBundle.getString("index"));

        myCcp.setVisibility(View.GONE);
        myInfo.setHint(userForm.getRequirement(i));

        if(myInfo.getHint().equals(userForm.getPhoneNumber())) {
            myCcp.setVisibility(View.VISIBLE);
            myCcp.setDefaultCountryUsingNameCode(myCcp.getDefaultCountryNameCode());
            myCcp.setNumberAutoFormattingEnabled(true);
            myInfo.setInputType(InputType.TYPE_CLASS_PHONE);
            myInfo.setSingleLine();
        }

        makeNextButton();
    }


    @Override
    protected int doNecessary(FormData tempFormData, Integer index, Bundle userData) {

        UserForm tempUserForm = (UserForm) tempFormData;

        if(tempUserForm.getPhoneNumber()==tempUserForm.getRequirement(index)){
            userData.putString(index.toString(),"+"+myCcp.getSelectedCountryCode()+" "+myInfo.getText().toString());
        } else {
            userData.putString(index.toString(), myInfo.getText().toString());
        }

        return 0;
    }
}
