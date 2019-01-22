package com.example.youssefiibrahim.musicsheetgenerationapp.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.youssefiibrahim.musicsheetgenerationapp.Interface.ItemClickListener;
import com.example.youssefiibrahim.musicsheetgenerationapp.R;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txt_name, txt_score;
    private ItemClickListener itemClickListener;
    public RankingViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_name = (TextView) itemView.findViewById(R.id.txt_name);
        txt_score = (TextView) itemView.findViewById(R.id.txt_score);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setTxt_name(TextView txt_name) {
        this.txt_name = txt_name;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onCLick(v, getAdapterPosition(), false);
    }
}
