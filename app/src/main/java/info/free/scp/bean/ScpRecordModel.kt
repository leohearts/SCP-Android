package info.free.scp.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import info.free.scp.util.TimeUtil
import java.util.*

/**
 * 储存本地的收藏读过等信息
 */
@Entity(tableName = "records")
data class ScpRecordModel(@PrimaryKey var link: String, var title: String,
        // 上次阅读的历史时间/加入待读列表的时间,根据type判断
                          var viewListType: Int = -1,
        // 待读还是读过的type，用一个表
                          var viewTime: Date = Date()) {
    fun showTime(): String {
        return TimeUtil.timeStampToStr(viewTime.time)
    }
}