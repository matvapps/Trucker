package com.foora.perevozkadev.utils.custom.tnvd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.utils.custom.places.Place;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class CargoArrayAdapter extends ArrayAdapter {
    public static final String TAG = CargoArrayAdapter.class.getSimpleName();
    private List<Cargo> dataList;
    private Context mContext;
    private GeoDataClient geoDataClient;
    private int filterType = AutocompleteFilter.TYPE_FILTER_CITIES;

    private Cargo selectedItem = null;

    private CargoArrayAdapter.CustomAutoCompleteFilter listFilter =
            new CargoArrayAdapter.CustomAutoCompleteFilter();

    //    private TextView country;
//    private Spinner placeType;
    private int[] placeTypeValues;

    public CargoArrayAdapter(Context context) {
        super(context, android.R.layout.simple_dropdown_item_1line,
                new ArrayList<Place>());
        mContext = context;
        dataList = new ArrayList<>();
        geoDataClient = Places.getGeoDataClient(mContext, null);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Cargo getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_dropdown_item_1line,
                            parent, false);
        }

        TextView textOne = view.findViewById(android.R.id.text1);
        selectedItem = dataList.get(position);
        textOne.setText(dataList.get(position).getCargoName());

        return view;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class CustomAutoCompleteFilter extends Filter {
        private Object lock = new Object();
        private Object lockTwo = new Object();
        private boolean cargoResults = false;


        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            cargoResults = false;
            final List<Cargo> cargoList = new ArrayList<>();

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = new ArrayList<Place>();
                    results.count = 0;
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                RemoteRepo remoteRepo = new RemoteRepoImpl();
                PrefRepo prefRepo = new PrefRepoImpl(getContext());

                remoteRepo.searchCargoTypes(searchStrLowerCase)
                        .enqueue(new Callback<List<Cargo>>() {
                            @Override
                            public void onResponse(Call<List<Cargo>> call, Response<List<Cargo>> response) {
                                if (response.body() != null)
                                    cargoList.addAll(response.body());

                                Log.d(TAG, "onResponse: " + cargoList);

                                cargoResults = true;
                                synchronized (lockTwo) {
                                    lockTwo.notifyAll();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Cargo>> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage());
                                cargoResults = true;
                                synchronized (lockTwo) {
                                    lockTwo.notifyAll();
                                }
                            }
                        });

                while (!cargoResults) {
                    synchronized (lockTwo) {
                        try {
                            lockTwo.wait();
                        } catch (InterruptedException e) {

                        }
                    }
                }

                results.values = cargoList;
                results.count = cargoList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                dataList = (ArrayList<Cargo>) results.values;
            } else {
                dataList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

        private Task<AutocompletePredictionBufferResponse> getAutoCompletePlaces(String query) {
            //create autocomplete filter using data from filter Views
            AutocompleteFilter.Builder filterBuilder = new AutocompleteFilter.Builder();
//            filterBuilder.setCountry(country.getText().toString());
            filterBuilder.setTypeFilter(filterType);

            Task<AutocompletePredictionBufferResponse> results =
                    geoDataClient.getAutocompletePredictions(query, null,
                            filterBuilder.build());
            return results;
        }

    }

    public Cargo getSelectedItem() {
        return selectedItem;
    }
}