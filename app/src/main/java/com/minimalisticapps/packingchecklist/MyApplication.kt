package com.minimalisticapps.packingchecklist

import android.app.Application
import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val mainModule = module {
            fun provideDataBase(application: Application): AppDatabase {
                return Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    "packing_checklist_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }

            fun provideDao(dataBase: AppDatabase): DatabaseDao {
                return dataBase.databaseDao()
            }

            single { provideDataBase(androidApplication()) }
            single { provideDao(get()) }
            viewModel { MainViewModel(get()) }
        }

        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    mainModule
                )
            )
        }
    }

}
