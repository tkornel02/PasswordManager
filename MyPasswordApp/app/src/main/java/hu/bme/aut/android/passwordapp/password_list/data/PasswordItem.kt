package hu.bme.aut.android.passwordapp.password_list.data

// PasswordEntity.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var password: String,
    var description: String
)
