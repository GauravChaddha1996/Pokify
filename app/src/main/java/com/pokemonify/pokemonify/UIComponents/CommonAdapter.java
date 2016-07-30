package com.pokemonify.pokemonify.UIComponents;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 8/4/16.
 */
public class CommonAdapter<T> extends BaseAdapter {

    private List<T> mListItems = new ArrayList<>();
    private OnGetViewListener<T> mOnGetViewListener;

    public CommonAdapter(OnGetViewListener<T> onGetViewListener) {
        mOnGetViewListener = onGetViewListener;
    }

    public static <T> CommonAdapter<T> createAdapter(OnGetViewListener<T> onGetViewListener) {
        return new CommonAdapter<>(onGetViewListener);
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public T getItem(int position) {
        return mListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setList(List<T> items) {
        if (items == null) {
            return;
        }
        mListItems = items;
    }

    public void addItem(T item) {
        mListItems.add(item);
    }

    public void addItems(List<T> items) {
        mListItems.addAll(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return mOnGetViewListener.getView(convertView, getItem(position), position);
    }

    public void remove(int position) {
        mListItems.remove(position);
    }

    public void clear() {
        mListItems.clear();
    }

    public interface OnGetViewListener<T> {
        View getView(View convertView, T item, int position);
    }
}
