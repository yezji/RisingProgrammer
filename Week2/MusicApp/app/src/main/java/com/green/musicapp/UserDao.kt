package com.green.musicapp

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users:User) // 데이터 여러개 올 수 있기에 가변인자 vararg 사용
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user:User)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateAll(vararg users:User)
    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(user:User)

    @Delete
    fun deleteAll(vararg users:User)
    @Delete
    fun delete(user:User)

    @Query("SELECT * FROM tb_user")
    fun getAll():List<User>
    @Query("SELECT * FROM tb_user WHERE id=:userId")
    fun getMatchUser(userId:String) : User?

}
