package com.gretel.zingo.view.fragments;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gretel.zingo.R;

public abstract class MainFragment extends Fragment {

    private TextView myReportNumber;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(getLayout(),container,false);

        myReportNumber = view.findViewById(R.id.reports_number);
        Button mainButton = view.findViewById(R.id.main_button);

        mainButton.setOnClickListener(getButtonListener());

        return view;
    }

    protected abstract View.OnClickListener getButtonListener();

    protected abstract int getLayout();

    protected void startCountAnimation(int count) {
        ValueAnimator animator = ValueAnimator.ofInt(count);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                myReportNumber.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }
}
