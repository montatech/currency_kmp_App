package org.example.project.di

import org.example.project.network.remote.ApiRepository
import org.example.project.network.remote.CurrencyProvider
import org.example.project.network.remote.MainApiClient
import org.example.project.peresentation.viewModel.MainViewModel
import org.koin.dsl.module

val appModule = module{
    single { MainApiClient.createHttpClient() }
    single { ApiRepository(get()) }
    single { MainViewModel(get()) }
}