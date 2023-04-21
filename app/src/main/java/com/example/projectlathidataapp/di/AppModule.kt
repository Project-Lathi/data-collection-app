
package com.example.projectlathidataapp.di

import com.example.projectlathidataapp.Data.AuthRepository
import com.example.projectlathidataapp.Data.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)// means insatlling this in application component
object AppModule {

     @Provides
     @Singleton
     fun providesFirebaseAuth() = FirebaseAuth.getInstance()

     @Provides
     @Singleton
     fun providesRepositoryImpl(firebaseAuth: FirebaseAuth):AuthRepository{
         return AuthRepositoryImpl(firebaseAuth)
     }
}