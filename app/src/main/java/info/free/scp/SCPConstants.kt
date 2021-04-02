package info.free.scp

/**
 * Created by zhufree on 2018/8/22.
 *
 */

object SCPConstants {
    const val PACKAGE_NAME = "info.free.scp"
    const val SCP_DB_NAME = "scp_data.db"
    const val INFO_DB_NAME = "scp_info.db"

    object AppMode {
        const val ONLINE = 1
        const val OFFLINE = 0
    }

    object OrderType {
        const val ASC = 1
        const val DESC = -1
    }

    object SearchType {
        const val TITLE = 0
        const val CONTENT = 1
    }

    // category type
    object Entry {
        const val SCP_DOC = 1 // SCP文档，包含其他文档
        const val SCP_CN_DOC = 2 // SCP-CN文档，包含其他文档
        const val STORY_DOC = 3 // 故事外围

        // no use
        const val ABOUT_SCP_DOC = 3 // 关于基金会 设定中心，GOI、hub等，相关材料，艺作、传承、精品、竞赛、征文等
        const val JOKE_DOC = 4 // 搞笑作品
    }


    object Category {
        const val SERIES = 1
        const val SERIES_CN = 2
        const val JOKE = 101
        const val SCP_EX = 102
        const val TALES = 99
        const val TALES_CN = 98
        const val TALES_BY_TIME = 97
        const val SETTINGS = 106
        const val SETTINGS_CN = 107
        const val STORY_SERIES = 108
        const val STORY_SERIES_CN = 109
        const val CONTEST = 110
        const val CONTEST_CN = 111

        const val JOKE_CN = 22
        const val SCP_EX_CN = 4
        const val SCP_ARCHIVES = 17
        const val SCP_REMOVED = 18
        const val SCP_DECOMMISSIONED = 19
        const val SCP_ABNORMAL = 20
        const val ABOUT_INFO = 5
        const val ABOUT_INTRO = 16


        const val EVENT = 14

        const val SCP_INTERNATIONAL = 24
    }

    object ScpType {
        // save type
        const val SINGLE_PAGE = 0 // 单页面
        const val SAVE_INFO = 102 // 设定说明类页面
        const val SAVE_ABNORMAL = 103 // 其中的异常物品超长事件等
        const val SAVE_INTRO = 104 // 其中 faq，新手指南之类

        const val SAVE_SERIES = 1
        const val SAVE_SERIES_CN = 2

        const val SAVE_TALES = 3
        const val SAVE_TALES_CN = 4
        const val SAVE_TALES_BY_TIME = 101

        const val SAVE_JOKE = 5
        const val SAVE_JOKE_CN = 6
        const val SAVE_ARCHIVED = 7
        const val SAVE_EX = 8
        const val SAVE_EX_CN = 9
        const val SAVE_DECOMMISSIONED = 10
        const val SAVE_REMOVED = 11
        // 后面加字母

        const val SAVE_STORY_SERIES = 19
        const val SAVE_STORY_SERIES_CN = 20
        const val SAVE_SETTINGS = 13
        const val SAVE_SETTINGS_CN = 14
        const val SAVE_CONTEST = 15
        const val SAVE_CONTEST_CN = 17
        const val SAVE_INTERNATIONAL = 24

        // android
        const val SAVE_OFFSET = 21
        const val SAVE_COLLECTION_ITEM = 22
    }

    const val BMOB_API_URL = "https://api.bmob.cn/"
    const val SCP_SITE_URL = "http://scp-wiki-cn.wikidot.com"
    const val FEED_API_URL = "http://api.zhufree.fun"

    // 广播类型
    object BroadCastAction {
        const val ACTION_CHANGE_THEME = "changeTheme"
    }

    // RequestCode
    object RequestCode {
        const val CATEGORY_TO_DETAIL = 0
        const val REQUEST_FILE_PERMISSION = 1
        const val REQUEST_PICTURE_DIR = 2
    }

    const val HISTORY_TYPE = 0
    const val LATER_TYPE = 1

    const val LATEST_CREATED = -1
    const val LATEST_TRANSLATED = -2
    const val TOP_RATED_ALL = 0
    const val TOP_RATED_SCP = 1
    const val TOP_RATED_TALES = 2
    const val TOP_RATED_GOI = 3
    const val TOP_RATED_WANDERS = 4

}