package foora.perevozka.com.choosecityview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import foora.perevozka.com.choosecityview.data.db.model.City;
import foora.perevozka.com.choosecityview.data.db.model.Country;
import foora.perevozka.com.choosecityview.data.db.model.Region;
import foora.perevozka.com.choosecityview.ui.ChooseCityMvpView;
import foora.perevozka.com.choosecityview.ui.ChooseCityPresenter;

/**
 * Created by Alexandr.
 */
public class ChooseCityView extends FrameLayout implements ChooseCityMvpView {

    public static final String TAG = ChooseCityView.class.getSimpleName();

    private ChooseCityPresenter presenter;

    private AppCompatAutoCompleteTextView countryPicker;
    private AppCompatAutoCompleteTextView regionPicker;
    private AppCompatAutoCompleteTextView cityPicker;

    private TextView titleView;

    private AutoCompleteViewAdapter countryAdapter;
    private AutoCompleteViewAdapter regionAdapter;
    private AutoCompleteViewAdapter cityAdapter;

    private int countrySelectedIndex;
    private int regionSelectedIndex;
    private int citySelectedIndex;

    private String country;
    private String region;
    private String city;

    private String title;

    private Callback listener;

    public ChooseCityView(@NonNull Context context) {
        super(context);
        init();
    }

    public ChooseCityView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getSyleableAttrs(attrs, context);
        init();
    }

    public ChooseCityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getSyleableAttrs(attrs, context);
        init();
    }

    private void getSyleableAttrs(AttributeSet attributeSet, Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ChooseCityView);

        title = typedArray.getString(
                R.styleable.ChooseCityView_ccv_title);

        typedArray.recycle();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void init() {
        presenter = new ChooseCityPresenter(getContext());
        presenter.onAttach(this);

        View view = inflate(getContext(), R.layout.choose_city_view, this);

        countryPicker = view.findViewById(R.id.country_picker);
        regionPicker = view.findViewById(R.id.region_picker);
        cityPicker = view.findViewById(R.id.city_picker);
        titleView = view.findViewById(R.id.text);

        titleView.setText(title);

        regionPicker.setEnabled(false);
        cityPicker.setEnabled(false);

        countryAdapter = new AutoCompleteViewAdapter(getContext());
        regionAdapter = new AutoCompleteViewAdapter(getContext());
        cityAdapter = new AutoCompleteViewAdapter(getContext());

        countryPicker.setThreshold(1);
        regionPicker.setThreshold(1);
        cityPicker.setThreshold(1);

        countryPicker.setAdapter(countryAdapter);
        regionPicker.setAdapter(regionAdapter);
        cityPicker.setAdapter(cityAdapter);


        countryPicker.setOnItemClickListener((adapterView, view1, i, l) -> {
            countryPicker.setText(countryAdapter.getItem(i).getName());
//            countrySelectedIndex = i;
            country = countryAdapter.getItem(i).getName();
            if (listener != null)
                listener.onCountryChanged(countryAdapter.getItem(i).getName());
            countryPicker.setActivated(true);
            regionPicker.setEnabled(true);
            presenter.getRegionsInCountry(countryAdapter.getItem(i).getId());
        });

        regionPicker.setOnItemClickListener((adapterView, view1, i, l) -> {
//            regionSelectedIndex = i;
            region = regionAdapter.getItem(i).getName();
            regionPicker.setText(regionAdapter.getItem(i).getName());
            if (listener != null)
                listener.onRegionChanged(regionAdapter.getItem(i).getName());
            regionPicker.setActivated(true);
            cityPicker.setEnabled(true);
            presenter.getCitiesInRegions(regionAdapter.getItem(i).getId());

        });

        cityPicker.setOnItemClickListener((adapterView, view1, i, l) -> {
//            citySelectedIndex = i;
            city = cityAdapter.getItem(i).getName();
            cityPicker.setText(cityAdapter.getItem(i).getName());
            cityPicker.setActivated(true);
            if (listener != null)
                listener.onCityChanged(cityAdapter.getItem(i).getName());

        });

        presenter.getCountries();

    }

    private List<SpinnerItem> fromObjToSpinnerItem(List items) {
        List<SpinnerItem> result = new ArrayList<>();

        if (items.isEmpty())
            return result;

        if (items.get(0) instanceof Country) {
            for (Object item : items) {
                Country country = (Country) item;

                result.add(new SpinnerItem(country.getName(), country.getId()));
            }
        } else if (items.get(0) instanceof Region) {
            for (Object item : items) {
                Region region = (Region) item;

                result.add(new SpinnerItem(region.getName(), region.getId()));
            }
        } else if (items.get(0) instanceof City) {
            for (Object item : items) {
                City city = (City) item;

                result.add(new SpinnerItem(city.getName(), city.getId()));
            }
        }

        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        titleView.setText(title);
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    @Override
    public void onGetCountries(List<Country> countries) {
        countryAdapter.setItems(fromObjToSpinnerItem(countries));
    }

    @Override
    public void onGetRegions(List<Region> regions) {
        regionAdapter.setItems(fromObjToSpinnerItem(regions));
    }

    @Override
    public void onGetCities(List<City> cities) {
        cityAdapter.setItems(fromObjToSpinnerItem(cities));
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }


    public interface Callback {
        void onCountryChanged(String name);
        void onRegionChanged(String name);
        void onCityChanged(String name);
    }

}
