package com.exmaple.locationphotostesttask.photodetails.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.exmaple.locationphotostesttask.core.Mapper
import com.exmaple.locationphotostesttask.photodetails.domain.CommentDomain
import com.exmaple.locationphotostesttask.photos.data.cache.PhotoCache

/**
 * Created by Ilya Boiko @camancho
on 16.10.2023.
 **/
@Entity(
 tableName = CommentsDao.comments_table_name,
 foreignKeys = [
  ForeignKey(
   entity = PhotoCache::class,
   parentColumns = ["id"],
   childColumns = ["photoId"],
   onDelete = CASCADE
  )]
)
data class CommentCache(
 @PrimaryKey(autoGenerate = false)
 val id: Int,
 @ColumnInfo(index = true)
 val photoId: Int,
 val text: String,
 val date: Long,
) : Mapper<Unit, CommentDomain> {

 override fun map(data: Unit): CommentDomain = CommentDomain(id, photoId, text, date)
}
