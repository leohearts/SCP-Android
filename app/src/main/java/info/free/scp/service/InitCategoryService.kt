package info.free.scp.service

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log
import info.free.scp.bean.ScpModel
import info.free.scp.util.PreferenceUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import info.free.scp.SCPConstants
import info.free.scp.SCPConstants.BroadCastAction.INIT_PROGRESS
import info.free.scp.bean.ScpCollectionModel
import info.free.scp.db.ScpDao


/**
 * 获取目录的service
 */
class InitCategoryService : IntentService("initDataService") {

    private var mLocalBroadcastManager: LocalBroadcastManager? = null

    private var scpModels: MutableList<ScpModel> = emptyList<ScpModel>().toMutableList()
    private var scpCollectionModels: MutableList<ScpCollectionModel> = emptyList<ScpCollectionModel>().toMutableList()
    private var requestCount = 0
        set(value) {
            Log.i("loading", "requestCount = $value")
            field = value
            sendThreadStatus(4*value)
        }

    override fun onCreate() {
        super.onCreate()
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this)
    }

    private fun finishCategoryLoad() {
        // TODO fix
        ScpDao.getInstance().insertCategoryData(scpModels)
        PreferenceUtil.setInitCategoryFinish(true)
        sendThreadStatus(100)
    }

    private fun sendThreadStatus(progress: Int) {
        val intent = Intent(INIT_PROGRESS)
        intent.putExtra("progress", progress)
        mLocalBroadcastManager?.sendBroadcast(intent)
    }


    override fun onHandleIntent(intent: Intent?) {
        getAllScpList(0)
    }

    private fun getAllScpList(i: Int) {
        HttpManager.instance.getAllScp(i*200, 200) {
            for (scp in it) {
                Log.i("loading", "${scp.scpType} index = ${scp.index}")
            }
            scpModels.addAll(it)
            Log.i("loading", "i = $i, size = ${scpModels.size}")
            requestCount += 1
            val next = i + 1
            if (it.size == 200) {
                getAllScpList(next)
            } else {
                finishCategoryLoad()
            }
        }
        HttpManager.instance.getAllCollection {
            scpCollectionModels.addAll(it)
            Log.i("loading", "i = $i, size = ${scpModels.size}")
            finishCategoryLoad()

        }
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
