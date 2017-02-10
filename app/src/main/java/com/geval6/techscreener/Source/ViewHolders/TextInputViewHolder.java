package com.geval6.techscreener.Source.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.geval6.techscreener.R;

public class TextInputViewHolder extends RecyclerView.ViewHolder {

    public TextView textInputLayout;
    public EditText editText;


    public TextInputViewHolder(View itemView) {
        super(itemView);

        textInputLayout = (TextView) itemView.findViewById(R.id.textview);
        editText = (EditText) itemView.findViewById(R.id.edittext);
    }
}

