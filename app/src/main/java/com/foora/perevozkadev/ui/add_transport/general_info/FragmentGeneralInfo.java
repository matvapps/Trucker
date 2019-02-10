package com.foora.perevozkadev.ui.add_transport.general_info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_transport.PhotoListAdapter;
import com.foora.perevozkadev.ui.base.BaseFragment;
import com.foora.perevozkadev.utils.custom.CustomSpinner;
import com.foora.perevozkadev.utils.custom.SpinnerArrayAdapter;
import com.github.matvapps.AppEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class FragmentGeneralInfo extends BaseFragment {

    private static final int PHOTO_REQUEST = 100;

    @BindView(R.id.photo_list)
    RecyclerView photoList;
    @BindView(R.id.transport_category)
    CustomSpinner transportCategorySpinner;
    @BindView(R.id.transport_type)
    CustomSpinner transportTypeSpinner;

    @BindView(R.id.transport_model)
    AppEditText transportModelEdtxt;
    @BindView(R.id.transport_mass_placed)
    AppEditText transportMassPlacedEdtxt;
    @BindView(R.id.transport_no_load_mass)
    AppEditText transportNoLoadMassEdtxt;


    private Callback listener;
    private PhotoListAdapter photoListAdapter;

    private SpinnerArrayAdapter typesAdapter;
    private SpinnerArrayAdapter categoriesAdapter;

    public static FragmentGeneralInfo newInstance() {
        Bundle args = new Bundle();
        FragmentGeneralInfo fragment = new FragmentGeneralInfo();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transport_general_info, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected void setUp(View view) {
        photoListAdapter = new PhotoListAdapter();
        photoList.setLayoutManager(new LinearLayoutManager(getContext()));
        photoList.setAdapter(photoListAdapter);
        photoList.setNestedScrollingEnabled(false);

        List<String> transports = new ArrayList<String>();
        String[] transportArr = getContext().getResources().getStringArray(R.array.transport_types);
        transports.add("Тип транспорта");
        transports.addAll(Arrays.asList(transportArr));

        List<String> categories = new ArrayList<>();
        categories.add("Категория ТС");
        categories.add("C");
        categories.add("C1");
        categories.add("CE");
        categories.add("C1E");
        categories.add("D");
        categories.add("D1");
        categories.add("DE");
        categories.add("D1E");

        typesAdapter = new SpinnerArrayAdapter(getContext(), transports, true);
        categoriesAdapter = new SpinnerArrayAdapter(getContext(), categories, true);

        transportTypeSpinner.setAdapter(typesAdapter);
        transportCategorySpinner.setAdapter(categoriesAdapter);

        transportCategorySpinner.setGravity(Gravity.BOTTOM);
        transportTypeSpinner.setGravity(Gravity.BOTTOM);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        File file;

        if (requestCode == PHOTO_REQUEST && resultCode == Activity.RESULT_OK) {
            final Uri selectedImage = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            file = new File(filePath);
            photoListAdapter.addItem(file);

        } else if (requestCode == PHOTO_REQUEST) {
            onError("Фото не было выбрано");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Callback) getActivity();
    }

    @OnClick(R.id.btn_choose_photo)
    void onClick() {
        getImage();
    }

    @OnClick(R.id.btn_next)
    void onNext() {
        String model = transportModelEdtxt.getText().toString();
        String category = (String) transportCategorySpinner.getSelectedItem();
        String type = (String) transportTypeSpinner.getSelectedItem();
        float massPlaced;
        float noLoadMass;
        List<File> photos = new ArrayList<>(photoListAdapter.getItems());

        if (!model.equals("")) {
            if (!category.equals("Категория ТС")) {
                if (!type.equals("Тип ТС")) {
                    if (!transportMassPlacedEdtxt.getText().toString().isEmpty()) {
                        massPlaced = Float.parseFloat(transportMassPlacedEdtxt.getText().toString());
                        if (!transportNoLoadMassEdtxt.getText().toString().isEmpty()) {
                            noLoadMass = Float.parseFloat(transportNoLoadMassEdtxt.getText().toString());

                            if (photos.size() >= 2) {
                                if (listener != null) {
                                    listener.onReceiveGeneralInfo(model, category, type,
                                            massPlaced, noLoadMass, photos);
                                }

                            } else {
                                onError("Фотографий должно быть не меньше 2");
                            }
                        } else {
                            onError("Заполните поле Масса без нагрузки");
                        }
                    } else {
                        onError("Заполните поле Размещаемая масса");
                    }
                } else {
                    onError("Заполните поле Тип ТС");
                }
            } else {
                onError("Заполните поле Категория ТС");
            }
        } else {
            onError("Заполните поле Модель ТС");
        }

    }


    private void getImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PHOTO_REQUEST);
    }

    public interface Callback {
        void onReceiveGeneralInfo(String model, String category,
                                  String type, float massPlaced,
                                  float noLoadMass, List<File> photos);
    }

}
