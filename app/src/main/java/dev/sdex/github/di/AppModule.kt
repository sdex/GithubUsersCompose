package dev.sdex.github.di


import dev.sdex.github.data.UserMapper
import dev.sdex.github.data.repository.UserRepositoryImpl
import dev.sdex.github.data.source.local.UsersDatabase.Companion.getDatabase
import dev.sdex.github.data.source.remote.AuthorizationInterceptor
import dev.sdex.github.data.source.remote.ForceCacheInterceptor
import dev.sdex.github.data.source.remote.GithubService
import dev.sdex.github.domain.repository.UserRepository
import dev.sdex.github.domain.usecase.GetUserDetailsUseCase
import dev.sdex.github.domain.usecase.GetUserListUseCase
import dev.sdex.github.ui.details.UserDetailsViewModel
import dev.sdex.github.ui.list.UserListViewModel
import dev.sdex.github.utils.isConnected
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

val appModule = module {
    viewModel {
        UserListViewModel(getUserListUseCase = get())
    }
    viewModel {
        UserDetailsViewModel(getUserDetailsUseCase = get())
    }
}

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(
            service = get(),
            database = get(),
            userMapper = UserMapper(),
        )
    }
}

val useCaseModule = module {
    factory { GetUserListUseCase(repository = get()) }
    factory { GetUserDetailsUseCase(repository = get()) }
}

val networkModule = module {

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(
                ForceCacheInterceptor(
                    isConnected = {
                        androidApplication().isConnected()
                    },
                ),
            )
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY),
            )
            .addInterceptor(AuthorizationInterceptor())
            .cache(
                Cache(
                    directory = File(androidApplication().cacheDir, "http_cache"),
                    maxSize = 10 * 1024 * 1024,
                ),
            )
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(GithubService.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(),
            )
            .client(get())
            .build()
            .create(GithubService::class.java)
    }
}

val databaseModule = module {
    single { getDatabase(androidApplication()) }
}
