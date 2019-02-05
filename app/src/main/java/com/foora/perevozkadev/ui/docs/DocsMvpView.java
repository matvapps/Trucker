package com.foora.perevozkadev.ui.docs;

import com.foora.perevozkadev.data.network.model.FileResponse;
import com.foora.perevozkadev.ui.base.MvpView;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface DocsMvpView extends MvpView {
    void onGetOrderFiles(List<FileResponse> files);
    void onAddFileToOrder();
}
