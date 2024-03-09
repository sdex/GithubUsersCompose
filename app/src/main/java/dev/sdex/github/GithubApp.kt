package dev.sdex.github

import android.app.Application
import dev.sdex.github.di.appModule
import dev.sdex.github.di.databaseModule
import dev.sdex.github.di.networkModule
import dev.sdex.github.di.repositoryModule
import dev.sdex.github.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class GithubApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(this@GithubApp)
            modules(
                appModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                databaseModule,
            )
        }
    }
}