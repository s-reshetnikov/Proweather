package by.reshetnikov.proweather.data.exception;

import java.io.IOException;


public class NoSavedForecastDataException extends IOException {

    public NoSavedForecastDataException() {
        super();
    }

    public NoSavedForecastDataException(Throwable cause) {
        super(cause);
    }
}
