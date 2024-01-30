package hu.bme.aut.android.passwordapp.password_list

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.android.passwordapp.password_list.data.PasswordDao
import hu.bme.aut.android.passwordapp.password_list.data.PasswordItem

@Database(entities = [PasswordItem::class], version = 1)
abstract class PasswordDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao

    companion object {
        fun getDatabase(applicationContext: Context): PasswordDatabase {
            return Room.databaseBuilder(
                applicationContext,
                PasswordDatabase::class.java,
                "password-list"
            ).build();
        }
    }
}