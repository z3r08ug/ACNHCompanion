package dev.uublabs.chrisvansco.acnhcompanion.persistence

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.uublabs.chrisvansco.acnhcompanion.model.Bug
import dev.uublabs.chrisvansco.acnhcompanion.model.Fish
import dev.uublabs.chrisvansco.acnhcompanion.model.SeaCreature
import dev.uublabs.chrisvansco.acnhcompanion.util.BugDictionary
import dev.uublabs.chrisvansco.acnhcompanion.util.FishDictionary
import dev.uublabs.chrisvansco.acnhcompanion.util.SeaCreatureDictionary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//Annotates class to be a Room DB with a table(entity) of the Fish and Bug tables
@Database(entities = [Fish::class, Bug::class, SeaCreature::class], version = 2, exportSchema = false)
abstract class ACNHCompRoomDatabase: RoomDatabase() {
    abstract fun fishDao(): FishDao
    abstract fun bugDao(): BugDao
    abstract fun seaCreatureDao(): SeaCreatureDao

    companion object {
        private lateinit var context: Context
        private val TAG = ACNHCompRoomDatabase::class.simpleName

        //Singleton prevents multiple instances of database opening at same time
        @Volatile
        private var INSTANCE: ACNHCompRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ACNHCompRoomDatabase {
            Companion.context = context
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ACNHCompRoomDatabase::class.java,
                        "app_database"
                )
                        .addCallback(ACNHCompDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                return instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `seacreatures_table` (`name` TEXT NOT NULL, `monthNH` TEXT NOT NULL, " +
                        "'monthSH' TEXT NOT NULL, 'time' TEXT NOT NULL, 'shadow' TEXT NOT NULL, 'speed' TEXT NOT NULL, " +
                        "'price' TEXT NOT NULL, 'caught' TEXT NOT NULL, PRIMARY KEY('name'))")
            }
        }
    }

    private class ACNHCompDatabaseCallback(
            private val scope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d(TAG, "onOpen: ")
            INSTANCE?.let { db ->
                scope.launch {
                    populateDatabase(db.fishDao(), db.bugDao(), db.seaCreatureDao())
                }
            }
        }

        suspend fun populateDatabase(fishDao: FishDao, bugDao: BugDao, seaCreatureDao: SeaCreatureDao) {
            fishDao.deleteAll()
            bugDao.deleteAll()
            seaCreatureDao.deleteAll()

            val fishDictionary = FishDictionary(context)
            val bugDictionary = BugDictionary(context)
            val seaCreatureDictionary = SeaCreatureDictionary(context)
            //populate db here
            for (f: Fish in fishDictionary.getAllFish()) {
                Log.d(TAG, "populateDatabase: ${f.name}")
                fishDao.insert(f)
            }
            for (b: Bug in bugDictionary.getAllBugs()) {
                Log.d(TAG, "populateDatabase: ${b.name}")
                bugDao.insert(b)
            }
            for (s: SeaCreature in seaCreatureDictionary.getAllSeaCreatures()) {
                Log.d(TAG, "populateDatabase: ${s.name}")
                seaCreatureDao.insert(s)
            }
        }
    }
}