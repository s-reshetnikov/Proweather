package by.reshetnikov.proweather.data.exception;

import java.io.IOException;

/**
 * Created by s-reshetnikov.
 */

public class NoLocationException extends IOException {

    public NoLocationException() {
        super();
    }

    public NoLocationException(Throwable cause) {
        super(cause);
    }
}
