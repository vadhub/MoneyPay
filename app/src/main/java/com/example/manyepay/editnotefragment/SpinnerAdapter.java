package com.example.manyepay.editnotefragment;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
            row = super.getView(position, convertView, parent);
        if (parent.getWidth() > 0)
        {
            TextView label = (TextView) row.findViewById(android.R.id.text1);
            label.setTextSize(TypedValue.COMPLEX_UNIT_PX,label.getAutoSizeTextType() );
            label.setPadding(label.getPaddingLeft(), 0, 0, 0);
        }
        return(row);
    }
}
