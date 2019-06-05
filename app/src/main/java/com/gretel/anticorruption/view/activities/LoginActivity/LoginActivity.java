package com.gretel.anticorruption.view.activities.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gretel.anticorruption.view.activities.FormActivity.UserFormActivity;
import com.gretel.anticorruption.view.activities.MainActivity.AuthorityPrimaryActivity;
import com.gretel.anticorruption.view.activities.MainActivity.UserPrimaryActivity;
import com.gretel.anticorruption.util.FirebaseManager;
import com.gretel.anticorruption.util.JSONParser;
import com.gretel.anticorruption.util.LocalStorage;
import com.gretel.anticorruption.R;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * This Activity implements all tasks related to Login. Currently supporting Facebook.
 * @author Amik Mandal
 */
public class LoginActivity extends AppCompatActivity {

    //frontend variables
    private LoginButton myLoginButton;

    //backend variables
    private CallbackManager myCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        PackageInfo info;
//        try {
//            info = getPackageManager().getPackageInfo("com.gretel.anticorruption", PackageManager.GET_SIGNATURES);
//        } catch (PackageManager.NameNotFoundException e) {
//            System.out.println("bruhhhhh what");
//            e.printStackTrace();
//        }
//        try {
//            System.out.println("bruhh what");
//            info = getPackageManager().getPackageInfo("com.gretel.anticorruption", PackageManager.GET_SIGNATURES);
//            System.out.println("bruhhh what");
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                //String something = new String(Base64.encodeBytes(md.digest()));
//                Log.e("hash key", something);
//            }
//        } catch (PackageManager.NameNotFoundException e1) {
//            Log.e("name not found", e1.toString());
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("no such an algorithm", e.toString());
//        } catch (Exception e) {
//            Log.e("exception 123", e.toString());
//        }

        myCallbackManager = CallbackManager.Factory.create();

        //checks if User is currently Logged In
        checkIfLoggedIn();

        myLoginButton = findViewById(R.id.login_button);

        //get permissions
        myLoginButton.setReadPermissions(Arrays.asList("email","public_profile"));

        // Callback registration
        myLoginButton.registerCallback(myCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                System.out.println("bruhhh what");

                myLoginButton.setVisibility(View.GONE);

                //create Graph Request to get more details from User
                GraphRequest loginGraphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        JSONParser jsonParser = new JSONParser();
                        final JSONObject jsonObject = object;
                        final String id = jsonParser.readID(jsonObject);

                        //get to root of tree where all facebook users are stored.
                        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("user");
                        databaseUsers = databaseUsers.child("facebook");

                        //load everything in that tree
                        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {

                                //check if user is already registered with us
                                if (snapshot.hasChild(id)) {

                                    //load User data
                                    FirebaseManager firebaseManager = new FirebaseManager("user",getApplicationContext());
                                    firebaseManager.getUser("facebook", id);

                                    Intent intent = new Intent(getApplicationContext(), UserPrimaryActivity.class);
                                    startActivity(intent);


                                    //gotta check if Authority

                                } else {

                                    JSONParser jsonParser = new JSONParser();

                                    //if not registered then we need some more details
                                    Intent intent = new Intent(getApplicationContext(), UserFormActivity.class);
                                    intent.putExtras(jsonParser.makePreliminaryUserData(jsonObject,"facebook"));
                                    startActivity(intent);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                return;
                            }
                        });
                    }

                });

                //put the parameters (details) we need from User in a bundle
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,email,first_name,last_name");
                loginGraphRequest.setParameters(parameters);
                loginGraphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast toast = Toast.makeText(getApplicationContext(), "Login Cancelled.", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast toast = Toast.makeText(getApplicationContext(), "Login Error. Please Try Again.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        myCallbackManager.onActivityResult(requestCode,resultCode,data);
    }

    /**
     * Method to check if the User is already logged in. We accomplish by just checking if there is
     * a value to the key id in the local storage since id will have to exist if logged in.
     */
    private void checkIfLoggedIn() {
        LocalStorage localStorage = new LocalStorage(getApplicationContext());

        if(localStorage.checkIfAuthorityPresent()){
            Intent intent = new Intent(getApplicationContext(),AuthorityPrimaryActivity.class);
            startActivity(intent);
        } else if(localStorage.checkIfUserPresent()){
            Intent intent = new Intent(getApplicationContext(), UserPrimaryActivity.class);
            startActivity(intent);
        }
    }

}