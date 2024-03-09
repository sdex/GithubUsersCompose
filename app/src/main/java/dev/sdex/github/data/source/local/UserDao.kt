package dev.sdex.github.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sdex.github.domain.model.User
import dev.sdex.github.domain.model.UserDetails

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM users")
    fun getAll(): PagingSource<Int, User>

    @Query("DELETE FROM users")
    suspend fun clearAll()

    @Query("SELECT * FROM users WHERE id = (SELECT MAX(id) FROM users)")
    suspend fun getLastUser(): User?

    @Query("SELECT * FROM user_details WHERE login = :username")
    suspend fun getUserDetails(username: String): UserDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserDetails)
}
