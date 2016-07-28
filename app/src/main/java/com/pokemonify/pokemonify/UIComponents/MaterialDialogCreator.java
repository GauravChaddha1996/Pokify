package com.pokemonify.pokemonify.UIComponents;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

/**
 * Created by gaurav on 28/7/16.
 */
public class MaterialDialogCreator implements View.OnClickListener {

    Context mContext;
    OnClickCallBack mOnClickCallBack;
    Boolean shouldEdit = false;

    public MaterialDialogCreator(Context context, OnClickCallBack onClickCallBack) {
        mContext = context;
        mOnClickCallBack = onClickCallBack;
    }

    @Override
    public void onClick(final View view) {
        if (shouldEdit) {
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            final EditText edittext = new EditText(mContext);
            alert.setTitle("Enter your pokemon name");
            alert.setView(edittext);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    mOnClickCallBack.onPress(view, edittext.getText().toString());
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
            alert.show();
        }
    }

    public Boolean getShouldEdit() {
        return shouldEdit;
    }

    public void setShouldEdit() {
        this.shouldEdit = !shouldEdit;
    }

    public interface OnClickCallBack {
        public void onPress(View v, String s);
    }
}
