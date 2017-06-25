package by.reshetnikov.proweather;


public interface BasePresenter {

    void onCreate();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onError();
}
