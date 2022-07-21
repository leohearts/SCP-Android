package info.free.scp.bean

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * 储存本地的收藏读过等信息
 */
@Entity(tableName = "like_table")
data class ScpLikeModel(@PrimaryKey var link: String = "", var title: String = "", var like: Boolean,
                        var hasRead: Boolean, var boxId: Int = 0)

@Entity(tableName = "like_box_table")
data class ScpLikeBox @Ignore constructor(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        var name: String = ""
) {
    constructor() : this(0,"")
}