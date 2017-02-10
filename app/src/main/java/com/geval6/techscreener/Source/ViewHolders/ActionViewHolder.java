package com.geval6.techscreener.Source.ViewHolders;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.geval6.techscreener.R;

public class ActionViewHolder extends RecyclerView.ViewHolder {

    public AppCompatButton button;

    public ActionViewHolder(View itemView) {
        super(itemView);
        button = (AppCompatButton) itemView.findViewById(R.id.signinButton);
    }
}
