package com.example.willow.gitreferenceproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by Willow on 2/28/2018.
 */

public class GitReferenceAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<JsonUtils.GitReference> mDataSource;

    public GitReferenceAdapter(Context context, ArrayList<JsonUtils.GitReference> arrayList) {
        this.mContext = context;
        this.mDataSource = arrayList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       View rowView = mInflater.inflate(R.layout.list_expansion, viewGroup, false);

       TextView command = rowView.findViewById(R.id.command);

       TextView example = rowView.findViewById(R.id.example);

        TextView explanation = rowView.findViewById(R.id.explanation);


        JsonUtils.GitReference gitReference = (JsonUtils.GitReference) getItem(i);
        command.setText("Command: " + gitReference.getCommand());
        example.setText("Example: " + gitReference.getExample());
        explanation.setText("Explanation: " + gitReference.getExplanation());

        return rowView;

    }
}
