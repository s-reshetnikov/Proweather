package by.reshetnikov.proweather;

/**
 * Created by SacRahl on 5/29/2017.
 */

public interface BasePresenter {

    void Subscribe();

    void Unsubscribe();

    void resume();

    void pause();

    void stop();

    void destroy();

    void onError();
}
