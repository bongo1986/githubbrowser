/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package grzegorz.com.githubbrowser.di

import android.app.Application
import android.content.Context
import grzegorz.com.githubbrowser.services.GitHubService

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {



    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context
    ): OkHttpClient {
        var okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        return okHttpClientBuilder.build()
    }


    @Singleton
    @Provides
    fun provideGithubService(okHttpClient: OkHttpClient): GitHubService {

        val builder = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())

        return builder.client(okHttpClient).build().create<GitHubService>(GitHubService::class.java)

    }

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext
}
