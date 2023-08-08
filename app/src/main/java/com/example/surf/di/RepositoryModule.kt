package com.example.surf.di

import com.example.surf.data.repository.CocktailRepositoryRoomImpl
import com.example.surf.domain.repository.CocktailRepositoryRoom
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepositoryRoom(
        cocktailRepositoryRoomImpl: CocktailRepositoryRoomImpl
    ): CocktailRepositoryRoom
}