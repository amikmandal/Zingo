package com.gretel.anticorruption.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gretel.anticorruption.R;
import com.gretel.anticorruption.model.Report.Report;
import com.gretel.anticorruption.util.FirebaseManager;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder>{

    private ArrayList<Report> myReports = new ArrayList<Report>();
    private Context myContext;

    private FirebaseManager myFirebaseManager;

    public ReportAdapter(List<Report> data, Context context){
        myReports.addAll(data);
        myContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.holder_report,viewGroup,false);
        myFirebaseManager = new FirebaseManager("reports",myContext);
        return new ReportAdapter.ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        setText(viewHolder,position);
        viewHolder.myUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFirebaseManager.update(myReports.get(position),true);
                setText(viewHolder, position);
            }
        });
        viewHolder.myDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFirebaseManager.update(myReports.get(position),false);
                setText(viewHolder, position);
            }
        });
    }

    private void setText(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.myOfficerText.setText("Officer: "+myReports.get(position).getOfficer());
        viewHolder.myAuthorityText.setText("Of: "+myReports.get(position).getAuthority());
        viewHolder.myPlaceText.setText("Location: "+myReports.get(position).getPlace());
        viewHolder.myReportText.setText(myReports.get(position).getReport());
        viewHolder.myDateText.setText(myReports.get(position).getDate());
        viewHolder.myAuthorText.setText("By "+myReports.get(position).getAuthor());
        viewHolder.myUpText.setText(myReports.get(position).getUp().toString());
        viewHolder.myDownText.setText(myReports.get(position).getDown().toString());
    }

    @Override
    public int getItemCount() {
        return myReports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView myOfficerText;
        TextView myAuthorityText;
        TextView myPlaceText;
        TextView myReportText;
        TextView myDateText;
        TextView myAuthorText;
        TextView myUpText;
        TextView myDownText;
        Button myUpButton;
        Button myDownButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myOfficerText=itemView.findViewById(R.id.text_officer);
            myAuthorityText=itemView.findViewById(R.id.text_authority);
            myPlaceText=itemView.findViewById(R.id.text_place);
            myReportText=itemView.findViewById(R.id.text_report);
            myDateText=itemView.findViewById(R.id.text_date);
            myAuthorText=itemView.findViewById(R.id.text_author);
            myUpText=itemView.findViewById(R.id.text_up);
            myDownText=itemView.findViewById(R.id.text_down);
            myUpButton=itemView.findViewById(R.id.button_up);
            myDownButton=itemView.findViewById(R.id.button_down);
        }
    }
}
