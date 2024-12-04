package edu.ucne.jsautoimports.data.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.jsautoimports.data.local.database.JSAutoPartsDb
import edu.ucne.jsautoimports.data.remote.apis.CarritoApi
import edu.ucne.jsautoimports.data.remote.apis.CategoriaApi
import edu.ucne.jsautoimports.data.remote.apis.PedidoApi
import edu.ucne.jsautoimports.data.remote.apis.PiezaApi
import edu.ucne.jsautoimports.data.remote.apis.UsuarioAPi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    const val BASE_URL = "https://jsautoparts-fzajb3h9eqhvapfj.eastus2-01.azurewebsites.net/"

    @Provides
    @Singleton
    fun ProvideJSAutoPartsDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            JSAutoPartsDb::class.java,
            "JSAutoPartsDb"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun ProvideCategoriaDao(db: JSAutoPartsDb) = db.categoriaDao

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun ProvidePiezaDao(db: JSAutoPartsDb) = db.piezaDao

    @Provides
    @Singleton
    fun ProvideUsuarioDao(db: JSAutoPartsDb) = db.usuarioDao

    @Provides
    @Singleton
    fun ProvideCarritoDao(db: JSAutoPartsDb) = db.carritoDao

    @Provides
    @Singleton
    fun ProvideCarritoDetalleDao(db: JSAutoPartsDb) = db.carritoDetalleDao

    @Provides
    @Singleton
    fun ProvidePedidoDao(db: JSAutoPartsDb) = db.pedidoDao

    @Provides
    @Singleton
    fun ProvidePedidoDetalleDao(db: JSAutoPartsDb) = db.pedidoDetalleDao

    @Singleton
    @Provides
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesCarritoApi(moshi: Moshi): CarritoApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(CarritoApi::class.java)
    }

    @Provides
    @Singleton
    fun providesCategoriaApi(moshi: Moshi): CategoriaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(CategoriaApi::class.java)
    }

    @Provides
    @Singleton
    fun providesPedidoApi(moshi: Moshi): PedidoApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PedidoApi::class.java)
    }

    @Provides
    @Singleton
    fun providesPiezaApi(moshi: Moshi): PiezaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PiezaApi::class.java)
    }

    @Provides
    @Singleton
    fun providesUsuarioApi(moshi: Moshi): UsuarioAPi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(UsuarioAPi::class.java)
    }
}