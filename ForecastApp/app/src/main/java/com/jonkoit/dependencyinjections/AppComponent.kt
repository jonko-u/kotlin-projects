package com.jonkoit.dependencyinjections

import com.jonkoit.forecastapp.MainActivity
import com.jonkoit.datasource.WeatherApiClient
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    // Function to inject dependencies into MainActivity
    fun inject(mainActivity: MainActivity)
    // Function to inject dependencies into WeatherApiClient
    
}