package com.gretel.anticorruption.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gretel.anticorruption.R;
import com.gretel.anticorruption.model.Chat.StringText;
import com.gretel.anticorruption.model.Chat.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.gretel.anticorruption.view.adapters.ChatAdapter.PrevTextType.FINAL;
import static com.gretel.anticorruption.view.adapters.ChatAdapter.PrevTextType.INITIAL;
import static com.gretel.anticorruption.view.adapters.ChatAdapter.PrevTextType.NONE;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    enum PrevTextType {
        NONE,INITIAL,FINAL
    }

    private HashMap<String,String> myProfilePhotos = new HashMap<>();

    private ArrayList<Date> myDates = new ArrayList<>();
    private Context myContext;
    private String myCurrSender;
    private String myUser;
    private PrevTextType myPrevTextType;
    private LinearLayout myCurrLinearLayout;
    private CircleImageView myCurrProfilePhoto;
    private TextView myPrevTextView;
    private Text myCurrentText;

    public ChatAdapter(ArrayList<ArrayList<Text>> textSets, String userID, Context context){
        myCurrSender = "";
        myPrevTextType = FINAL;
        for(ArrayList<Text> textSet: textSets){
            for(Text text: textSet){
                updateChat(text);
            }
        }
        myUser = userID;
        myContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.holder_chat,viewGroup,false);
        return new ChatAdapter.ViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        myCurrLinearLayout = viewHolder.linearLayout;
        myCurrProfilePhoto = viewHolder.profilePhoto;

        Picasso.get()
                .load(myProfilePhotos.get(myCurrSender))
                .into(myCurrProfilePhoto);

        viewHolder.textDate.setText(myCurrentText.getDate().toString());

        if(myCurrentText instanceof StringText){
            addTextView(myCurrentText);
        }
    }

    @Override
    public int getItemCount() {
        return myDates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profilePhoto;
        TextView textDate;
        LinearLayout linearLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linear_layout_text);
            profilePhoto=itemView.findViewById(R.id.profile_photo_sender);
            textDate=itemView.findViewById(R.id.sender_date);
        }
    }

    public void updateChat(Text text) {
        if(myCurrSender.equals(text.getSender())){
            if(text instanceof StringText){
                addTextView(text);
            }
        } else {
            addTextSetToList(text);
            this.notifyDataSetChanged();
        }

    }

    private void addTextSetToList(Text newText) {
        myPrevTextType = NONE;
        myCurrentText = newText;
        String newSender = newText.getSender();
        myCurrSender = newSender;
        if(!myProfilePhotos.containsKey(newSender)){
            if(!myUser.equals(newText.getSender()))
                myProfilePhotos.put(newSender,"https://cdn.shopify.com/s/files/1/1061/1924/products/Woman_Saying_Hi_Emoji_Icon_ios10_grande.png?v=1542436004");
            else
                myProfilePhotos.put(newSender,"https://openclipart.org/image/2400px/svg_to_png/263304/Checkered1.png");
        }
        myDates.add(newText.getDate());
    }

    private void addTextView(Text text) {

        int textNormal, textInitial, textMiddle, textLast, textAlignment;
        TextView toAdd = new TextView(myContext);
        toAdd.setText((String) text.getMessage());
        toAdd.setPadding(24,8,24,8);
        toAdd.setTextSize(16);

        if(!myUser.equals(text.getSender())){
            textNormal = R.drawable.text_user;
            textInitial = R.drawable.text_user_initial;
            textMiddle = R.drawable.text_user_middle;
            textLast = R.drawable.text_user_last;
            textAlignment = Gravity.END;
            myCurrProfilePhoto.setVisibility(View.GONE);
        } else {
            textNormal = R.drawable.text_other;
            textInitial = R.drawable.text_other_initial;
            textMiddle = R.drawable.text_other_middle;
            textLast = R.drawable.text_other_last;
            textAlignment = Gravity.START;
        }

        switch (myPrevTextType) {
            case NONE:
                toAdd.setBackgroundResource(textNormal);
                myPrevTextType = INITIAL;
                break;
            case INITIAL:
                myPrevTextView.setBackgroundResource(textInitial);
                toAdd.setBackgroundResource(textLast);
                myPrevTextType = FINAL;
                break;
            case FINAL:
                myPrevTextView.setBackgroundResource(textMiddle);
                toAdd.setBackgroundResource(textLast);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(2,2,2,2);
        params.gravity = textAlignment;
        toAdd.setLayoutParams(params);
        myCurrLinearLayout.addView(toAdd);
        myPrevTextView = toAdd;
    }
}
