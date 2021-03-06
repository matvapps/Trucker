package com.foora.perevozkadev.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Alexander Matvienko on 08.02.2019.
 */
public class AppUtils {
    public static String getCountryCode(String countryName) {
        String[] isoCountryCodes = Locale.getISOCountries();
        Map<String, String> countryMap = new HashMap<>();

        for (String code: isoCountryCodes) {
            Locale locale = new Locale("", code);
            String name = locale.getDisplayCountry();

            countryMap.put(name, code);
        }

        return countryMap.get(countryName) == null? "  ": countryMap.get(countryName);
//        return countryMap.get(countryName);
    }
}
