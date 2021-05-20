/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CommonModule {

    companion object {
        private const val GITHUB_ADDRESS_BASE_URL =
            "https://raw.githubusercontent.com/anhquandlqb2001/hanhchinhvn/master/dist/"

        private const val FIRESTORE_BASE_URL =
            "http://192.168.1.7:3000/"
    }

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build();

    @Singleton
    @Provides
    @Named("address")
    fun provideAddressGithubRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(GITHUB_ADDRESS_BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .build()

    @Singleton
    @Provides
    @Named("nodejs")
    fun provideFirestoreRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(FIRESTORE_BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .build()
}