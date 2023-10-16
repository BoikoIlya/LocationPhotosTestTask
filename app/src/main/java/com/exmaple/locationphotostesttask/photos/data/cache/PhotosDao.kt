package com.exmaple.locationphotostesttask.photos.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
@Dao
interface PhotosDao {

    companion object{
        const val photos_table_name: String ="photos_table"
    }

    @Query("SELECT * FROM photos_table " +
            "ORDER BY date DESC " +
            "LIMIT :pageSize OFFSET :offset")
    suspend fun fetchPhotos(offset:Int, pageSize: Int):List<PhotoCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(list: List<PhotoCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(item: PhotoCache)

    @Query("DELETE FROM photos_table WHERE id = :id")
    suspend fun deletePhoto(id: Int)
}