package com.example.chatgeminiapp.domain.models.gemini_chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.chatgeminiapp._common.resources.Constants.USER_ROLE

//@Entity(
//    foreignkeys = [
//        foreignkey(
//            entity = chatgroup::class,
//            parentcolumns = ["id"],
//            childcolumns = ["chatgroupowner"],
//            ondelete = foreignkey.cascade
//        ),
//    ],
//)
@Entity()
data class ChatItem(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    // True == User; False == Model
    val role: Int,
    val prompt: String? = null,
    val bitmapSource: String? = null,

    @ColumnInfo(index = true)
    val chatGroupOwner: Long // For Foreign Key
) {


}
