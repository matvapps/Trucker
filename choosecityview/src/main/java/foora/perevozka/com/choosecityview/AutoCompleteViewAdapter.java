package foora.perevozka.com.choosecityview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class AutoCompleteViewAdapter extends ArrayAdapter<SpinnerItem> {

    public static final String TAG = AutoCompleteViewAdapter.class.getSimpleName();

    private Context context;
    private List<SpinnerItem> items, filteredItems, itemsAll;
    private ItemsFilter filter;

    private Callback listener;

    private int selectedItemPos = -1;

    public AutoCompleteViewAdapter(Context context) {
        super(context, R.layout.item_dropdown, R.id.textView);

        this.context = context;
        this.items = new ArrayList<>();
        itemsAll = new ArrayList<>();
        filteredItems = new ArrayList<>();
    }

    public void setItems(List<SpinnerItem> items) {
        this.items.clear();
        this.items.addAll(items);
        itemsAll.clear();
        itemsAll.addAll(items);
        filteredItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public SpinnerItem getItem(int position) {

        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.item_dropdown, parent, false);

            TextView textView = view.findViewById(R.id.textView);
            textView.setText(items.get(position).getName());
        }
        SpinnerItem item = items.get(position);
        if (item != null) {
            TextView textView = view.findViewById(R.id.textView);
            if (textView != null) {
                textView.setText(item.getName());
            }

        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new ItemsFilter();

        return filter;
    }
    private class ItemsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (listener != null)
                listener.onDataChanged();

            if (constraint == null || constraint.length() == 0) {
                results.values = itemsAll;
                results.count = itemsAll.size();
            }
            else {
                ArrayList<SpinnerItem> filteredContacts = new ArrayList<SpinnerItem>();

                for (SpinnerItem c : itemsAll) {
                    if (c.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())) {

                        filteredContacts.add(c);
                    }
                }

                results.values = filteredContacts;
                results.count = filteredContacts.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (ArrayList<SpinnerItem>) results.values;
            notifyDataSetChanged();
        }
    }


    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public int getSelectedItemPos() {
        return selectedItemPos;
    }

    public interface Callback {
        void onDataChanged();
    }
}
