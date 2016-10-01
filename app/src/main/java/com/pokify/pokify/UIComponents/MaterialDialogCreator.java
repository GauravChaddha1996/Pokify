package com.pokify.pokify.UIComponents;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokify.pokify.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 28/7/16.
 */
public class MaterialDialogCreator implements View.OnClickListener {

    Context mContext;
    OnClickCallBack mOnClickCallBack;
    Boolean shouldEdit = false;
    List<String> mStringList;

    public MaterialDialogCreator(Context context, OnClickCallBack onClickCallBack) {
        mContext = context;
        mOnClickCallBack = onClickCallBack;
        initListData();
    }

    private void initListData() {
        mStringList = new ArrayList<>();
        mStringList.add("Grass");
        mStringList.add("Electric");
        mStringList.add("Water");
        mStringList.add("Fire");
        mStringList.add("Flying");
        mStringList.add("Poison");
        mStringList.add("Psychic");
        mStringList.add("Ground");
        mStringList.add("Fairy");
        mStringList.add("Fighting");
        mStringList.add("Dragon");
        mStringList.add("Ghost");
        mStringList.add("Bug");
    }

    @Override
    public void onClick(final View view) {
        if (shouldEdit) {
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            AlertDialog.Builder alerttype = new AlertDialog.Builder(mContext);
            final EditText edittext = new EditText(mContext);
            TextView temp = (TextView) view;
            switch (view.getId()) {
                case R.id.pokemon_name:
                    alert.setTitle("Enter your pokemon name");
                    edittext.setText(temp.getText().toString());
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                    break;
                case R.id.pokemon_hp:
                    alert.setTitle("Enter your pokemon hp");
                    edittext.setText(temp.getText().toString().substring(0, temp.getText().length() - 3));
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                    break;
                case R.id.pokemon_type:
                    alerttype.setTitle("Select your pokmeon type");
                    View typeListView = LayoutInflater.from(mContext).inflate(R.layout.typeview, null);
                    CommonAdapter<String> commonAdapter = new CommonAdapter<>(new CommonAdapter.OnGetViewListener<String>() {
                        @Override
                        public View getView(View convertView, String item, int position) {
                            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_imagepicker, null);
                            ((TextView) convertView.findViewById(R.id.dialogListText)).setText(item);
                            ((ImageView) convertView.findViewById(R.id.dialogListImage)).
                                    setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                                    mContext.getResources().getIdentifier("type_" + item.toLowerCase(), "drawable",
                                    mContext.getPackageName()))
                            );
                            return convertView;
                        }
                    });
                    commonAdapter.setList(mStringList);
                    alerttype.setAdapter(commonAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mOnClickCallBack.onPress(view, mStringList.get(i).toLowerCase());
                        }
                    });
                    break;
                case R.id.pokemon_weight:
                    alert.setTitle("Enter your pokemon weight");
                    edittext.setText(temp.getText().toString().substring(0, temp.getText().length() - 4));
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                    break;
                case R.id.pokemon_height:
                    alert.setTitle("Enter your pokemon height");
                    edittext.setText(temp.getText().toString().substring(0, temp.getText().length() - 5));
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                    break;
                case R.id.pokemon_desc:
                    alert.setTitle("Enter your pokemon description");
                    edittext.setSingleLine(false);
                    edittext.setText(temp.getText().toString());
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(150)});
                    break;
                case R.id.pokemon_level:
                    alert.setTitle("Enter your pokemon experience");
                    edittext.setText(temp.getText().toString().substring(4));
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
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
            if (view.getId() != R.id.pokemon_type) {
                alert.show();
            } else {
                alerttype.show();
            }
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
