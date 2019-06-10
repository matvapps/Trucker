package com.github.matvapps.setmapplacesview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.matvapps.setmapplacesview.PlaceAutoCompleteView;
import com.github.matvapps.setmapplacesview.R;
import com.github.matvapps.setmapplacesview.model.Place;
import com.github.matvapps.setmapplacesview.utils.ScreenUtils;
import com.github.matvapps.setmapplacesview.utils.ViewUtils;
import com.github.matvapps.setmapplacesview.view.DrawableRoute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class PlaceArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Place> items;
    private int anchorId = -1;
    private Callback listener;

    public void setItems(List<Place> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Place item) {
        this.items.add(item);
        if (listener != null)
            listener.onItemInserted(items.size() - 1, item);
//        notifyItemInserted(items.size() - 1);
        notifyDataSetChanged();

    }

    public void setAnchorId(int anchorId) {
        this.anchorId = anchorId;
        notifyDataSetChanged();
    }

    public PlaceArrayAdapter() {
        items = new ArrayList<>();
    }

    public Place getItem(int position) {
        return items.get(position);
    }

    public void reset() {
        List<Place> places = new ArrayList<>();
        places.add(new Place());
        setItems(places);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place, viewGroup, false);
        return new PlaceViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PlaceViewHolder placeViewHolder = (PlaceViewHolder) viewHolder;

        Log.d("TAG", "onBindViewHolder: itemCount = " + getItemCount() + ", i = " + i);


        if (getItemCount() == 1) {
            placeViewHolder.drawableRoute.setVisibility(View.INVISIBLE);
        } else {
            placeViewHolder.drawableRoute.setVisibility(View.VISIBLE);
        }

        if (i == getItemCount() - 1)
            placeViewHolder.divider.setVisibility(View.INVISIBLE);
        else
            placeViewHolder.divider.setVisibility(View.VISIBLE);

        if (anchorId != -1)
            placeViewHolder.placeAutoCompleteView.setDropDownAnchor(anchorId);

        placeViewHolder.placeAutoCompleteView.setText(null);

        if (getItem(i).getPlaceText() != null && !getItem(i).getPlaceText().isEmpty()) {
            placeViewHolder.placeAutoCompleteView.setText(getItem(i).getPlaceText());
        }

        placeViewHolder.placeAutoCompleteView.setDropDownVerticalOffset(ViewUtils.dpToPx(8));
        placeViewHolder.placeAutoCompleteView.setDropDownHorizontalOffset(ViewUtils.dpToPx(8));
        placeViewHolder.placeAutoCompleteView.setDropDownHeight(ViewUtils.dpToPx(120));
        placeViewHolder.placeAutoCompleteView.setDropDownBackgroundResource(R.drawable.bg_dropdown);
        placeViewHolder.placeAutoCompleteView.setDropDownWidth(
                ScreenUtils.getScreenWidth(viewHolder.itemView.getContext()) - ViewUtils.dpToPx(16));


        placeViewHolder.placeAutoCompleteView.setOnItemClickListener((parent, view, position, id) -> {
            Place place = (Place) parent.getItemAtPosition(position);
            if (listener != null)
                listener.onItemSelected(i, place);
            items.set(i, place);
            if (getItemCount() == 1)
                addItem(new Place());
            notifyDataSetChanged();
        });

        if (i == 0) {
            placeViewHolder.drawableRoute.setType(DrawableRoute.Type.FIRST_ROUTE);
            placeViewHolder.placeImage.setImageResource(R.drawable.ic_action_clear);
            placeViewHolder.placeImage.setOnClickListener(v -> {
                if (getItemCount() == 1) {
                    items.set(0, new Place());
                    notifyDataSetChanged();
                    return;
                }
                if (listener != null)
                    listener.onItemRemoved(i, getItem(i));
                items.remove(i);
                notifyDataSetChanged();
            });
        }
        else if (i == getItemCount() - 1) {
            placeViewHolder.drawableRoute.setType(DrawableRoute.Type.LAST_ROUTE);
            placeViewHolder.placeImage.setImageResource(R.drawable.ic_action_clear);
            placeViewHolder.placeImage.setOnClickListener(v -> {
                if (getItemCount() == 1) {
                    items.set(0, new Place());
                    notifyDataSetChanged();
                    return;
                }
                if (listener != null)
                    listener.onItemRemoved(i, getItem(i));
                items.remove(i);
                notifyDataSetChanged();
            });
        }
        else {
            placeViewHolder.drawableRoute.setType(DrawableRoute.Type.BASE_ROUTE);
            placeViewHolder.placeImage.setImageResource(R.drawable.ic_action_clear);
            placeViewHolder.placeImage.setOnClickListener(v -> {
                if (getItemCount() == 1) {
                    items.set(0, new Place());
                    notifyDataSetChanged();
                    return;
                }
                if (listener != null)
                    listener.onItemRemoved(i, getItem(i));
                items.remove(i);
                notifyDataSetChanged();
            });
        }

    }

    public void reverseList() {
        List<Place> places = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            places.add(items.get(items.size() - 1 - i));
        }

        setItems(places);
        if (listener != null)
            listener.onReverseList();
    }

    public void clear() {
        List<Place> places = new ArrayList<>();
        places.add(new Place());
        setItems(places);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class PlaceViewHolder extends RecyclerView.ViewHolder {

        PlaceAutoCompleteView placeAutoCompleteView;
        DrawableRoute drawableRoute;
        ImageView placeImage;
        View divider;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            placeAutoCompleteView = itemView.findViewById(R.id.order_id_edtxt);
            drawableRoute = itemView.findViewById(R.id.route_drawable);
            placeImage = itemView.findViewById(R.id.place_image);
            divider = itemView.findViewById(R.id.divider);


        }
    }


    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public interface Callback {
        void onItemInserted(int pos, Place place);

        void onItemRemoved(int pos, Place place);

        void onReverseList();

        void onItemSelected(int pos, Place place);
    }


}
