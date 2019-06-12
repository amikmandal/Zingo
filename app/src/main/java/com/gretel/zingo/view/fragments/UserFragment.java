//package com.gretel.zingo.view.fragments;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AlertDialog;
//import android.view.ContextThemeWrapper;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ViewSwitcher;
//
//import com.gretel.zingo.model.Agent.User;
//import com.gretel.zingo.util.FirebaseManager;
//import com.gretel.zingo.util.LocalStorage;
//import com.gretel.zingo.R;
//import com.gretel.zingo.util.EditButtonListener;
//import com.squareup.picasso.Picasso;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import static com.gretel.zingo.view.fragments.UserFragment.FragmentMode.EDIT;
//import static com.gretel.zingo.view.fragments.UserFragment.FragmentMode.VIEW;
//
///**
// * This fragment helps display the information of the logged in user.
// * @author Amik Mandal
// */
//public class UserFragment extends Fragment implements EditButtonListener {
//
//    public enum FragmentMode{
//        VIEW,EDIT
//    }
//
//    private ViewSwitcher[] myViewSwitchers;
//    private TextView[] myTextViews;
//    private EditText[] myEditTexts;
//
//    private static final int[] SWITCHER_LAYOUTS = {R.id.view_switcher_name,R.id.view_switcher_email,R.id.view_switcher_phone_number};
//    private static final int[] TEXT_VIEW_LAYOUTS = {R.id.user_name,R.id.user_email,R.id.user_phone_number};
//    private static final int[] EDIT_TEXT_LAYOUTS = {R.id.edit_user_name,R.id.edit_user_email,R.id.edit_user_phone_number};
//
//    private Button mySaveButton;
//    private Button myCancelButton;
//    private Button myToolbarButton;
//    private CircleImageView myProfilePhoto;
//
//    private FragmentMode myCurrentFragmentMode;
//    private User myNewUser;
//    private Context myContext;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        final View view = inflater.inflate(R.layout.fragment_profile,container,false);
//
//        myContext = getActivity().getApplicationContext();
//
//        myProfilePhoto = view.findViewById(R.id.user_profile_photo);
//
//        myViewSwitchers = new ViewSwitcher[SWITCHER_LAYOUTS.length];
//        myTextViews = new TextView[SWITCHER_LAYOUTS.length];
//        myEditTexts = new EditText[SWITCHER_LAYOUTS.length];
//
//        for(int i=0; i<SWITCHER_LAYOUTS.length; i++){
//            myViewSwitchers[i] = view.findViewById(SWITCHER_LAYOUTS[i]);
//            myTextViews[i] = view.findViewById(TEXT_VIEW_LAYOUTS[i]);
//            myEditTexts[i] = view.findViewById(EDIT_TEXT_LAYOUTS[i]);
//        }
//
//        mySaveButton = view.findViewById(R.id.save_button);
//        myCancelButton = view.findViewById(R.id.cancel_button);
//        mySaveButton.setVisibility(View.GONE);
//        myCancelButton.setVisibility(View.GONE);
//        mySaveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleSaveButton(view);
//            }
//        });
//        myCancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleCancelButton(view);
//            }
//        });
//
//        myCurrentFragmentMode = VIEW;
//
//        return view;
//    }
//
//    private void handleSaveButton(View view) {
//        if(changesMade()){
//            hideKeyBoard(view);
//            scrollUp(view);
//            switchToViewMode();
//            myToolbarButton.setVisibility(View.VISIBLE);
//            saveProfile(myNewUser);
//            Toast.makeText(myContext,"" + "Your changes have been saved",Toast.LENGTH_SHORT).show();
//        } else {
//            scrollUp(view);
//            switchToEditMode();
//            activateEditText(myEditTexts[0]);
//            Toast.makeText(myContext,"Nothing to update",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void handleCancelButton(final View view) {
//        if(changesMade()){
//            new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog))
//                    .setTitle("Cancel Changes")
//                    .setMessage("Are you sure you want to cancel editing your profile?")
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            hideKeyBoard(view);
//                            switchToViewMode();
//                            myToolbarButton.setVisibility(View.VISIBLE);
//                            setDefault();
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            scrollUp(view);
//                            onEditButtonSelect(myToolbarButton);
//                        }
//                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        }else{
//            hideKeyBoard(view);
//            switchToViewMode();
//            myToolbarButton.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public boolean changesMade() {
//        switchToViewMode();
//
//        LocalStorage localStorage = new LocalStorage(myContext);
//        String firstName = User.makeFirstName(myTextViews[0].getText().toString());
//        String lastName = User.makeLastName(myTextViews[0].getText().toString());
//        String email = myTextViews[1].getText().toString();
//        String number = myTextViews[2].getText().toString();
//
//        User oldUser = localStorage.loadUser();
//        User newUser = new User(oldUser.getDisplayPicture(),firstName,lastName,oldUser.getID(),email,number,oldUser.getLoginType());
//
//        myNewUser = newUser;
//        return !oldUser.equals(newUser);
//    }
//
//    private void scrollUp(View v) {
//        final ScrollView sv = v.findViewById(R.id.user_container);
//        sv.post(new Runnable() {
//            public void run() {
//                sv.fullScroll(sv.FOCUS_UP);
//            }
//        });
//    }
//
//    private void setDefault() {
//        LocalStorage localStorage = new LocalStorage(getActivity().getApplicationContext());
//        User u = localStorage.loadUser();
//
//        myTextViews[0].setText(u.getName());
//        myTextViews[1].setText(u.getEmail());
//        myTextViews[2].setText(u.getNumber());
//
//        Picasso.get().load(u.getDisplayPicture()).into(myProfilePhoto);
//    }
//
//    private void hideKeyBoard(View view) {
//        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }
//
//    private void showKeyBoard(View v) {
//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
//    }
//
//    private void saveProfile(User u) {
//        LocalStorage localStorage = new LocalStorage(myContext);
//        FirebaseManager firebaseManager = new FirebaseManager("user", myContext);
//        firebaseManager.editUser(u);
//        localStorage.editUser(u);
//    }
//
//    @Override
//    public void onStart(){
//        super.onStart();
//        System.out.println("order check ---> UserFragment-onStart");
//        setDefault();
//
//    }
//
//    @Override
//    public void onEditButtonSelect(Button toolbarButton) {
//
//        myToolbarButton = toolbarButton;
//
//        switchToEditMode();
//
//        //set Name to be editable by default
//        activateEditText(myEditTexts[0]);
//    }
//
//    private void switchToViewMode(){
//
//        for(int i=0; i<SWITCHER_LAYOUTS.length; i++){
//            switchInTextView(myViewSwitchers[i],myTextViews[i],myEditTexts[i]);
//        }
//
//        mySaveButton.setVisibility(View.GONE);
//        myCancelButton.setVisibility(View.GONE);
//
//        myCurrentFragmentMode = VIEW;
//    }
//
//    private void switchToEditMode(){
//
//        for(int i=0; i<SWITCHER_LAYOUTS.length; i++){
//            switchInEditText(myViewSwitchers[i],myEditTexts[i],myTextViews[i]);
//        }
//
//        myToolbarButton.setVisibility(View.GONE);
//
//        myCurrentFragmentMode = EDIT;
//    }
//
//    private void switchInEditText(ViewSwitcher switcher, EditText editText, TextView textView) {
//        switcher.setDisplayedChild(1);
//        editText.setText(textView.getText());
//        showKeyBoard(editText);
//    }
//
//    private void switchInTextView(ViewSwitcher switcher, TextView textView, EditText editText) {
//        switcher.setDisplayedChild(0);
//        textView.setText(editText.getText().toString());
//    }
//
//    private void activateEditText(EditText editText) {
//
//        editText.setSelection(editText.getText().length());
//        editText.requestFocus();
//        editText.setCursorVisible(true);
//        showKeyBoard(editText);
//
//        mySaveButton.setVisibility(View.VISIBLE);
//        myCancelButton.setVisibility(View.VISIBLE);
//    }
//
//    public FragmentMode getCurrentMode(){
//        return myCurrentFragmentMode;
//    }
//}