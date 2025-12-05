package dev.jmyp.movies.di

import android.content.Context
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import dev.jmyp.movies.data.cache.GenreCache
import dev.jmyp.movies.data.network.GenreApiService
import dev.jmyp.movies.data.network.MoviesApiService
import dev.jmyp.movies.data.network.PeopleApiService
import dev.jmyp.movies.data.network.createHttpClient
import dev.jmyp.movies.data.repository.FavoriteRepository
import dev.jmyp.movies.data.repository.GenreRepository
import dev.jmyp.movies.data.repository.MoviesRepository
import dev.jmyp.movies.data.repository.PeopleRepository
import dev.jmyp.movies.domain.usecase.AddFavoriteUseCase
import dev.jmyp.movies.domain.usecase.CreditsUseCase
import dev.jmyp.movies.domain.usecase.FavoriteMoviesUseCase
import dev.jmyp.movies.domain.usecase.GenreUseCase
import dev.jmyp.movies.domain.usecase.GetFavoritesUseCase
import dev.jmyp.movies.domain.usecase.IsFavoriteUseCase
import dev.jmyp.movies.domain.usecase.MovieDetailUseCase
import dev.jmyp.movies.domain.usecase.NowPlayingUseCase
import dev.jmyp.movies.domain.usecase.PersonCreditsUseCase
import dev.jmyp.movies.domain.usecase.PersonDetailUseCase
import dev.jmyp.movies.domain.usecase.PopularMoviesUseCase
import dev.jmyp.movies.domain.usecase.RemoveFavoriteUseCase
import dev.jmyp.movies.domain.usecase.SimilarMoviesUseCase
import dev.jmyp.movies.domain.usecase.TrendingMoviesUseCase
import dev.jmyp.movies.domain.usecase.UpcomingUseCase
import dev.jmyp.movies.ui.navigation.MovieCategory
import dev.jmyp.movies.ui.navigation.PersonCategory
import dev.jmyp.movies.ui.screens.allCredits.AllCreditsViewModel
import dev.jmyp.movies.ui.screens.allMovies.AllMoviesViewModel
import dev.jmyp.movies.ui.screens.home.HomeViewModel
import dev.jmyp.movies.ui.screens.movieDetail.MovieDetailViewModel
import dev.jmyp.movies.ui.screens.personDetail.PersonDetailViewModel
import org.koin.dsl.module

// NETWORK
val networkModule = module {
    single { createHttpClient() }
    single { MoviesApiService(get()) }
    single { PeopleApiService(get()) }
    single { GenreApiService(get()) }
}

// DATA
val dataModule = module {
    single { MoviesRepository(get()) }
    single { PeopleRepository(get()) }
    single { GenreRepository(get()) }
    single { GenreCache() }
    single { FavoriteRepository(get()) }
}

// DOMAIN
val domainModule = module {
    single { TrendingMoviesUseCase(get()) }
    single { PopularMoviesUseCase(get()) }
    single { NowPlayingUseCase(get()) }
    single { UpcomingUseCase(get()) }
    single { MovieDetailUseCase(get()) }
    single { CreditsUseCase(get()) }
    single { SimilarMoviesUseCase(get()) }
    single { PersonDetailUseCase(get()) }
    single { PersonCreditsUseCase(get()) }
    single { GenreUseCase(get()) }
    single { FavoriteMoviesUseCase(get()) }
    single { AddFavoriteUseCase(get()) }
    single { RemoveFavoriteUseCase(get()) }
    single { IsFavoriteUseCase(get()) }
    single { GetFavoritesUseCase(get()) }
}

// PRESENTATION
val presentationModule = module {
    factory { HomeViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    factory { (movieId: Int) ->
        MovieDetailViewModel(
            movieId,
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    factory { (personId: Int) -> PersonDetailViewModel(personId, get(), get()) }
    factory { (referenceId: Int?, movieCategory: MovieCategory) ->
        AllMoviesViewModel(
            referenceId,
            movieCategory,
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    factory { (personId: Int, personCategory: PersonCategory) ->
        AllCreditsViewModel(
            personId,
            personCategory,
            get()
        )
    }
}

// STORAGE
val storageModule = module {
    single {
        PreferenceDataStoreFactory.create(
            produceFile = { get<Context>().dataStoreFile("app_prefs.preferences_pb") }
        )
    }
}

// ALL MODULES
val appModules = listOf(
    networkModule,
    dataModule,
    domainModule,
    presentationModule,
    storageModule
)