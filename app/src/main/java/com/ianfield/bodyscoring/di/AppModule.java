package com.ianfield.bodyscoring.di;
import android.content.Context;

import com.ianfield.bodyscoring.BaseApplication;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */
@Module public class AppModule {
    private final BaseApplication application;

    public AppModule(BaseApplication application) {
        this.application = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ApplicationContext @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Qualifier @Documented @Retention(RetentionPolicy.RUNTIME) public @interface ApplicationContext {
    }
}