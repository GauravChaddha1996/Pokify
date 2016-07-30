package com.pokemonify.pokemonify.UIComponents;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pokemonify.pokemonify.R;

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
            edittext.setText(((TextView)view).getText().toString());
            switch (view.getId()) {
                case R.id.pokemon_name:
                    alert.setTitle("Enter your pokemon name");
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                    break;
                case R.id.pokemon_hp:
                    alert.setTitle("Enter your pokemon hp");
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                    break;
                case R.id.pokemon_type:
                    alert.setTitle("Enter your pokemon type");
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                    break;
                case R.id.pokemon_weight:
                    alert.setTitle("Enter your pokemon weight");
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                    break;
                case R.id.pokemon_height:
                    alert.setTitle("Enter your pokemon height");
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                    break;
                case R.id.pokemon_desc:
                    alert.setTitle("Enter your pokemon description");
                    edittext.setSingleLine(false);
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(120)});
                    break;
                case R.id.pokemon_level:
                    alert.setTitle("Enter your pokemon level");
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                    break;
            }
            alert.setView(edittext);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (!edittext.getText().toString().isEmpty()) {
                        mOnClickCallBack.onPress(view, edittext.getText().toString());
                    }
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
