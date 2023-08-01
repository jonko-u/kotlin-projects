package com.jonkoit.dependencyinjections

import com.jonkoit.apiservice.WeatherApiService
import com.jonkoit.datasource.WeatherApiClient
import com.jonkoit.datasource.WeatherApiDataSource
import com.jonkoit.datasource.WeatherDataSource
import com.jonkoit.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWeatherApiService(): WeatherApiService {
        return WeatherApiClient.weatherService
    }

    @Provides
    @Singleton
    fun provideWeatherDataSource(weatherApiService: WeatherApiService): WeatherDataSource {
        return WeatherApiDataSource(weatherApiService) // Use WeatherApiDataSource here, not WeatherApiClient
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherDataSource: WeatherDataSource): WeatherRepository {
        return WeatherRepository(weatherDataSource)
    }
}