package com.example.quizappadmin.DataAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quizappadmin.Model.Institute;
import com.example.quizappadmin.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class InstituteListAdapter extends  RecyclerView.Adapter<InstituteListAdapter.ViewHolder>{

    List<Institute> mInstitutes;

    public InstituteListAdapter(List<Institute> institutes) {
        mInstitutes = institutes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.institute_list_item, parent, false);
        InstituteListAdapter.ViewHolder viewHolder = new InstituteListAdapter.ViewHolder (listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Institute institute = mInstitutes.get (position);
        holder.code.setText (institute.getInstituteCode ());
        holder.email.setText (institute.getEmail ());
        holder.name.setText (institute.getName ());
    }

    @Override
    public int getItemCount() {
        return mInstitutes.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView code, email,name;
        public CardView card;
        public ViewHolder(View itemView) {
            super(itemView);

            card = itemView.findViewById (R.id.card_institute_item);
            code = itemView.findViewById (R.id.tvCode_institute);
            email = itemView.findViewById (R.id.tvEmail_institute);
            name  = itemView.findViewById (R.id.tvName_institute);
        }
    }
}
