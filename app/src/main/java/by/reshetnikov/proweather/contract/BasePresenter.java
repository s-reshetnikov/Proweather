package by.reshetnikov.proweather.contract;


public interface BasePresenter {

    void onCreate();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onError();
}
