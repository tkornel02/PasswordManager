package hu.bme.aut.android.passwordapp.password_list.data

// PasswordDao.kt
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PasswordDao {

    @Query("SELECT * FROM passwords")
    fun getAll(): List<PasswordItem>

    @Insert
    fun insert(passwords: PasswordItem): Long

    @Update
    fun update(password: PasswordItem)
    @Delete
    fun deleteItem(password: PasswordItem)
}
