package com.gretel.anticorruption.view.activities.FormActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.gretel.anticorruption.R;
import com.gretel.anticorruption.model.Agent.User;
import com.gretel.anticorruption.model.FormData.FormData;
import com.gretel.anticorruption.model.FormData.AuthorityForm;
import com.gretel.anticorruption.model.FormData.UserForm;
import com.gretel.anticorruption.util.LocalStorage;
import com.hbb20.CountryCodePicker;

import static com.gretel.anticorruption.view.activities.FormActivity.FormActivity.FormType.REPAIRER_FORM;

public class AuthorityFormActivity extends FormActivity {

    private String mySpinnerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        CountryCodePicker ccp = findViewById(R.id.ccp);

        myInfo = findViewById(R.id.form_input);
        Spinner mySpinnerInfo = findViewById(R.id.spinner_input);

        myFormType = REPAIRER_FORM;
        Bundle userBundle = getIntent().getExtras();
        if(userBundle == null)
            userBundle = new Bundle();

        Integer i=0;
        if(userBundle.containsKey("index"))
            i = Integer.parseInt(userBundle.getString("index"));

        AuthorityForm authorityForm = new AuthorityForm(getApplicationContext());

        myInfo.setHint(authorityForm.getRequirement(i));
        ccp.setVisibility(View.GONE);
        storeUserInBundle(userBundle);
        makeNextButton();

        if(myInfo.getHint().equals(authorityForm.getAuthorityName())) {
            myInfo.setVisibility(View.GONE);
            setSpinner();
        }

    }

    private void storeUserInBundle(Bundle userBundle) {

        //int size = new UserForm(getApplicationContext()).getRequirementsSize();
        LocalStorage localStorage = new LocalStorage(getApplicationContext());
        User u = localStorage.loadUser();

        Gson gson = new Gson();
        String json = gson.toJson(u);
        userBundle.putString("userData", json);

        getIntent().putExtras(userBundle);
    }

    private void setSpinner() {
        Spinner spinner = findViewById(R.id.spinner_input);
        spinner.setVisibility(View.VISIBLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.agencies, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mySpinnerData = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mySpinnerData = "Select an item";
            }
        });
    }

    @Override
    protected int doNecessary(FormData tempFormData, Integer index, Bundle userData) {

        AuthorityForm tempUserForm = (AuthorityForm) tempFormData;

        if(tempUserForm.getAuthorityName()==tempUserForm.getRequirement(index)){
            if(mySpinnerData.equals("Select an item")){
                new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog))
                        .setTitle("Alert!")
                        .setMessage("The report is missing details. Please complete them.")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ;
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return -1;
            }
            userData.putString(index.toString(),mySpinnerData);
        } else {
            userData.putString(index.toString(), myInfo.getText().toString());
        }
        return 0;
    }
}
