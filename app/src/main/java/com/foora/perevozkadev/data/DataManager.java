package com.foora.perevozkadev.data;

import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.prefs.PrefRepo;

/**
 * Created by Alexandr.
 */
public interface DataManager extends RemoteRepo, PrefRepo, LocalRepo {
}
