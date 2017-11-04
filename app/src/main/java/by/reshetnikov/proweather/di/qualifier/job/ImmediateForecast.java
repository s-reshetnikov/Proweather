package by.reshetnikov.proweather.di.qualifier.job;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by s-reshetnikov.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ImmediateForecast {
}
