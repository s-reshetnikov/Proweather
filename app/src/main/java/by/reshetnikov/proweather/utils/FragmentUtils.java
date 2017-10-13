package by.reshetnikov.proweather.utils;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public final class FragmentUtils {

    public static void replaceFragment(FragmentManager fragmentManager, @IdRes int placeholder, Fragment fragment, @Nullable String tag) {
        fragmentManager.beginTransaction()
                .replace(placeholder, fragment, tag)
                .commit();
    }

    public static void replaceFragmentAndAddToBackStack(FragmentManager fragmentManager, @IdRes int placeholder, Fragment fragment, @Nullable String tag) {
        fragmentManager.beginTransaction()
                .replace(placeholder, fragment, tag)
                .addToBackStack(null)
                .commit();

    }
}
