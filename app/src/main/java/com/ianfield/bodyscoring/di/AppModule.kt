package com.ianfield.bodyscoring.di

import android.content.Context
import com.ianfield.bodyscoring.BodyConditioningApplication
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * A module for Android-specific dependencies which require a [Context] or
 * [android.app.Application] to create.
 */
@Module
class AppModule(private val application: BodyConditioningApplication) {

    /**
     * Allow the application context to be injected but require that it be annotated with
     * [@Annotation][ApplicationContext] to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    @Qualifier
    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ApplicationContext
}