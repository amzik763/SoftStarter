package com.tillagewireless.ss.di
import android.content.Context
import androidx.room.Room
import com.tillagewireless.ss.data.UserPreferences
import com.tillagewireless.ss.data.db.DeviceDao
import com.tillagewireless.ss.data.db.DeviceDatabase
import com.tillagewireless.ss.data.db.UserDao
import com.tillagewireless.ss.data.network.AuthApi
import com.tillagewireless.ss.data.network.DeviceAPI
import com.tillagewireless.ss.data.network.RemoteDataSource
import com.tillagewireless.ss.data.network.UserApi
import com.tillagewireless.ss.data.repository.AuthRepository
import com.tillagewireless.ss.data.repository.DeviceRepository
import com.tillagewireless.ss.data.repository.UserRepository
import com.tillagewireless.ss.others.Constants.RUNNING_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource()
    }

    @Singleton
    @Provides
    fun provideAuthApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): AuthApi {
        return remoteDataSource.buildApi(AuthApi::class.java, context)
    }

    @Singleton
    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Singleton
    @Provides
    fun provideUserApi(
        remoteDataSource: RemoteDataSource,
        userPreferences: UserPreferences,
        @ApplicationContext context: Context
    ): UserApi {
        return runBlocking {
            val token = userPreferences.accessToken.first()
            remoteDataSource.buildApi(UserApi::class.java, context,token)
       }
    }

    @Singleton
    @Provides
    fun provideDeviceApi(
        remoteDataSource: RemoteDataSource,
        userPreferences: UserPreferences,
        @ApplicationContext context: Context
    ): DeviceAPI {
        return runBlocking {
            val token = userPreferences.accessToken.first()
            remoteDataSource.buildApi(DeviceAPI::class.java, context,token)
        }
    }

    @Singleton
    @Provides
    fun provideDeviceDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        DeviceDatabase::class.java,
        RUNNING_DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideUserDao(db:DeviceDatabase) = db.getUserDao()

    @Singleton
    @Provides
    fun provideDeviceDao(db:DeviceDatabase) = db.getDeviceDao()

    @Provides
    fun provideAuthRepository(
        authApi: AuthApi,
        userPreferences: UserPreferences
    ): AuthRepository {
        return AuthRepository(authApi, userPreferences)
    }

    @Provides
    fun provideUserRepository(
        userApi: UserApi,
        userDao: UserDao,
        deviceDao: DeviceDao,
        userPreferences: UserPreferences
    ): UserRepository {
        return UserRepository(userApi,userDao, deviceDao, userPreferences)
    }

    @Provides
    fun provideDeviceDataRepository(
        deviceApi: DeviceAPI,
        userPreferences: UserPreferences
    ): DeviceRepository{
        return DeviceRepository(deviceApi,userPreferences)
    }
}