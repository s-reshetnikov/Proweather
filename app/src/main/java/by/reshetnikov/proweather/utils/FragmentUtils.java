package by.reshetnikov.proweather.utils;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public final class FragmentUtils {

    public static void replaceFragment(FragmentManager fragmentManager, @IdRes int placeholder, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(placeholder, fragment)
                .commit();
    }
}
