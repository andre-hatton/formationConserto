package pro.conserto.database.module

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import pro.conserto.database.FormationDatabase

val databaseModule = module {
    single { provideRoomDatabase(androidContext()) }
    getDependencies(this)

}

val databaseTestModule = module {
    single { provideRoomDatabaseTest(androidContext()) }
    getDependencies(this)
}

// list of dependencies for room modules
private fun getDependencies(module: Module) {
    module.apply {
        single { get<FormationDatabase>().getAnimeDao() }
    }
}

/**
 * Provide the database
 * @param context The app context
 * @return The database
 */
private fun provideRoomDatabase(context: Context): FormationDatabase =
    Room.databaseBuilder(context, FormationDatabase::class.java, "formation-conserto-db").build()


/**
 * Provide the database for testing
 * @param context The app context
 * @return The database
 */
private fun provideRoomDatabaseTest(context: Context): FormationDatabase =
    Room.inMemoryDatabaseBuilder(context, FormationDatabase::class.java).allowMainThreadQueries().build()