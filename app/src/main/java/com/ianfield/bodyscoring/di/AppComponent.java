package com.ianfield.bodyscoring.di;

import com.ianfield.bodyscoring.BodyConditioningApplication;
import com.ianfield.bodyscoring.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Ian Field on 26/03/2016.
 */
@Singleton @Component(modules = AppModule.class) public interface AppComponent {
    void inject(BodyConditioningApplication application);
    void inject(MainActivity activity);
}