package com.foora.perevozkadev.ui.add_transport.register_info;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_transport.PhotoListAdapter;
import com.foora.perevozkadev.ui.base.BaseFragment;
import com.foora.perevozkadev.utils.custom.MyDatePickerFragment;
import com.github.matvapps.AppEditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class FragmentRegisterInfo extends BaseFragment {

    private static final int PHOTO_REQUEST = 100;

    @BindView(R.id.transport_register_number)
    AppEditText registerNumberEdtxt;
    @BindView(R.id.transport_vin)
    AppEditText vinEdtxt;
    @BindView(R.id.transport_passport_num)
    AppEditText passportNumEdtxt;
    @BindView(R.id.transport_register_place)
    EditText registerPlaceEdtxt;
    @BindView(R.id.photo_list)
    RecyclerView photoList;
    @BindView(R.id.date)
    TextView dateTxtv;


    private Callback listener;

    private PhotoListAdapter photoListAdapter;

    public static FragmentRegisterInfo newInstance() {
        Bundle args = new Bundle();
        FragmentRegisterInfo fragment = new FragmentRegisterInfo();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transport_register_info, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected void setUp(View view) {
        photoListAdapter = new PhotoListAdapter();
        photoList.setLayoutManager(new LinearLayoutManager(getContext()));
        photoList.setAdapter(photoListAdapter);
        photoList.setNestedScrollingEnabled(false);
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

    @OnClick(R.id.date_container)
    void onDateContainerClick() {

        MyDatePickerFragment datePickerFragment = new MyDatePickerFragment();
        datePickerFragment.show(getFragmentManager(), "DATE");
        datePickerFragment.setDateChangeListener(datePicker -> {


            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, datePicker.getYear());
            calendar.set(Calendar.MONTH, datePicker.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault());
            String dateStr = format.format(calendar.getTime());
            dateTxtv.setText(dateStr);
        });
    }

    @OnClick(R.id.btn_add)
    void onAddTransport() {
        String registerNum = registerNumberEdtxt.getText().toString();
        String vin = vinEdtxt.getText().toString();
        String pssportNum = passportNumEdtxt.getText().toString();
        String registerPlace = registerPlaceEdtxt.getText().toString();
        String registerDate = dateTxtv.getText().toString();

        List<File> photos = new ArrayList<>(photoListAdapter.getItems());

        if (!registerNum.isEmpty()) {
            if (!vin.isEmpty()) {
                if (!pssportNum.isEmpty()) {
                    if (!registerPlace.isEmpty()) {
                        if (!registerDate.isEmpty()) {
                            if (photos.size() >= 2) {
                                if (listener != null)
                                    listener.onReceiveRegisterInfo(registerNum, vin,
                                            pssportNum, registerPlace, registerDate, photos);
                            } else {
                                onError("Фотографий должно быть не меньше 2");
                            }
                        } else {
                            onError("Заполните поле Дата регистрации");
                        }
                    } else {
                        onError("Заполните поле Место регистрации");
                    }
                } else {
                    onError("Заполните поле Номер транспорта ТС");
                }
            } else {
                onError("Заполните поле VIN");
            }
        } else {
            onError("Заполните поле Регистрационный номер");
        }

    }

    private void getImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PHOTO_REQUEST);
    }

    public interface Callback {
        void onReceiveRegisterInfo(String regNum, String vin,
                                   String carNum, String regPlace,
                                   String regDate, List<File> photos);
    }
}
