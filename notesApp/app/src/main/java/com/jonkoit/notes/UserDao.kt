package com.jonkoit.notes

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Long): User?

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUsersByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): User?
}