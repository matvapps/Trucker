package com.foora.perevozkadev.utils.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;

import java.util.List;

/**
 * Created by Alexandr.
 */
public class SpinnerArrayAdapter extends ArrayAdapter<String> {

    private List<String> items;
    private LayoutInflater inflater;
    private boolean useCardView;

    public SpinnerArrayAdapter(Context context, List<String> objects, boolean useCardView) {
        super(context, 0, objects);

        inflater = LayoutInflater.from(context);
        items = objects;
        this.useCardView = useCardView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, R.layout.item_spinner_dropdown, parent);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (useCardView)
                return createItemView(position, R.layout.custom_spinner, parent);
            else
                return createItemView(position, R.layout.custom_spinner_no_card_view, parent);
    }

    private View createItemView(int position, int viewId, ViewGroup parent) {

        View view = inflater.inflate(viewId, parent, false);
        view.setPadding(0, 0, 0, 0);

        TextView text = view.findViewById(R.id.textView);
        View divider = view.findViewById(R.id.divider);

        String item = items.get(position);
        text.setText(item);

        if (divider != null)
            if (position == items.size() - 1)
                divider.setVisibility(View.INVISIBLE);
            else
                divider.setVisibility(View.VISIBLE);

        return view;
    }
}
