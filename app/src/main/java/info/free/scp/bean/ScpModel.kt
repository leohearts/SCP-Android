package info.free.scp.bean

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by zhufree on 2018/8/24.
 * SCP数据Model
 */

@Serializable
data class ScpModel(@SerialName("objectId") val sId: String, var link: String, var title: String,
                    @Optional var detailHtml: String = "", @Optional var subtext: String = "",
                    @Optional var snippet: String = "", @Optional var desc: String = "",
                    val createdAt: String, val updatedAt: String)